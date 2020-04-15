package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;
import org.me.LovesAsuna.util.BasicUtil;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FunListener implements Listener {
    private static boolean enableajiao = false;
    private static boolean enablejohn = false;
    private static int timeajiao = 1000;
    private static int timejohn = 1000;
    private static String delayajiao = "h";
    private static String delayjohn = "d";
    private static boolean firstajiao = false;
    private static boolean firstjohn = false;
    private static ScheduledExecutorService ajiao = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledExecutorService john = Executors.newSingleThreadScheduledExecutor();


    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (fromGroup == 343234268 || fromGroup == 797764751) {
            if (msg.startsWith("/call")) {
                String[] strings = msg.split(" ");
                if (strings[1].equals("set")) {
                    int time = BasicUtil.ExtraceInt(strings[3]);
                    String delay = "d";
                    if (strings[3].contains("s")) {
                        delay = "s";
                    } else if (strings[3].contains("m")) {
                        delay = "m";
                    } else if (strings[3].contains("h")) {
                        delay = "h";
                    } else if (strings[3].contains("d")) {
                        delay = "d";
                    }

                    if (strings[2].equalsIgnoreCase("阿娇")) {
                        if (firstajiao) {
                            ajiao.shutdown();
                            ajiao = null;
                            ajiao = Executors.newSingleThreadScheduledExecutor();
                        }
                        firstajiao = false;
                        timeajiao = time;
                        delayajiao = delay;
                        Main.CQ.sendGroupMsg(fromGroup, "当前阿娇呼叫器间隔: " + timeajiao + delayajiao + "\n启用状态: " + enableajiao);
                    } else if (strings[2].equalsIgnoreCase("john")) {
                        if (firstjohn) {
                            john.shutdown();;
                            john = null;
                            john = Executors.newSingleThreadScheduledExecutor();
                        }
                        firstjohn = false;
                        timejohn = time;
                        delayjohn = delay;
                        Main.CQ.sendGroupMsg(fromGroup, "当前john呼叫器间隔: " + timejohn + delayjohn + "\n启用状态: " + enablejohn);
                    } else {
                        Main.CQ.sendGroupMsg(fromGroup, "命令参数错误!");
                    }
                } else if (strings[1].equals("enable")) {
                    if (strings[2].equalsIgnoreCase("阿娇")) {
                        if (enableajiao) {
                            enableajiao = false;
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇呼叫器已关闭");
                        } else {
                            enableajiao = true;
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇呼叫器已开启");
                        }
                    } else if (strings[2].equalsIgnoreCase("john")) {
                        if (enablejohn) {
                            enablejohn = false;
                            Main.CQ.sendGroupMsg(fromGroup, "john呼叫器已关闭");
                        } else {
                            enablejohn = true;
                            Main.CQ.sendGroupMsg(fromGroup, "john呼叫器已开启");
                        }
                    } else {
                        Main.CQ.sendGroupMsg(fromGroup, "命令参数错误!");
                    }
                } else {
                    Main.CQ.sendGroupMsg(fromGroup, "命令参数错误!");
                }

            }

            if (!firstajiao && enableajiao) {
                if (delayajiao.equals("s")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enableajiao) {
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇出来打电动了");
                        }
                        }, 0, timeajiao, TimeUnit.SECONDS);
                } else if (delayajiao.equals("m")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enableajiao) {
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇出来打电动了");
                        }
                        }, 0, timeajiao, TimeUnit.MINUTES);
                } else if (delayajiao.equals("h")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enableajiao) {
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇出来打电动了");
                        }
                        }, 0, timeajiao, TimeUnit.HOURS);
                } else if (delayajiao.equals("d")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enableajiao) {
                            Main.CQ.sendGroupMsg(fromGroup, "阿娇出来打电动了");
                        }
                        }, 0, timeajiao, TimeUnit.DAYS);
                }
                firstajiao = true;
            }

            if (!firstjohn && enablejohn) {
                if (delayajiao.equals("s")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enablejohn) {
                            Main.CQ.sendGroupMsg(fromGroup, "john出来打电动了");
                        }
                        }, 0, timejohn, TimeUnit.SECONDS);
                } else if (delayajiao.equals("m")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enablejohn) {
                            Main.CQ.sendGroupMsg(fromGroup, "john出来打电动了");
                        }
                        }, 0, timejohn, TimeUnit.MINUTES);
                } else if (delayajiao.equals("h")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enablejohn) {
                            Main.CQ.sendGroupMsg(fromGroup, "john出来打电动了");
                        }
                        }, 0, timejohn, TimeUnit.HOURS);
                } else if (delayajiao.equals("d")) {
                    ajiao.scheduleAtFixedRate(() -> {
                        if (enablejohn) {
                            Main.CQ.sendGroupMsg(fromGroup, "john出来打电动了");
                        }
                    }, 0, timejohn, TimeUnit.DAYS);
                }
            }
            firstjohn = true;
        }


        return true;
    }
}
