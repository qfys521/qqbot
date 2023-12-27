/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `Bot.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Bot.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core;


import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;

public class Bot {


    public static Starter login(String appid, String token, String secret) {
        Starter starter = new Starter(appid, token, secret);
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.and(Intents.GROUP_INTENTS));
        return starter;
    }

}