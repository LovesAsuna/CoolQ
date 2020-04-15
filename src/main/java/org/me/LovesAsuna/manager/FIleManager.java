package org.me.LovesAsuna.manager;

import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.enumeration.PathType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FIleManager {

    public static void createFolder() throws IOException {
        for (long group : BotData.getGroups()) {
            File file = new File(String.valueOf(group));
            Path path = Paths.get(PathType.FILES_PATHS.getPath() + group);
            /*如果文件不存在*/
            if (!file.exists()) {
                Files.createDirectories(path);
                createListenerSettingFolder(path);
            }
        }

    }

    private static void createListenerSettingFolder(Path path) throws IOException {
        String[] listeners = {"mute", "runcommand", "showdoc", "playerlist", "link"};
        for (String listener : listeners) {
            Path listenerPath = Paths.get(path.toFile().getPath() + "\\" + listener);
            if (listenerPath.toFile().exists()) {
                continue;
            }
            Files.createDirectory(listenerPath);
            createUsers(listenerPath);
            createEnable(listenerPath);
        }

    }

    private static void createUsers(Path path) throws IOException {
        File file = new File(path.toFile().getPath() + "\\users.ini");
        if (file.exists()) {
            return;
        }
        file.createNewFile();
    }

    private static void createEnable(Path path) throws IOException{
        File file = new File(path.toFile().getPath() + "\\enable.ini");
        if (file.exists()) {
            return;
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write("true");
        writer.flush();
        writer.close();
    }

}
