package org.me.LovesAsuna.listener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.LovesAsuna.util.HTTPConnect;
import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.BasicUtil;
import org.me.LovesAsuna.util.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BilibiliListener implements Listener {
    private final Pattern pattern = Pattern.compile("BV(\\d|[a-z]|[A-Z]){10}");

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {
        String av = null, bv = null;
        BufferedReader reader = null;

        if (msg.toLowerCase().contains("av")) {
            av = String.valueOf(BasicUtil.ExtraceInt(msg));
            reader = HTTPConnect.connect("https://api.bilibili.com/x/web-interface/view?aid=" + av);
        } else if (msg.contains("BV")) {
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                bv = matcher.group();
            } else {
                return false;
            }
            reader = HTTPConnect.connect("https://api.bilibili.com/x/web-interface/view?bvid=" + bv);
        }

        if (reader == null) {
            return false;
        }
        String line = reader.readLine();
        if (!line.startsWith("{\"code\":0")) {
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(line);
        JsonNode dataObject = jsonNode.get("data");

        String title = dataObject.get("title").asText();
        String description = dataObject.get("desc").asText();
        String UP = dataObject.get("owner").get("name").asText();
        String uplink = dataObject.get("owner").get("mid").asText();
        String zone = dataObject.get("tname").asText();
        JsonNode statObject = dataObject.get("stat");
        String view = statObject.get("view").asText();
        String Barrage = statObject.get("danmaku").asText();
        String reply = statObject.get("reply").asText();
        String fav = statObject.get("favorite").asText();
        String coin = statObject.get("coin").asText();
        String share = statObject.get("share").asText();
        String like = statObject.get("like").asText();
        String desc = dataObject.get("desc").asText();

        Main.CQ.sendGroupMsg(fromGroup, title + "\nUP: " + UP + "(https://space.bilibili.com/" + uplink + ")\n分区: "
                + zone + "\n播放量: " + view + " 弹幕: " + Barrage + " 评论: "
                + reply + "\n收藏: " + fav + " 投币: " + coin + " 分享: " + share + " 点赞: " + like + "\n" + desc);
        return true;
    }
}
