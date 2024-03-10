/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `JavaPlugin.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `JavaPlugin.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.plugin;

import lombok.Data;

public abstract class JavaPlugin {
    public abstract void onLoad();

    public void onEnable() {
    }
    public abstract JavaPlugin getInstance();

    public abstract void onDisable();
    public abstract PluginInformation getPluginInformation();
    @Data
    public class PluginInformation{
        String name;
        String[] authors;
        String author;
        String version;
        int versionCode;
        Object[] other;
    }
}
