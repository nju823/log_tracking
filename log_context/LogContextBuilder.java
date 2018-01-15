package nju.edu.cn.log.log_tracking.log_context;

import javax.servlet.http.HttpServletRequest;

import static nju.edu.cn.log.log_tracking.log_context.LogContext.TRACE_ID_HEADER_KEY;

/**
 * Created by cong on 2018-01-07.
 * 用http请求的信息初始化logcontexxt
 */
public class LogContextBuilder {

    private String sysName;

    public LogContextBuilder(String sysName){
        this.sysName=sysName;
    }

    /**
     * http Header中有traceId,说明是被调用的
     * @param request
     * @param context
     */
    public void buildWithHeader(HttpServletRequest request,LogContext context){
        Long traceId=getValue(request.getHeader(TRACE_ID_HEADER_KEY));
        Long spanId=generateSapnId();
        Long parentSapnId=getValue(request.getHeader(LogContext.PARENT_SPAN_ID_HEADER_KEY));
        context.init(traceId,spanId,parentSapnId,sysName);
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
        return 10L;
    }

    private Long generateTraceId(){
        return 100L;
    }

}
