/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `Coin.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Coin.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.interactors.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.util.Optional;

import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coin {
    public ConcurrentHashMap<String, Long> coin;
    public ConcurrentHashMap<String, Long> lastDate;

    public Coin() {
        coin = new ConcurrentHashMap<>();
        lastDate = new ConcurrentHashMap<>();
    }

    synchronized public long getCoinCount(String name) {
        if (coin == null) {
            return 0;
        }
        return coin.getOrDefault(name, 0L);
    }

    synchronized public boolean getLastSign(String name) {
        Optional<ConcurrentHashMap<String, Long>> lastDateOptional = Optional.ofNullable(lastDate);
        Optional<Long> lastDateStringOptional = lastDateOptional.map(m -> m.getOrDefault(name, 0L));
        Long lastDate = lastDateStringOptional.orElse(0L);
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(lastDate))
                .equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    synchronized public void addCoin(String name, long c) {
        if (coin == null) {
            coin = new ConcurrentHashMap<>(); // 初始化 coin ConcurrentHashMap
        }
        long coinCount = getCoinCount(name);
        coin.put(name, coinCount + c);
    }

    synchronized public void updateLastDate(String name) {
        if (lastDate == null) {
            lastDate = new ConcurrentHashMap<>(); // 初始化 lastDate ConcurrentHashMap
        }
        lastDate.put(name, System.currentTimeMillis());
    }
}
