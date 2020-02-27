package io.javac.minoss.minosscommon.enums.cache;

import io.javac.minoss.minosscommon.toolkit.DateUtils;

/**
 * @author pencilso
 * @date 2020/2/27 10:51 上午
 */
public enum CaffineType {

    bucket(1000, DateUtils.MillisecondConst.MINUTE * 10);


    private long expires;
    private int maximumSize;

    CaffineType(int maximumSize, long expires) {
        this.expires = expires;
        this.maximumSize = maximumSize;
    }

    public long getExpires() {
        return expires;
    }

    public long getMaximumSize() {
        return maximumSize;
    }
}
