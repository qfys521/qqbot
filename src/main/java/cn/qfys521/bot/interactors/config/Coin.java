/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `Coin.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Coin.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.config;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class Coin {
    HashMap<String, String> lastDate;
    HashMap<String, Long> coin;

    public long getCoinCount(String name) {
        return getCoin().getOrDefault(name, 0L);
    }

    public boolean getLastSign(String name) {
        return getLastDate().getOrDefault(name, "1970-01-01").equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    public void addLastCoin(String name, long c) {
        long coin = getCoinCount(name);
        getCoin().put(name, coin + c);
    }

    public void updateLastDate(String name) {
        getLastDate().put(name, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
