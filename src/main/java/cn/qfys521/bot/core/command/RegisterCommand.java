/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `RegisterCommand.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `RegisterCommand.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.command;


import cn.qfys521.bot.core.annotation.Author;
import cn.qfys521.bot.core.annotation.Command;
import cn.qfys521.bot.core.annotation.Permission;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class RegisterCommand {
    public static final ArrayList<Method> methodArrayList = new ArrayList<>();
    public static final HashMap<Method, Integer> permissionhashmap = new HashMap<>();
    public static final ArrayList<Class<?>> classArrayList = new ArrayList<>();

    public static void registerCommand(Class<?>[] clazz) {
        for (Class<?> cl : clazz) {
            for (Method m : cl.getMethods()) {
                Command command = m.getAnnotation(Command.class);
                if (command != null) {
                    methodArrayList.add(m);
                }
                Permission permission = m.getAnnotation(Permission.class);
                if (permission != null) {
                    permissionhashmap.put(m, permission.value());
                }else {
                    permissionhashmap.put(m, 0);
                }
            }
            Author author = cl.getAnnotation(Author.class);
            if (author != null) {
                classArrayList.add(cl);
            }
        }
    }
}
