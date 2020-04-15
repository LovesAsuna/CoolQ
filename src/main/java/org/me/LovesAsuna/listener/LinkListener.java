package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.util.JudgeEnable;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.isAlive;

import java.io.*;
import java.net.Socket;

public class LinkListener implements Listener {
    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (!JudgeEnable.judegeEnable(fromGroup, fromQQ, "link")) {
            return false;
        }

        if (msg.equals("/link")) {
            BotData.setSocket(new Socket("localhost", 1234));
            BotData.getPool().submit(() -> {

                while (true) {
                    InputStream inputStream = BotData.getSocket().getInputStream();

                    DataInputStream dataInputStream = new DataInputStream(inputStream);

                    String line = "";
                    try {
                        line = dataInputStream.readUTF();
                    } catch (IOException e) {

                    }

                    if (line.equalsIgnoreCase("命令执行成功!") && BotData.getId() != 0) {
                        Main.CQ.sendPrivateMsg(BotData.getId(), line);
                        BotData.setId(0);
                    }

                }




            });

            Main.CQ.sendGroupMsg(fromGroup, "与服务器建立连接成功");



        } else if (msg.equals("/link status")) {
            Main.CQ.sendGroupMsg(fromGroup, "———————\n连接状态: " + isAlive.judge());
        }

        return true;
    }
}
