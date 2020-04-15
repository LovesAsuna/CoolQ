package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.util.Listener;

import java.io.IOException;

public class DeBugListener implements Listener {
    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (fromQQ == 625924077 || fromQQ == 2122723273) {
            if (msg.equalsIgnoreCase("/debug")) {

            }

        }
        return true;
    }
}
