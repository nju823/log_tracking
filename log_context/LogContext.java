package nju.edu.cn.log.log_tracking.log_context;



import org.springframework.stereotype.Service;

/**
 * Created by cong on 2018-01-03.
 * 日志相关的上下文，用于标识一个调用链
 */
@Service(value = "logContext")
public class LogContext {

    public static final String TRACE_ID_HEADER_KEY="traceId";

    public static final String SPAN_ID_HEADER_KEY="spanId";

    public static final String PARENT_SPAN_ID_HEADER_KEY="parentSpanId";

    public static final String IS_LOG_KEY="isLog";

    public static final Long INVALID_PARENT_SPAN_ID=-1L;

    /**
     * 每次调用独有的上下文
     */
    private static final ThreadLocal<TrackContextVO> context=new ThreadLocal<>();

    public void init(Long traceId,Long spanId,Long parentSpanId,String sysName){
        TrackContextVO contextVO=new TrackContextVO(traceId,spanId,parentSpanId,sysName);
        context.set(contextVO);
    }

    public TrackContextVO getContextCopy(){
        return new TrackContextVO(context.get());
    }

    public void setContext(TrackContextVO contextVO){
        context.set(new TrackContextVO(contextVO));
    }

    /**
     * 判断是否为调用链的第一个请求
     * @return
     */
    public boolean isFirstRequest(){
        return INVALID_PARENT_SPAN_ID.equals(getParentSpanId());
    }

    public boolean isLog(){
        return context.get().getIsLog();
    }

    public void setLog(boolean isLog){
        context.get().setIsLog(isLog);
    }

    public boolean isException() {
        return context.get().getIsException();
    }

    public void setException(boolean exception) {
        context.get().setIsException(exception);
    }

    public Long getNextSpanId() {
        return context.get().getNextSpanId();
    }

    public void setNextSpanId(Long nextSpanId) {
        context.get().setNextSpanId(nextSpanId);
    }

    public Long getTraceId() {
        return context.get().getTraceId();
    }

    public void setTraceId(Long traceId) {
        context.get().setTraceId(traceId);
    }

    public Long getSpanId() {
        return context.get().getSpanId();
    }

    public void setSpanId(Long spanId) {
        context.get().setTraceId(spanId);
    }

    public Long getParentSpanId() {
        return context.get().getParentSpanId();
    }

    public void setParentSpanId(Long parentSpanId) {
        context.get().setParentSpanId(parentSpanId);
    }

    public String getSysName(){
        return context.get().getSysName();
    }

    public void print(){
        System.out.println("traceId:"+getTraceId());
        System.out.println("spanId:"+getSpanId());
        System.out.println("parentSpanId:"+getParentSpanId());
    }


}
