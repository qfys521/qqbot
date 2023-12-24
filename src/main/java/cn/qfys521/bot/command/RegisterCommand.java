/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `RegisterCommand.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `RegisterCommand.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.command;


import cn.qfys521.bot.annotation.Command;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class RegisterCommand {
    public static ArrayList<Method> methodArrayList = new ArrayList<>();

    public static void registerCommand(Class<?>[] clazz) {
        for (Class<?> cl : clazz) {
            for (Method m : cl.getMethods()) {
                Command command = m.getAnnotation(Command.class);
                if (command != null) {
                    methodArrayList.add(m);
                }
            }
        }
    }
}
