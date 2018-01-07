package nju.edu.cn.log.log_tracking;



import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by cong on 2018-01-03.
 * 日志相关的上下文，用于标识一个调用链
 */
@Service(value = "logContext")
public class LogContext {


    /**
     * 每次调用独有的上下文
     */
    private ThreadLocal<TrackContextVO> context=new ThreadLocal<>();

    public void init(Long traceId,Long spanId,Long parentSpanId){
        TrackContextVO contextVO=new TrackContextVO(traceId,spanId,parentSpanId);
        context.set(contextVO);
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

    public void print(){
        System.out.println("traceId:"+getTraceId());
        System.out.println("spanId:"+getSpanId());
        System.out.println("parentSpanId:"+getParentSpanId());
    }


}
