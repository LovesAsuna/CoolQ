package org.me.LovesAsuna.listener;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.BasicUtil;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.NetWorkUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            InputStream inputStream = NetWorkUtil.fetch("https://api.bilibili.com/x/web-interface/view?aid=" + av).getFirst();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } else if (msg.contains("BV")) {
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                bv = matcher.group();
            } else {
                return false;
            }
            InputStream inputStream = NetWorkUtil.fetch("https://api.bilibili.com/x/web-interface/view?bvid=" + bv).getFirst();
            reader = new BufferedReader(new InputStreamReader(inputStream));
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

        StringBuilder builder = new StringBuilder(title);
        builder.append("\nUP: ")
                .append(UP)
                .append("(https://space.bilibili.com/")
                .append(uplink)
                .append(")\n分区: ")
                .append(zone)
                .append("\n播放量: ")
                .append(view)
                .append(" 弹幕: ")
                .append(Barrage)
                .append(" 评论: ")
                .append(reply)
                .append("\n收藏: ")
                .append(fav)
                .append(" 投币: ")
                .append(coin)
                .append(" 分享: ")
                .append(share)
                .append(" 点赞: ")
                .append(like)
                .append("\n")
                .append(desc);
        Main.CQ.sendGroupMsg(fromGroup, builder.toString());
        return true;
    }
}
