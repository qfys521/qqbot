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

import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.annotation.Usage;
import io.github.kloping.qqbot.api.v2.MessageV2Event;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.api.v2.FriendMessageEvent;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.impl.message.BaseMessageChannelReceiveEvent;

import java.lang.reflect.Method;
import java.util.*;


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
/*
        @EventReceiver
        private void onGroup(GroupMessageEvent messageEvent) {
            handleMessage(messageEvent);
        }

        @EventReceiver
        private void onChannel(BaseMessageChannelReceiveEvent messageEvent) {
            handlesMessage(messageEvent);
        }
        
        @EventReceiver
        private void onFriend(FriendMessageEvent messageEvent){
            handleMessage(messageEvent);
        }
        private void handleMessage(MessageV2Event messageEvent) {

            String message = messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0];
            Method method = commandMap.get(message);
            if (method != null) {
                try {
                    method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), messageEvent);
                } catch (Exception e) {
                    String usage = getUsage(method);
                    messageEvent.send(usage != null ? usage : "不正确的用法。" + e.toString());
                    throw new RuntimeException(e);
                }
            }
        }
    */
       @EventReceiver
        private void onQQMessage(MessageEvent messageEvent){
            handlesMessage(messageEvent);
        }

        @EventReceiver
        private void onChannelMessage(MessageEvent messageEvent){
            handleMessage(messageEvent);
        }

        private void handlesMessage(MessageEvent messageEvent) {

            String message = messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0];
            Method method = commandMap.get(message);
            if (method != null) {
                try {
                    method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), messageEvent);
                } catch (Exception e) {
                    String usage = getUsage(method);
                    messageEvent.send(usage != null ? usage : "不正确的用法。" + e.toString());
                    throw new RuntimeException(e);
                }
            }
        }

        private void handleMessage(MessageV2Event messageEvent) {

            String message = messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0];
            Method method = commandMap.get(message);
            if (method != null) {
                try {
                    method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), messageEvent);
                } catch (Exception e) {
                    String usage = getUsage(method);
                    messageEvent.send(usage != null ? usage : "不正确的用法。" + e.toString());
                    throw new RuntimeException(e);
                }
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
