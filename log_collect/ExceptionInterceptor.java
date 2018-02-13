package nju.edu.cn.log.log_tracking.log_collect;

import com.ts.dto.ResponseDto;
import nju.edu.cn.log.log_tracking.log_context.LogContext;
import nju.edu.cn.log.log_tracking.log_send.LogkafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by wuwf on 17/3/31.
 * 全局异常处理
 */
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @Autowired
    private LogkafkaProducer logkafkaProducer;

    @Autowired
    private LogContext logContext;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDto exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        ResponseDto responseDto=new ResponseDto();
        responseDto.setSuccess(false);
        responseDto.setMessage(e.getMessage());
        sendLog(e);
        return responseDto;
    }


    /**
     * 发送异常日志
     * @param e
     */
    private void sendLog(Exception e){
        AccessLogVO accessLogVO=new AccessLogVO();
        accessLogVO.setSpanId(logContext.getSpanId());
        accessLogVO.setTraceId(logContext.getTraceId());
        accessLogVO.setParentSpanId(logContext.getParentSpanId());
        accessLogVO.setType(AccessTypeEnum.EXCEPTION.getCode());
        accessLogVO.setContent(e.getMessage());
        logkafkaProducer.send(accessLogVO);
        logContext.setException(true);
    }

}