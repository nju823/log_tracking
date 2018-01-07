package nju.edu.cn.log.log_tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cong on 2018-01-03.
 * 拦截每一次http请求,如果没有traceId，需要生成一个traceId并且记录访问日志
 */
@Component("accessLogInteceptor")
public class AccessLogInteceptor implements HandlerInterceptor{

    @Resource
    private LogContext logContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        LogContextBuilder builder=new LogContextBuilder();
        if(StringUtils.isEmpty(request.getHeader("traceId"))){
            builder.buildWithoutHeader(request,logContext);
        }else{
            builder.buildWithHeader(request,logContext);
        }
        logContext.print();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
