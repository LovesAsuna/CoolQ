package org.me.LovesAsuna.manager;

import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.listener.*;
import org.me.LovesAsuna.util.Listener;

import java.io.IOException;

public class ListenerManager {

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
            try {
                listener.execute(fromGroup, fromQQ, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
