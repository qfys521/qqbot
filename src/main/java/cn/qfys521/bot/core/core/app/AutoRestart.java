/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `AutoRestart.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `AutoRestart.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.core.app;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.network.AuthAndHeartbeat;
import io.github.kloping.qqbot.network.WebSocketListener;
import io.github.kloping.spt.annotations.AutoStand;
import org.java_websocket.client.WebSocketClient;

public class AutoRestart implements WebSocketListener {

    @AutoStand
    AuthAndHeartbeat authAndHeartbeat;

    @AutoStand
    Starter starter;

    @Override
    public boolean onError(WebSocketClient client, Exception e) {
        //尝试重连
        authAndHeartbeat.identifyConnect(0, client);
        return WebSocketListener.super.onError(client, e);
    }


}