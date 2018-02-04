package nju.edu.cn.log.log_tracking.log_collect;

import com.alibaba.fastjson.JSONObject;
import nju.edu.cn.log.log_tracking.http_wrapper.WrapperResponse;
import nju.edu.cn.log.log_tracking.id_generate.IdGetter;
import nju.edu.cn.log.log_tracking.log_context.LogContext;
import nju.edu.cn.log.log_tracking.log_context.LogContextBuilder;
import nju.edu.cn.log.log_tracking.send_log.LogkafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by cong on 2018-01-03.
 * 拦截每一次http请求,如果没有traceId，需要生成一个traceId并且记录访问日志
 */
@Component("accessLogInteceptor")
public class AccessLogInteceptor implements HandlerInterceptor{

    @Resource
    private LogContext logContext;

    @Value("${spring.application.name}")
    private String sysName;

    @Autowired
    private LogkafkaProducer logSender;

    @Autowired
    private LogContextBuilder builder;

    @Autowired
    private LogSelector logSelector;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        if(StringUtils.isEmpty(request.getHeader(LogContext.TRACE_ID_HEADER_KEY))){
            builder.buildWithoutHeader(request,logContext);
            HandlerMethod handlerMethod=(HandlerMethod)o;

            AccessLogVO accessLogVO=new AccessLogVO();
            accessLogVO.setType(AccessTypeEnum.HTTP_REQUEST.getCode());
            accessLogVO.setServiceUrl(request.getRequestURL().toString());
            accessLogVO.setServiceName(buildServiceName(getServicePath(handlerMethod)));
            if(logSelector.logContent()){
                accessLogVO.setContent(readRequestBody(request));
            }
            accessLogVO.setTarget(logContext.getSysName());
            saveLog(accessLogVO);
        }else{
            builder.buildWithHeader(request,logContext);
        }

        return true;
    }



    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) throws Exception {
        String body=readResponseBody(response);
        if(!logContext.isFirstRequest()||logContext.isException())
            return;
        AccessLogVO logVO=new AccessLogVO();
        logVO.setType(AccessTypeEnum.HTTP_RESPONSE.getCode());
        if(logSelector.logContent()){
            logVO.setContent(body);
        }
        saveLog(logVO);
    }

    private void saveLog(AccessLogVO accessLogVO){
        accessLogVO.setTraceId(logContext.getTraceId());
        accessLogVO.setSpanId(logContext.getSpanId());
        accessLogVO.setParentSpanId(logContext.getParentSpanId());
        logSender.send(accessLogVO);
    }


    /**
     * 读取http中的值
     * @param request
     * @return
     */
    private String readRequestBody(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder builder=new StringBuilder();
        try {
            reader= request.getReader();

            String str = "";
            while ((str = reader.readLine()) != null) {
                builder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString().replace("\t","");
    }

    /**
     * 读取response的值
     * @param response
     * @return
     * @throws Exception
     */
    private String readResponseBody(HttpServletResponse response) throws Exception{
        WrapperResponse wrapperResponse=(WrapperResponse)response;
        String content=new String(wrapperResponse.getContent());
        PrintWriter out = response.getWriter();
        out.write(content);
        return content;
    }

    private String buildServiceName(String path){
        ServiceNameBuilder builder=new ServiceNameBuilder();
        builder.append(logContext.getSysName());
        builder.appendUrl(path);
        return builder.toString();
    }

    /**
     * 获取注解@RequestMapping配置的url
     * @param handlerMethod
     * @return
     */
    private String getServicePath(HandlerMethod handlerMethod){
        Method method=handlerMethod.getMethod();

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
