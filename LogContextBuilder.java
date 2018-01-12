package nju.edu.cn.log.log_tracking;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cong on 2018-01-07.
 * 用http请求的信息初始化logcontexxt
 */
public class LogContextBuilder {

    public static final Long INVALID_PARENT_SPAN_ID=-1L;

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
        Long traceId=getValue(request.getHeader("traceId"));
        Long spanId=generateSapnId();
        Long parentSapnId=getValue(request.getHeader("parentSpanId"));
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
        Long parentSapnId=INVALID_PARENT_SPAN_ID;
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
