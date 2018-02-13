package nju.edu.cn.log.log_tracking.log_select;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by cong on 2018-02-11.
 */
@Component
public class RandomUtil {

    private Random randomGenerator;

    public RandomUtil(){
        randomGenerator=new Random();
    }

    /**
     * 生成小于bound的随机数
     * @param bound
     * @return
     */
    public int randomNumber(int bound){
        return randomGenerator.nextInt(bound);
    }

}
