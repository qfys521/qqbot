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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Getter
@Setter
public class Coin {
    HashMap<String, Long> coin = new HashMap<>();
    HashMap<String, String> lastDate = new HashMap<>();

    public Coin() {
        coin = new HashMap<>();
        lastDate = new HashMap<>();
    }

    public long getCoinCount(String name) {
        if (coin == null) {
            return 0;
        }
        return coin.getOrDefault(name, 0L);
    }

    public boolean getLastSign(String name) {
        Optional<HashMap<String, String>> lastDateOptional = Optional.ofNullable(lastDate);
        Optional<String> lastDateStringOptional = lastDateOptional.map(m -> m.getOrDefault(name, "1970-01-01"));
        String lastDate = lastDateStringOptional.orElse("");
        return lastDate.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }


    public void addLastCoin(String name, long c) {
        if (coin == null) {
            coin = new HashMap<>(); // 初始化 coin HashMap
        }
        long coinCount = getCoinCount(name);
        coin.put(name, coinCount + c);
    }

    public void updateLastDate(String name) {
        if (lastDate == null) {
            lastDate = new HashMap<>(); // 初始化 lastDate HashMap
        }
        lastDate.put(name, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
