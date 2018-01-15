package nju.edu.cn.log.log_tracking.log_collect;

import com.alibaba.fastjson.JSONObject;
import nju.edu.cn.log.log_tracking.id_generate.IdGenerator;
import nju.edu.cn.log.log_tracking.log_context.LogContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;


/**
 * Created by cong on 2018-01-14.
 * 拦截feign调用，记录调用日志
 */
@Component
@Aspect
public class FeignAop{

    @Autowired
    private LogContext logContext;

    @Value("${spring.application.name}")
    private String sysName;

    @Pointcut("execution(* *..outter_service.*.*(..))")
    public void feignRequest(){

    }

    @Before("feignRequest()")
    public void saveRequstLog(JoinPoint joinPoint){
        FeignClient client=(FeignClient) joinPoint.getSignature().getDeclaringType().getAnnotation(FeignClient.class);
        if(client==null)
            return;

        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        AccessLogVO accessLogVO=new AccessLogVO();

        String[] names=signature.getParameterNames();
        JSONObject jsonObject=new JSONObject(true);
        Object[] args=joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            jsonObject.put(names[i],args[i]);
        }

        ServiceNameBuilder builder=new ServiceNameBuilder();
        builder.append(client.value());
        builder.appendUrl(getServicePath(signature.getMethod()));
        accessLogVO.setServiceName(builder.toString());
        accessLogVO.setSource(sysName);
        accessLogVO.setTarget(client.value());
        Long spanId= IdGenerator.generateSpanId();
        logContext.setNextSpanId(spanId);

        saveLog(accessLogVO,jsonObject.toString(),AccessTypeEnum.HTTP_REQUEST);
    }

    @AfterReturning(value="feignRequest()",returning = "response")
    public void saveResponseLog(JoinPoint joinPoint,Object response){
        AccessLogVO logVO=new AccessLogVO();
        saveLog(logVO,JSONObject.toJSONString(response),AccessTypeEnum.HTTP_RESPONSE);
    }

    private void saveLog(AccessLogVO accessLogVO,String content,AccessTypeEnum accessType){
        accessLogVO.setType(accessType.getCode());
        accessLogVO.setSpanId(logContext.getNextSpanId());
        accessLogVO.setParentSpanId(logContext.getSpanId());
        accessLogVO.setTraceId(logContext.getTraceId());
        accessLogVO.setContent(content);
        System.out.println(JSONObject.toJSONString(accessLogVO));
    }


    /**
     * 获取注解@RequestMapping配置的url
     * @param handlerMethod
     * @return
     */
    private String getServicePath(Method method){
        RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
        if(requestMapping!=null)
            return requestMapping.value()[0];

        PostMapping postMapping=method.getAnnotation(PostMapping.class);
        if(postMapping!=null)
            return postMapping.value()[0];

        GetMapping getMapping=method.getAnnotation(GetMapping.class);
        if(getMapping!=null)
            return getMapping.value()[0];

        return "";
    }

}
