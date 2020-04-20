package org.me.LovesAsuna.util;

import lombok.Getter;

/**
 * @author LovesAsuna
 * @date 2020/4/19 14:09
 */

public class Tuple<K,V> {
    @Getter
    private final K first;
    @Getter
    private final V second;
    public Tuple(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
