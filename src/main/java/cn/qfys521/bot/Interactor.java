/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `Interactor.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Interactor.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot;

import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.event.MessageEventKt;
import io.github.kloping.qqbot.api.message.MessageEvent;

public class Interactor {
    @Command("hello")
    public void hello(MessageEvent messageEvent) {
        messageEvent.send("hello world");
        MessageEventKt.getOriginalContent(messageEvent);
    }


}
