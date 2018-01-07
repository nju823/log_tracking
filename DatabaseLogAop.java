package nju.edu.cn.log.log_tracking;

import com.alibaba.fastjson.JSONObject;
import com.ts.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-01-07.
 * 记录数据库访问日志
 */
@Component
@Aspect
public class DatabaseLogAop {

    @Pointcut("execution(* com.ts.mapper..*.*(..))")
    public void queryDatabase(){

    }

    @Before("queryDatabase()")
    public void saveAccessRequestLog(JoinPoint joinPoint){
        Object[] args=joinPoint.getArgs();
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        String[] names=signature.getParameterNames();
        JSONObject jsonObject=new JSONObject(true);
        for(int i=0;i<args.length;i++){
            jsonObject.put(names[i],args[i]);
        }
        System.out.println(jsonObject.toString());
        System.out.println(signature.getName());

    }

    @AfterReturning(value = "queryDatabase()",returning = "response")
    public void saveAccessResponseLog(JoinPoint joinPoint,Object response){
        System.out.println(JSONObject.toJSONString(response));
    }



}
