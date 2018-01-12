package nju.edu.cn.log.log_tracking.http_wrapper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        WrapperResponse wrapperResponse = new WrapperResponse((HttpServletResponse) response);
        filterChain.doFilter(request, wrapperResponse);
    }

    @Override
    public void init(FilterConfig paramFilterConfig) throws ServletException {
    }

}