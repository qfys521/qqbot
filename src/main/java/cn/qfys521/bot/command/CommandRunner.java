/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `CommandRunner.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CommandRunner.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.command;

import static cn.qfys521.bot.BotApplication.cause;
import cn.qfys521.bot.SendEmail;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.annotation.Usage;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.impl.message.BaseMessageChannelReceiveEvent;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class CommandRunner {
    public static final ListenerHost listenerHost = new ListenerHost() {
        private final Map<String, Method> commandMap;

        {
            Map<String, Method> map = new HashMap<>();
            for (Method method : RegisterCommand.methodArrayList) {
                for (String command : method.getAnnotation(Command.class).value()) {
                    map.put(command, method);
                }
            }
            commandMap = Collections.unmodifiableMap(map);
        }

        @EventReceiver
        private void handlesMessage(BaseMessageChannelReceiveEvent messageEvent) {

            var message = messageEvent.getMessage().get(1).toString().split(" ")[1];
            sendMessage(messageEvent, message);


        }


        @EventReceiver
        private void handleMessage(MessageEvent messageEvent) {

            String message = messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0];
            sendMessage(messageEvent, message);

        }

        private void sendMessage(MessageEvent messageEvent, String message) {
            Method method = commandMap.get(message);
            if (method == null) return;
            try {
                method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), messageEvent);
            } catch (Exception e) {
                String usage = getUsage(method);
                messageEvent.send(usage != null ? usage : "不正确的用法。" + e.toString());
                SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            }
        }

        private String getUsage(Method method) {
            if (method.getAnnotation(Usage.class) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("用法: ").append("\n");
                for (String usageLine : method.getAnnotation(Usage.class).value()) {
                    sb.append(usageLine).append("\n");
                }
                return sb.toString();
            }
            return null;
        }
    };
}
