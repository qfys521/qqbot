/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `CoreInteractors.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CoreInteractors.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core;

import cn.qfys521.bot.annotation.Author;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.command.RegisterCommand;
import cn.qfys521.bot.event.MessageEventKt;
import io.github.kloping.qqbot.api.message.MessageEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

@Author("qfys521")
public class CoreInteractors {
    @Command({"/help", "/帮助", "/菜单"})
    public void helpMenu(MessageEvent<?, ?> messageEvent) {
        ArrayList<Method> method = RegisterCommand.methodArrayList;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("指令菜单");
        for (Method methods : method) {
            Command command = methods.getAnnotation(Command.class);
            if (command != null) {
                if (command.inCommandList()) {
                    stringBuilder.append("\n")
                            .append(Arrays.toString(methods.getAnnotation(Command.class).value()));
                }
            }
        }
        messageEvent.send(stringBuilder.toString());
    }

    @Command({"/echo", "/复述", "/say", "说"})
    public void echo(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event).split(" ")[2];
        if (oriMessage == null) {
            event.send("用法:/echo <内容>");
        } else {
            event.send(oriMessage);
        }
    }

    @Command({"/关于", "/about"})
    public void about(MessageEvent<?, ?> messageEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = """
                千枫Bot
                为您带来一些Simple小功能
                ======作者======
                框架作者:qfys521
                框架原作者:kloping
                """.trim();
        stringBuilder.append(a);
        ArrayList<Class<?>> classArrayList = RegisterCommand.classArrayList;
        for (Class<?> clazz : classArrayList) {
            Author author = clazz.getAnnotation(Author.class);
            int commandCount = 0;
            for (Method method : clazz.getMethods()) {
                Command command = method.getAnnotation(Command.class);
                if (command != null) {
                    commandCount++;
                }
            }
            if (author != null) {
                stringBuilder.append("\n").append(clazz.getSimpleName()).append("的").append(commandCount).append("条命令").append(":").append(author.value());
            }

        }
        messageEvent.send(stringBuilder.toString());
    }
}
