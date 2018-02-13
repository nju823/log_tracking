package nju.edu.cn.log.log_tracking.log_select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cong on 2018-02-04.
 */
@Component
public class LogSelector {

    @Autowired
    private RandomUtil randomUtil;

    @Value("${log.content.chance}")
    private Integer logContentChance;

    @Value("${log.chance}")
    private Integer logChance;

    /**
     * 最大概率
     */
    private static final int CHANCE_BOUND=100;


    /**
     * 是否记录content
     * @return
     */
    public boolean isLogContent(LogTrack logTrack){
        if(logTrack!=null)
            return selectByRandom(logTrack.contentChance());
        return selectByRandom(logContentChance);
    }

    /**
     * 判断是否记录日志
     * 可以在配置文件中配置全局几率，使用注解配置单个方法的几率
     * 优先使用注解配置
     * @param logTrack
     * @return
     */
    public boolean isLog(LogTrack logTrack){
        if(logTrack!=null)
            return selectByRandom(logTrack.logChance());//使用注解配置的概率，优先由注解决定
        return selectByRandom(logChance);//使用配置文件配置的概率
    }


    /**
     * 通过随机数判断是否选择
     * @param chance
     * @return
     */
    private boolean selectByRandom(Integer chance){
        if(chance==null||chance>=CHANCE_BOUND)
            return true;
        if(chance<=0)
            return false;

        return chance>randomUtil.randomNumber(CHANCE_BOUND);
    }


}
