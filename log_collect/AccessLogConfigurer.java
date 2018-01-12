package nju.edu.cn.log.log_tracking.log_collect;


import nju.edu.cn.log.log_tracking.http_wrapper.HttpServletRequestReplacedFilter;
import nju.edu.cn.log.log_tracking.http_wrapper.ResponseFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AccessLogConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(getInteceptor());
        super.addInterceptors(registry);
    }

    @Bean
    public AccessLogInteceptor getInteceptor(){
        return new AccessLogInteceptor();
    }

    @Bean
    public FilterRegistrationBean requestWrapperRegistration() {
        HttpServletRequestReplacedFilter requestReplacedFilter=new HttpServletRequestReplacedFilter();
        return getRegistration(requestReplacedFilter);
    }

    @Bean
    public  FilterRegistrationBean responseWrapperRegistration(){
        ResponseFilter filter=new ResponseFilter();
        return getRegistration(filter);
    }

    private FilterRegistrationBean getRegistration(Filter filter){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}