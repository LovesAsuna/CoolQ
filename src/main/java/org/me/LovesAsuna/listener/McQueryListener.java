package org.me.LovesAsuna.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.Mcquery;
import org.me.LovesAsuna.util.SRVConvert;

import java.io.IOException;

public class McQueryListener implements Listener {
    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (msg.startsWith("/mcquery ")) {
            String[] strings = msg.split(" ");
            String ipAndport = strings[1];
            boolean query = false;
            String NewipAndport = "";

            /*如果不含:则默认为srv记录*/
            if (!ipAndport.contains(":")) {
                if (qeuery(ipAndport + ":25565", fromGroup, false)) {
                    query = true;
                } else {
                    Main.CQ.sendGroupMsg(fromGroup, "正在尝试SRV解析");
                    NewipAndport = SRVConvert.convert(ipAndport);
                    if (NewipAndport == null) {
                        query = false;
                    } else {
                        query = qeuery(NewipAndport, fromGroup, true);
                    }
                }
            } else {
                query = qeuery(ipAndport, fromGroup, false);
            }

            if (!query) {
                if (!NewipAndport.equals(ipAndport) && !NewipAndport.isEmpty()) {
                    Main.CQ.sendGroupMsg(fromGroup, "无法获取Motd!" + "\n" +
                            "SRV结果为:" + "\n" +
                            NewipAndport);
                } else {
                    Main.CQ.sendGroupMsg(fromGroup, "ip地址不正确!");
                }
            }
            return true;
        }
        return true;
    }

    private String nodeProcess(JsonNode node) {
        String text = "";
        JsonNode color = node.get("color");
        JsonNode strikethrough = node.get("strikethrough");
        JsonNode bold = node.get("bold");
        if (color != null) {
            String colortext = color.asText();
            switch (colortext) {
                case "dark_gray":
                    text += "§7";
                    break;
                case "gray":
                    text += "§7";
                    break;
                case "aqua":
                    text += "§5";
                    break;
                case "white":
                    text += "";
                    break;
                case "green":
                    text += "§20";
                    break;
                case "light_purple":
                    text += "";
                    break;
                case "gold":
                    text += "";
                    break;
                case "yellow":
                    text += "";
                    break;
                default:
                    text += "";
            }
        }

        if (strikethrough != null) {
            text += "§n";
        }

        if (bold != null) {
            text += "§l";
        }
        return text += node.get("text").asText();
    }

    private boolean qeuery(String ipAndport, long fromGroup, boolean SRV) throws IOException {
        String host = ipAndport.split(":")[0];
        int port = Integer.parseInt(ipAndport.split(":")[1]);
        String json = null;
        try {
            json = Mcquery.query(host, port);
        } catch (IOException e) {
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        boolean mod = false;

        JsonNode root = objectMapper.readTree(json);
        JsonNode version = root.get("version");
        JsonNode players = root.get("players");
        JsonNode description = root.get("description");
        JsonNode modinfo = root.get("modinfo");

        JsonNode text = description.get("text");
        JsonNode extra = description.get("extra");
        JsonNode translate = description.get("translate");
        String texts = "";
        String mods = "";

        if (extra != null) {
            for (int i = 0; i <= extra.size()-1; i++) {
                JsonNode node = extra.get(i);
                texts += nodeProcess(node);
            }
        } else if (text != null) {
            texts = description.get("text").asText();
        } else if (translate != null) {
            texts = description.get("translate").asText();
        }

        if (modinfo != null) {
            JsonNode type = modinfo.get("type");
            JsonNode modList = modinfo.get("modList");
            mods += "\n" +
                    "服务器Mod类型: " + type.asText() +
                    modeProcess(modList);
        }
        Main.CQ.sendGroupMsg(fromGroup,
                "服务器IP:  " + host + ":" + port + "\n" +
                        "是否使用SRV域名解析:  " + SRV + "\n" +
                        "服务器版本:  " + version.get("name").asText() + "\n" +
                        "是否为mod服务器: " + mod + "\n" +
                        "目标服务器协议号码:  " + version.get("protocol").asText() + "\n" +
                        "最大在线人数:  " + players.get("max").asText() + "\n" +
                        "当前在线人数:  " + players.get("online").asText() + "\n" +
                        "MOTD:" + "\n" + texts +
                mods);
        return true;
    }

    private String modeProcess(JsonNode modList) {
        int size = modList.size();
        String mod = "(总计共" + (size-1) + "个Mod)";
        for (int i = 0; i <= size-1; i++) {
            JsonNode node = modList.get(i);
            String modid = node.get("modid").asText();
            String version = node.get("version").asText();
            mod += ("\n" + modid + ": " + version);
        }
        return mod;
    }
}
