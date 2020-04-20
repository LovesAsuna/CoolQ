package org.me.LovesAsuna.util;

import java.io.IOException;

/**
 * @author LovesAsuna
 */
public interface Listener {
    /**
     *
     * @param fromGroup 来自群
     * @param fromQQ 来自QQ
     * @param msg 来自消息
     * @return 是否调用成功
     */
    boolean execute(long fromGroup, long fromQQ, String msg) throws IOException;
}
