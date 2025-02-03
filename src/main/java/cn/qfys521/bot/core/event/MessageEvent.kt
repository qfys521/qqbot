/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `MessageEvent.kt`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `MessageEvent.kt` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */
package cn.qfys521.bot.core.event

import io.github.kloping.qqbot.api.message.MessageEvent


val MessageEvent<*, *>.originalContent: String
    get() = getMessage(this)


private fun getMessage(messageEvent: MessageEvent<*, *>): String {
    var rawMessage = messageEvent.rawMessage.content
    val message: String
    while (true) {
        if (rawMessage.startsWith(java.lang.String.format("<@!%s>", messageEvent.bot.info.id))) {
            rawMessage =
                rawMessage.replaceFirst(String.format(java.lang.String.format("<@!%s>", messageEvent.bot.info.id)), "")
        } else if (rawMessage.startsWith(" ")) {
            rawMessage = rawMessage.replaceFirst(" ", "")
        } else {
            message = rawMessage
            break
        }
    }

    return message
}

