package org.me.LovesAsuna.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.LovesAsuna.util.HTTPConnect;
import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;

import java.io.BufferedReader;
import java.io.IOException;

public class HitokotoListener implements Listener {

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {
        if (msg.startsWith("/一言")) {
            BufferedReader reader;
            String[] strings = msg.split(" ");
            ObjectMapper mapper = new ObjectMapper();
            /*如果不带参数,默认全部获取*/
            if (strings.length == 1) {
                reader = HTTPConnect.connect("https://v1.hitokoto.cn/");
                String string = null;
                String text = "";
                while ((string = reader.readLine())!=null) {
                    text += string;
                }

                JsonNode object = mapper.readTree(text);
                String hitokoto = object.get("hitokoto").asText();
                String from = object.get("from").asText();

                Main.CQ.sendGroupMsg(fromGroup, "『 " + hitokoto + " 』- 「" + from + "」");
            }
            /*如果长度为2*/
            if (strings.length == 2) {
                if ("help".equalsIgnoreCase(strings[1])) {
                    Main.CQ.sendGroupMsg(fromGroup, "一言参数: \n" +
                            "a\tAnime - 动画\n" +
                            "b\tComic – 漫画\n" +
                            "c\tGame – 游戏\n" +
                            "d\tNovel – 小说\n" +
                            "e\tMyself – 原创\n" +
                            "f\tInternet – 来自网络\n" +
                            "g\tOther – 其他\n" +
                            "不填 - 随机");
                } else {
                    reader = HTTPConnect.connect("https://v1.hitokoto.cn/?c=" + strings[1]);
                    String string = null;
                    String text = "";
                    while ((string = reader.readLine())!=null) {
                        text += string;
                    }

                    JsonNode object = mapper.readTree(text);
                    String hitokoto = object.get("hitokoto").asText();
                    String from = object.get("from").asText();

                    Main.CQ.sendGroupMsg(fromGroup, "『 " + hitokoto + " 』- 「" + from + "」");
                }
            }

        }
        return true;
    }
}
