package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.util.JudgeEnable;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.isAlive;

import java.io.*;
import java.util.Base64;

public class RunServerCommandListener implements Listener {

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (!JudgeEnable.judegeEnable(fromGroup, fromQQ, "runcommand")) {
            return false;
        }

        if (msg.startsWith("/rsc ")) {

            /*新建线程*/
            BotData.getPool().submit(() -> {
                try {
                    if (!isAlive.judge()) {
                        Main.CQ.sendGroupMsg(fromGroup, "还未与服务器建立连接!");
                        return;
                    }
                    InputStream inputStream = BotData.getSocket().getInputStream();
                    OutputStream outputStream = BotData.getSocket().getOutputStream();

                    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                    dataOutputStream.writeUTF(Base64.getEncoder().encodeToString(msg.getBytes()));

                    BotData.setId(fromQQ);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        }



        return true;
    }


}
