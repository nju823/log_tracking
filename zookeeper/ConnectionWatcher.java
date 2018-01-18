package nju.edu.cn.log.log_tracking.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * Created by cong on 2018-01-16.
 */
public class ConnectionWatcher implements Watcher{

    private CountDownLatch countDownLatch;

    public ConnectionWatcher(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            countDownLatch.countDown();
        }

        if(Event.KeeperState.Expired==watchedEvent.getState()){
            ZookeeperManager.createConnection();
        }
    }
}
