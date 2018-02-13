package nju.edu.cn.log.log_tracking.log_context;

import nju.edu.cn.log.log_tracking.id_generate.IdGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static nju.edu.cn.log.log_tracking.log_context.LogContext.SPAN_ID_HEADER_KEY;
import static nju.edu.cn.log.log_tracking.log_context.LogContext.TRACE_ID_HEADER_KEY;

/**
 * Created by cong on 2018-01-07.
 * 用http请求的信息初始化logcontexxt
 */
@Component
public class LogContextBuilder {

    @Value("${spring.application.name}")
    private String sysName;

    @Autowired
    private IdGetter idGetter;

    /**
     * http Header中有traceId,说明是被调用的
     * @param request
     * @param context
     */
    public void buildWithHeader(HttpServletRequest request,LogContext context){
        Long traceId=getValue(request.getHeader(TRACE_ID_HEADER_KEY));
        Long spanId=getValue(request.getHeader(SPAN_ID_HEADER_KEY));
        Long parentSapnId=getValue(request.getHeader(LogContext.PARENT_SPAN_ID_HEADER_KEY));
        context.init(traceId,spanId,parentSapnId,sysName);

        String isLog=request.getHeader(LogContext.IS_LOG_KEY);
        context.setLog("false".equals(isLog));
    }

    /**
     * http Header中没有traceId,需要生成traceId
     * @param request
     * @param context
     */
    public void buildWithoutHeader(HttpServletRequest request,LogContext context){
        Long traceId=generateTraceId();
        Long spanId=generateSapnId();
        Long parentSapnId=LogContext.INVALID_PARENT_SPAN_ID;
        context.init(traceId,spanId,parentSapnId,sysName);
    }

    private Long getValue(String str){
        Long value=0L;
        try {
            value=Long.parseLong(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    private Long generateSapnId(){
        return idGetter.nextSpanId();
    }

    private Long generateTraceId(){
        return idGetter.nextTraceId();
    }

}
