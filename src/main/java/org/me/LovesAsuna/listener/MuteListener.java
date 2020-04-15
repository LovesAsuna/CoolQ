package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.BasicUtil;
import org.me.LovesAsuna.util.JudgeEnable;
import org.me.LovesAsuna.util.Listener;

import java.io.IOException;

public class MuteListener implements Listener {

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

       if (!JudgeEnable.judegeEnable(fromGroup, fromQQ, "mute")) {
           return false;
       }

        /*格式:/mute qq号 时间*/
        String[] strings = null;
        if (msg.contains("/mute")) {
            strings = msg.split(" ");
            if (strings.length != 3) {
                return false;
            }
            if (!strings[1].contains("CQ:at")) {
                Main.CQ.setGroupBan(fromGroup, Long.parseLong(strings[1]), Long.parseLong(strings[2]));
            } else {
                Main.CQ.setGroupBan(fromGroup, BasicUtil.getUID(strings[1]), Long.parseLong(strings[2]));
            }
        }
        return true;
    }
}
