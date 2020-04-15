package org.me.LovesAsuna.listener;

import org.me.LovesAsuna.Main;
import org.me.LovesAsuna.util.Listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DownloadImageListener implements Listener {
    private static int max = 0;
    private static int count = 1;
    private static String path = "C:/Users/Administrator/Desktop/image";
    static {
        init();
    }

    @Override
    public boolean execute(long fromGroup, long fromQQ, String msg) throws IOException {

        if (fromQQ == 625924077) {
            if (msg.equals("/photoinit")) {
                init();
                Main.CQ.sendPrivateMsg(625924077, Main.CC.at(fromQQ) + "捕捉器重启成功! 索引: " + getCount());
                return true;
            } else if (msg.equals("/getcount")) {
                Main.CQ.sendPrivateMsg(625924077, String.valueOf(getCount()));
                return true;
            } else if (msg.startsWith("/convert")) {
                Main.CQ.sendPrivateMsg(625924077, convert(msg.split(" ")[1]));
                return true;
            } else if (msg.startsWith("/sort")) {
                Main.CQ.sendPrivateMsg(625924077, sort(msg.split(" ")[1]));
                return true;
            }
        }

        /*如果图片不为空*/
        if (Main.CC.getCQImage(msg) != null) {
            /*如果图片大小大于650Kb*/
            if (Main.CC.getCQImage(msg).getSize() > 650000) {
                /*获取图片类型*/
                String type = Main.CC.getImage(msg).split("\\.")[1];
                if (!type.equalsIgnoreCase("null") && !type.equalsIgnoreCase("jpg") && !type.equalsIgnoreCase("png")) {
                    return false;
                }
                File file = new File(path);
                if (!file.exists()) {
                    Files.createDirectories(Paths.get(file.getPath()));
                }
                Main.CC.getCQImage(msg).download(file.getPath() + File.separator + (max+count) + ".png");
                count++;
            }
        }
        return true;
    }

    private static void init() {
        max = 0;
        try (Stream<Path> pathStream = Files.list(Paths.get(path))) {
            pathStream.forEach(p -> {
                int i = Integer.parseInt(p.toFile().getName().split("\\.")[0]);
                if (i>max) {
                    max = i;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getCount() {
        init();
        return max;
    }

    private static String convert(String suffix) {
        init();
        int count = 0;
        int mode = 1;
        if (suffix.equalsIgnoreCase("jpg")) {
            mode = 1;
        } else if (suffix.equalsIgnoreCase("png")) {
            mode = 2;
        }

        for (int i = 1; i <= max; i++) {
            String fileName = path + File.separator + i + ".";
            File file = new File(fileName + suffix);
            if (file.exists()) {
                if (mode == 1) {
                    if (file.renameTo(new File(fileName + "png"))) {
                        count ++;
                    }
                } else if (mode == 2) {
                    if (file.renameTo(new File(fileName + "jpg"))) {
                        count ++;
                    }
                }
            }
        }

        return mode == 1 ? "完成转换jpg → png " + count + " 张" : "完成转换png → jpg " + count + " 张";
    }

    private static String sort(String suffix) {
        int notExist = 1;
        boolean still = false;
        int add = 0;
        for (int i = 1; i <= max; i++) {
            String fileName = path + File.separator + i + ".";
            File file = new File(fileName + suffix);
            /*如果文件不存在*/
            if (!file.exists()) {
                if (!still) {
                    notExist = i;
                }
                still = true;
            } else {
                if (still) {
                    file.renameTo(new File(path + File.separator + (notExist + add) + "." + suffix));
                    add++;
                }
            }
        }
        return "成功整理 " + add + " 张" + suffix + "图片";
    }

}
