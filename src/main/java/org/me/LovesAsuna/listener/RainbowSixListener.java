package org.me.LovesAsuna.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.NetWorkUtil;
import org.me.LovesAsuna.util.Tuple;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LovesAsuna
 * @date 2020/4/19 20:17
 */

public class RainbowSixListener implements Listener {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) {
        if (fromGroup != 797764751 && fromGroup != 343234268) {
            return false;
        }


        if (!msg.startsWith("R6 ") && !msg.startsWith("r6 ")) {
            return false;
        }

        String[] strings = msg.split(" ");
        if (strings.length == 4) {
            String username = strings[1];
            String uuid = getUUID(username, fromGroup);
            String operator = strings[3];
            operatorCheck(uuid, operator, fromGroup);
            return true;
        } else if (strings.length == 2) {
            String username = strings[1];
            String uuid = getUUID(username, fromGroup);
            normalCheck(uuid, fromGroup);
            return true;
        }

        return false;
    }

    private void normalCheck(String uuid, long fromGroup) {
        if (uuid == null) {
            return;
        }
        long start = System.currentTimeMillis();
        Tuple<InputStream, Integer> result = NetWorkUtil.fetch("https://r6stats.com/api/stats/" + uuid);


        JsonNode root = null;
        try {
            InputStream inputStream = result.getFirst();
            root = mapper.readTree(inputStream);
        } catch (IOException | NullPointerException e) {
            Main.CQ.sendGroupMsg(fromGroup, "玩家不存在或数据未被初始化！");
            return;
        }

        String lastUpdated = root.get("last_updated").asText();

        String userName = root.get("aliases").get(0).get("username").asText();
        String lastSeen = root.get("aliases").get(0).get("last_seen_at").asText();

        String level = root.get("progression").get("level").asText();
        String lootboxProbability = root.get("progression").get("lootbox_probability").asText();

        String assists = root.get("stats").get(0).get("general").get("assists").asText();
        String barricadesDeployed = root.get("stats").get(0).get("general").get("barricades_deployed").asText();
        String blindKills = root.get("stats").get(0).get("general").get("blind_kills").asText();
        String kills = root.get("stats").get(0).get("general").get("kills").asText();
        String deaths = root.get("stats").get(0).get("general").get("deaths").asText();
        String distanceTravelled = root.get("stats").get(0).get("general").get("distance_travelled").asText();
        String gadgetsDestroyed = root.get("stats").get(0).get("general").get("gadgets_destroyed").asText();
        String gamesPlayed = root.get("stats").get(0).get("general").get("games_played").asText();
        String headshots = root.get("stats").get(0).get("general").get("headshots").asText();
        String kd = root.get("stats").get(0).get("general").get("kd").asText();
        String losses = root.get("stats").get(0).get("general").get("losses").asText();
        String meleeKills = root.get("stats").get(0).get("general").get("melee_kills").asText();
        String reinforcementsDeployed = root.get("stats").get(0).get("general").get("reinforcements_deployed").asText();
        String revives = root.get("stats").get(0).get("general").get("revives").asText();
        String suicides = root.get("stats").get(0).get("general").get("suicides").asText();
        String wins = root.get("stats").get(0).get("general").get("wins").asText();
        String wl = root.get("stats").get(0).get("general").get("wl").asText();


        StringBuilder builder = new StringBuilder();
        builder.append("数据更新时间: " + lastUpdated)
                .append("\n")
                .append("用户名: " + userName)
                .append(" ")
                .append("等级: " + level)
                .append(" ")
                .append("游戏场数: " + gamesPlayed)
                .append(" ")
                .append("战利品几率: " + lootboxProbability)
                .append("\n")
                .append("育碧用户识别码: " + uuid)
                .append("\n")
                .append("上次游玩时间: " + lastSeen)
                .append("\n")
                .append("胜利: " + wins)
                .append(" ")
                .append("失败: " + losses)
                .append(" ")
                .append("K/D: " + kd)
                .append(" ")
                .append("W/L: " + wl)
                .append("\n")
                .append("击杀: " + kills)
                .append(" ")
                .append("助攻: " + assists)
                .append(" ")
                .append("盲杀: " + blindKills)
                .append(" ")
                .append("爆头: " + headshots)
                .append(" ")
                .append("刀杀: " + meleeKills)
                .append(" ")
                .append("被救起: " + revives)
                .append("\n")
                .append("死亡: " + deaths)
                .append(" ")
                .append("自杀: " + suicides)
                .append(" ")
                .append("路障部署: " + barricadesDeployed)
                .append(" ")
                .append("防御部署" + reinforcementsDeployed)
                .append("\n")
                .append("装置摧毁: " + gadgetsDestroyed)
                .append(" ")
                .append("行走距离: " + distanceTravelled)
                .append("\n");

        long end = System.currentTimeMillis();
        Main.CQ.sendGroupMsg(fromGroup, builder.append(String.format("查询耗时%.2f秒", (double) (end - start) / 1000)).toString());
    }

    private void operatorCheck(String uuid, String operator, long fromGroup) {
        if (uuid == null) {
            return;
        }
        long start = System.currentTimeMillis();
        Tuple<InputStream, Integer> result = NetWorkUtil.fetch("https://r6stats.com/api/stats/" + uuid);

        InputStream inputStream = result.getFirst();
        JsonNode root = null;
        try {
            root = mapper.readTree(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userName = root.get("aliases").get(0).get("username").asText();
        JsonNode operators = root.get("operators");
        int size = operators.size();

        List<String> paths = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            paths.add(operators.get(i).get("operator").get("name").asText().toLowerCase());
        }

        int i = paths.indexOf(operator.toLowerCase());
        if (i == -1) {
            Main.CQ.sendGroupMsg(fromGroup, "该干员不存在或玩家从未使用过!");
            return;
        }
        String kills = operators.get(i).get("kills").asText();
        String deaths = operators.get(i).get("deaths").asText();
        String kd = operators.get(i).get("kd").asText();
        String wins = operators.get(i).get("wins").asText();
        String losses = operators.get(i).get("losses").asText();
        String wl = operators.get(i).get("wl").asText();
        String headshots = operators.get(i).get("headshots").asText();
        String melee_kills = operators.get(i).get("melee_kills").asText();
        String ctu = operators.get(i).get("operator").get("ctu").asText();

        StringBuilder builder = new StringBuilder();
        builder.append("用户名: " + userName)
                .append("\n")
                .append("干员: " + operator)
                .append(" ")
                .append("组织: " + ctu)
                .append("\n")
                .append("击杀: " + kills)
                .append(" ")
                .append("爆头: " + headshots)
                .append(" ")
                .append("刀杀: " + melee_kills)
                .append(" ")
                .append("死亡: " + deaths)
                .append(" ")
                .append("K/D: " + kd)
                .append("\n")
                .append("胜利: " + wins)
                .append(" ")
                .append("失败: " + losses)
                .append(" ")
                .append("W/L" + wl)
                .append("\n");
        long end = System.currentTimeMillis();
        Main.CQ.sendGroupMsg(fromGroup, builder.append(String.format("查询耗时%.2f秒", (double) (end - start) / 1000)).toString());
    }

    private String getUUID(String userName, long fromGroup) {
        try {
            Tuple<InputStream, Integer> result = NetWorkUtil.fetch(String.format("https://r6stats.com/api/player-search/%s/pc", userName));
            InputStream inputStream = result.getFirst();
            JsonNode root = mapper.readTree(inputStream);
            return root.get(0).get("ubisoft_id").asText();
        } catch (IOException | NullPointerException e) {
            Main.CQ.sendGroupMsg(fromGroup, "玩家不存在或数据未被初始化！");
            return null;
        }
    }

}
