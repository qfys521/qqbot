/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `MessageEvent.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `MessageEvent.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */
package cn.qfys521.bot.event

import io.github.kloping.qqbot.api.message.MessageEvent



val MessageEvent<*,*>.originalContent: String
    get() =  if (message[0].toString()  == String.format("<@!%s>" , sender.bot.id)) message[1].toString() else message[0].toString()

