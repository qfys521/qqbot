/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `CoreConfigApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CoreConfigApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.config;

public class CoreConfigApplication extends ConfigApplication {
    public CoreConfigApplication(Object t, String fileName) {
        super(t, "data/core/" + t.getClass().getPackageName() + "/" + fileName);
    }
}
