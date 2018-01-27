package nju.edu.cn.log.log_tracking.log_context;

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

    /**
     * 下一级要调用的spanId
     */
    private Long nextSpanId;

    /**
     * 本系统名称
     */
    private String sysName;

    /**
     * 是否异常
     */
    private boolean isException;

    public TrackContextVO() {
    }

    public TrackContextVO(Long traceId, Long spanId, Long parentSpanId,String sysName) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.sysName=sysName;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public Long getNextSpanId() {
        return nextSpanId;
    }

    public void setNextSpanId(Long nextSpanId) {
        this.nextSpanId = nextSpanId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
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
