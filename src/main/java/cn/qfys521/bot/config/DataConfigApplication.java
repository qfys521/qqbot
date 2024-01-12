/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `DataConfigApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `DataConfigApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.config;

public class DataConfigApplication extends ConfigApplication {
    public DataConfigApplication(Object t, String fileName) {
        super(t, "data/data/" + t.getClass().getPackageName() + "/" + fileName);
    }
}
