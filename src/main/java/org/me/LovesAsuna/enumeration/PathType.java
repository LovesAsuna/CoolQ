package org.me.LovesAsuna.enumeration;

import lombok.Getter;
import org.me.LovesAsuna.Main;

/**
 * @author LovesAsuna
 */

public enum PathType {

    /*数据文件存放路径*/
    FILES_PATHS(Main.CQ.getAppDirectory() + "\\");


    @Getter String path;

    private PathType(String path) {
        this.path = path;
    }

    public String getUsers(long group, String listener) {
        return PathType.FILES_PATHS.getPath() + group + "\\" + listener + "\\users.ini";
    }

    public String getEnable(long group, String listener) {
        return PathType.FILES_PATHS.getPath() + group + "\\" + listener + "\\enable.ini";
    }
}
