package nju.edu.cn.log.log_tracking;

/**
 * Created by cong on 2018-01-03.
 * 定义标识一次调用的id信息
 */
public class TrackContextVO {

    /**
     * 标识一次调用链的id
     */
    private Long traceId;

    /**
     * 标识调用链中的一个请求
     */
    private Long spanId;

    /**
     * 调用链中的父请求，即发起这个请求的spanId
     */
    private Long parentSpanId;

    public TrackContextVO() {
    }

    public TrackContextVO(Long traceId, Long spanId, Long parentSpanId) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public Long getSpanId() {
        return spanId;
    }

    public void setSpanId(Long spanId) {
        this.spanId = spanId;
    }

    public Long getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(Long parentSpanId) {
        this.parentSpanId = parentSpanId;
    }
}
