package nju.edu.cn.log.log_tracking.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by cong on 2018-01-16.
 */
public class ZookeeperManager {


    private static ZooKeeper zookeeper;

    private static final String ZOOKEEPER_HOST_LIST="112.74.176.249:4181";

    private static final int SESSION_TIME_OUT=2000;

    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void createConnection(){
        Watcher watcher=new ConnectionWatcher(countDownLatch);
        try {
            zookeeper=new ZooKeeper(ZOOKEEPER_HOST_LIST,SESSION_TIME_OUT,watcher);
            countDownLatch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        if(zookeeper!=null){
            try {
                zookeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static ZooKeeper getZookeeper(){
        if(zookeeper!=null){
            if(zookeeper.getState().isAlive()){
                return zookeeper;
            }
            close();
        }
        createConnection();
        return zookeeper;
    }

}
