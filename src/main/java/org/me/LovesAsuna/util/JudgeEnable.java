package org.me.LovesAsuna.util;

import org.me.LovesAsuna.data.BotData;
import org.me.LovesAsuna.enumeration.PathType;

import java.io.IOException;

public class JudgeEnable {
    public static boolean judegeEnable(long fromGroup, long fromQQ, String listener) throws IOException {
        /*如果不是启用小组则返回*/
        boolean isGroup = false;
        for (long group : BotData.getGroups()) {
            if (fromGroup == group) {
                isGroup = true;
            }
        }

        /*如果群未启用功能则返回*/
        boolean isEnable = false;
        if (BasicUtil.getEnable(PathType.FILES_PATHS.getEnable(fromGroup, listener))) {
            isEnable = true;
        }

        /*如果不是users则返回*/
        boolean isAdmin = false;
        for (long qq : BasicUtil.getUserList(PathType.FILES_PATHS.getUsers(fromGroup, listener))) {
            if (fromQQ == qq) {
                isAdmin = true;
            }
        }

        return isAdmin&&isEnable&&isGroup;
    }
}
