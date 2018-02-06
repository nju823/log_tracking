package nju.edu.cn.log.log_tracking.log_collect;

import com.alibaba.fastjson.JSONObject;
import nju.edu.cn.log.log_tracking.id_generate.IdGetter;
import nju.edu.cn.log.log_tracking.log_context.LogContext;
import nju.edu.cn.log.log_tracking.log_context.LogContextBuilder;
import nju.edu.cn.log.log_tracking.send_log.LogkafkaProducer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-01-07.
 * 记录数据库访问日志
 */
@Component
@Aspect
public class DatabaseLogAop {

    @Autowired
    private LogContext logContext;

    @Autowired
    private LogkafkaProducer logSender;

    @Autowired
    private LogSelector logSelector;

    @Autowired
    private IdGetter idGetter;

    private static final String PATH="execution(* *..mapper.*Mapper.*(..))";

    @Pointcut(PATH)
    public void queryDatabase(){

    }

    @Before("queryDatabase()")
    public void saveAccessRequestLog(JoinPoint joinPoint){

        MethodSignature signature=(MethodSignature) joinPoint.getSignature();

        String[] names=signature.getParameterNames();
        JSONObject jsonObject=new JSONObject(true);
        Object[] args=joinPoint.getArgs();
        for(int i=0;i<args.length;i++){
            jsonObject.put(names[i],args[i]);
        }

        String dao=signature.getDeclaringType().getSimpleName();

        logContext.setNextSpanId(idGetter.nextSpanId());
        saveDataBaseLog(signature.getName(),jsonObject.toString(), AccessTypeEnum.DATABASE_REQUEST,dao);
    }

    @AfterReturning(value = "queryDatabase()",returning = "response")
    public void saveAccessResponseLog(JoinPoint joinPoint,Object response){
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        String dao=signature.getDeclaringType().getSimpleName();
        saveDataBaseLog(signature.getName(),JSONObject.toJSONString(response),AccessTypeEnum.DATABASE_RESPONSE,dao);
    }

    /**
     * 保存数据库的访问日志
     * @param serviceName
     * @param content
     * @param type
     */
    private void saveDataBaseLog(String serviceName,String content,AccessTypeEnum type,String dao){
        AccessLogVO accessLogVO=new AccessLogVO();

        accessLogVO.setType(type.getCode());
        if(logSelector.logContent()){
            accessLogVO.setContent(content);
        }
        accessLogVO.setParentSpanId(logContext.getSpanId());
        accessLogVO.setSpanId(logContext.getNextSpanId());
        accessLogVO.setTraceId(logContext.getTraceId());
        accessLogVO.setSource(logContext.getSysName());
        accessLogVO.setTarget(logContext.getSysName()+"_db");
        ServiceNameBuilder builder=new ServiceNameBuilder();
        builder.append(logContext.getSysName()).append(dao).append(serviceName);
        serviceName=builder.toString();
        accessLogVO.setServiceName(serviceName);

        logSender.send(accessLogVO);
    }



}
