package nju.edu.cn.log.log_tracking.id_generate;

import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-01-18.
 */
@Component
public class IdGetter {

    private IdGenerator idGenerator;

    private static final String TRACE_ID_TAB="trace_id";

    private static final String SPAN_ID_TAB="span_id";

    public IdGetter(){
        idGenerator = IdGenerator.builder()
                .addHost("112.74.176.249", 6379,
                        "c5809078fa6d652e0b0232d552a9d06d37fe819c")
                .build();
    }

    /**
     * 获取下一个traceId
     * @return
     */
    public long nextTraceId(){
        return idGenerator.next(TRACE_ID_TAB);
    }

    /**
     * 获取下一个spanId
     * @return
     */
    public long nextSpanId(){
        return idGenerator.next(SPAN_ID_TAB);
    }

}
