package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;

/**
 * @author icestar
 */
public class ShowDocListener implements Listener {

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) {

        if (msg.equals("/doc")) {
            Main.CQ.sendGroupMsg(fromGroup, "BukkitAPI - Javadoc:\n" +
                    "   1.7.10版(已过时):https://jd.bukkit.org/\n" +
                    "   Chinese_Bukkit: \n" +
                    "       1.12.2版: http://docs.zoyn.top/bukkitapi/1.12.2/\n" +
                    "       1.13+版: https://bukkit.windit.net/javadoc/\n" +
                    "   Spigot: https://hub.spigotmc.org/javadocs/spigot/\n" +
                    "   Paper: https://papermc.io/javadocs/paper/\n" +
                    "Sponge(不推荐): https://docs.spongepowered.org/stable/zh-CN/\n" +
                    "BungeeCord:\n" +
                    "       API: https://ci.md-5.net/job/BungeeCord/ws/api/target/apidocs/overview-summary.html\n" +
                    "       API-Chat: https://ci.md-5.net/job/BungeeCord/ws/chat/target/apidocs/overview-summary.html\n" +
                    "MCP Query: https://mcp.exz.me/\n" +
                    "NMS或ProtocolLib必要网站: https://wiki.vg ; https://wiki.vg/Protocol\n" +
                    "Java8: https://docs.oracle.com/javase/8/docs/api/overview-summary.html");
        }

        return true;
    }
}
