package org.me.LovesAsuna.data;

import lombok.Getter;
import lombok.Setter;
import org.me.LovesAsuna.util.Listener;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BotData {

    static {
        listeners = new ArrayList<>();
        pool= Executors.newFixedThreadPool(5);
        schedulepool = Executors.newScheduledThreadPool(5);
    }

    /*监听器列表*/
    @Getter private static List<Listener> listeners;

    /*投票模式*/
    @Getter @Setter private static Boolean vote = false;

    /*线程池*/
    @Getter private static ExecutorService pool;

    /*计划线程池*/
    @Getter private static ScheduledExecutorService schedulepool;

    /*使用机器人的群聊*/
    @Getter @Setter private static List<Long> groups;

    /*与服务器之间的socket*/
    @Getter @Setter private static Socket socket;

    /*自身文件路径*/
    @Getter @Setter private static String filePath;

    /*成功发送命令的人*/
    @Getter @Setter private static long id;
}
