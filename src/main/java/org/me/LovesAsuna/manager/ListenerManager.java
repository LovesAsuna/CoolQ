package org.me.LovesAsuna.manager;

import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.listener.*;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.MyThreadFactory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListenerManager {
    private static final ThreadPoolExecutor pool;

    static {
        pool = new ThreadPoolExecutor(5,10,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(5), new MyThreadFactory("[BotThread]"));
    }

    public static void registerListener() {
        Listener[] listeners = {
                new ShowDocListener(), new McQueryListener(),
                new BilibiliListener(), new HitokotoListener(),
                new DeBugListener(), new DownloadImageListener(),
                new RainbowSixListener()};
        for (Listener listener : listeners) {
            BotData.getListeners().add(listener);
        }
    }

    public static void callEvent(long fromGroup, long fromQQ, String msg) {
        for (Listener listener : BotData.getListeners()) {
            pool.submit(() -> {
                try {
                    listener.execute(fromGroup, fromQQ, msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static void callEvent(long fromQQ, String msg) {
        Listener listener = new DownloadImageListener();
        try {
            listener.execute(0, fromQQ, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
