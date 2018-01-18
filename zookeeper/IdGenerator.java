package nju.edu.cn.log.log_tracking.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by cong on 2018-01-15.
 */
public class IdGenerator {


    private static final String LOG_CONTEXT_PATH="/log_tracking";

    private static final String TRACE_ID_PATH="/trace_id-";

    private static final String SPAN_ID="/span_id-";

    public static long generateTraceId(){

        return 30;
    }

    public static long generateSpanId(){
        ZooKeeper zooKeeper=ZookeeperManager.getZookeeper();
        System.out.println(zooKeeper.getState());
        System.currentTimeMillis();
        try {
            if(zooKeeper.exists(LOG_CONTEXT_PATH,false)==null)
                System.out.println(zooKeeper.create(LOG_CONTEXT_PATH,"".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT));
            System.out.println(zooKeeper.create(LOG_CONTEXT_PATH+SPAN_ID,"".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 300;
    }

}
