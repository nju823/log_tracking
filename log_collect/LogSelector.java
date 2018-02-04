package nju.edu.cn.log.log_tracking.log_collect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by cong on 2018-02-04.
 */
@Component
public class LogSelector {

    private static final String LOG_CONTENT="false";

    @Value("${log.content.has}")
    private String hasContent;


    /**
     * 是否记录content
     * @return
     */
    public boolean logContent(){
        return !LOG_CONTENT.equals(hasContent);
    }


}
