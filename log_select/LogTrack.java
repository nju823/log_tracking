package nju.edu.cn.log.log_tracking.log_select;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by cong on 2018-02-11.
 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target({ElementType.METHOD})//定义注解的作用目标**作用范围字段、枚举的常量/方法
public @interface LogTrack {

    /**
     * 记录接口参数的概率
     * 0-100%
     * @return
     */
    int contentChance() default 100;

    /**
     * 记录日志的概率
     * 0-100%
     * @return
     */
    int logChance() default 100;

}
