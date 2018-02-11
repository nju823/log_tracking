package nju.edu.cn.log.log_tracking.id_generate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-01-18.
 */
@Component
@Scope("singleton")
public class IdGetter {

    @Autowired
    private NumberIdGenerator idGenerator;

    private static final String TRACE_ID_TAB="trace_id";

    private static final String SPAN_ID_TAB="span_id";

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
