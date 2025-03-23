/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `Coin.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Coin.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */
package cn.qfys521.bot.plugin.qfPlugin.config;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coin {
    // 基础金币数据
    public ConcurrentHashMap<String, Integer> coin = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Long> lastDate = new ConcurrentHashMap<>();

    // 抽奖扩展字段
    public ConcurrentHashMap<String, Integer> failCount = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Long> lastGuaranteeTime = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Integer> failThreshold = new ConcurrentHashMap<>();

    // 初始化方法
    public Coin() {
    }

    synchronized public int getCoinCount(String name) {
        return coin.getOrDefault(name, 0);
    }

    synchronized public boolean getLastSign(String name) {
        Long last = lastDate.get(name);
        if (last == null) return false;
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(last))
                .equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    synchronized public void addCoin(String name, int c) {
        coin.put(name, getCoinCount(name) + c);
    }

    synchronized public void updateLastDate(String name) {
        lastDate.put(name, System.currentTimeMillis());
    }
}