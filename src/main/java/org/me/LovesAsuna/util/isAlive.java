package org.me.LovesAsuna.util;

import org.me.LovesAsuna.data.BotData;

import java.io.IOException;

public class isAlive {
    public static boolean judge() {

        if (BotData.getSocket() == null) {
            return false;
        }

        try {
            BotData.getSocket().sendUrgentData(0xFF);
        } catch (IOException e) {
            return false;
        }

        return true;

    }
}
