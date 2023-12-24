/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `CommandRunner.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CommandRunner.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.command;

import cn.qfys521.bot.anotation.Command;
import io.github.kloping.qqbot.api.message.MessageReceiveEvent;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.impl.ListenerHost;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import static cn.qfys521.bot.BotApplication.getLogger;

public class CommandRunner {
    public static ListenerHost listenerHost = new ListenerHost() {
        @EventReceiver
        private void onEvent(GroupMessageEvent messageEvent) {
            ArrayList<Method> arrayList = RegisterCommand.methodArrayList;
            for (Method method : arrayList) {
//                getLogger().info("messageEvent.getMessage().get(0).toString().split(\" \")[1]\n"+messageEvent.getMessage().get(0).toString().split(" ")[1]);
//                getLogger().info("messageEvent.getMessage().get(0).toString()\n"+messageEvent.getMessage().get(0).toString());
                getLogger().info("messageEvent.getMessage().get(0).toString().replaceFirst(\" \" , \"\").split(\" \")[0]\n" + messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0]);
                if (Objects.equals(messageEvent.getMessage().get(0).toString().replaceFirst(" ", "").split(" ")[0], method.getAnnotation(Command.class).value())) {
                    try {
                        method.invoke(method.getDeclaringClass().newInstance(), messageEvent);
                    } catch (Exception e) {

                        StringBuffer stringBuffer = new StringBuffer();
                        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                            stringBuffer.append("\n").append(stackTraceElement.toString());
                        }
                        getLogger().error(e.toString() + stringBuffer);
                    }
                }
            }
        }

        @EventReceiver
        private void onMessage(MessageReceiveEvent messageEvent) {
            ArrayList<Method> arrayList = RegisterCommand.methodArrayList;
            for (Method method : arrayList) {
                if (Objects.equals(messageEvent.getMessage().get(1).toString().replaceFirst(" ", "").split(" ")[0], method.getAnnotation(Command.class).value())) {
                    try {
                        method.invoke(method.getDeclaringClass().newInstance(), messageEvent);
                    } catch (Exception e) {

                        StringBuffer stringBuffer = new StringBuffer();
                        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                            stringBuffer.append("\n").append(stackTraceElement.toString());
                        }
                        getLogger().error(e.toString() + stringBuffer);
                    }
                }
            }
        }
    };

}

