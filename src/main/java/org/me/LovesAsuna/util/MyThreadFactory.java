package org.me.LovesAsuna.util;

import java.util.concurrent.ThreadFactory;

/**
 * @author LovesAsuna
 * @date 2020/4/21 11:08
 */

public class MyThreadFactory implements ThreadFactory {
    private final String threadName;

    public MyThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
