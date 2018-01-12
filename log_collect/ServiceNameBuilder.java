package nju.edu.cn.log.log_tracking.log_collect;

import org.springframework.util.StringUtils;

/**
 * Created by cong on 2018-01-09.
 * 拼接服务名称，用.分割开
 */
public class ServiceNameBuilder {

    private static final char SPLIT_CHARACTER='.';

    private StringBuilder builder=new StringBuilder();

    /**
     * 添加字符串
     * @param string
     */
    public ServiceNameBuilder append(String string){
        if(StringUtils.isEmpty(string))
            return this;
        builder.append(string);
        builder.append(SPLIT_CHARACTER);
        return this;
    }

    /**
     * 批量添加字符串
     * @param strings
     * @return
     */
    public ServiceNameBuilder append(String[] strings){
        for(String string:strings){
            append(string);
        }
        return this;
    }

    @Override
    public String toString(){
        int length=builder.length();
        if(length==0)
            return "";
        return builder.toString().substring(0,length-1);
    }


}
