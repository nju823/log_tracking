package nju.edu.cn.log.log_tracking;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-01-09.
 * http请求日志的获取
 */
@Component
@Aspect
public class HttpLogAop {

    @Autowired
    private LogContext logContext;

    @Pointcut("execution(* com.ts.controller..*.*(..))")
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


    }

    @AfterReturning(value = "queryDatabase()",returning = "response")
    public void saveAccessResponseLog(JoinPoint joinPoint,Object response){
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
    }

}
