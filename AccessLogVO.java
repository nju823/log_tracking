package nju.edu.cn.log.log_tracking;

/**
 * Created by cong on 2018-01-03.
 * 定义一个访问日志的信息
 */
public class AccessLogVO {

    /**
     * 调用类型 1=http请求，2=http响应,3=查询数据库,4=数据库响应
     */
    private int type;

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
     * 发起请求的系统名称
     */
    private String source;

    /**
     * 响应请求的系统名称
     */
    private String target;

    /**
     * 调用的服务url
     */
    private String serviceUrl;

    /**
     * 调用的参数
     */
    private String content;

    /**
     * 调用的接口名称
     */
    private String serviceName;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
