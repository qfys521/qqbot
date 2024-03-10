/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `BotApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `BotApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot;


import cn.qfys521.bot.command.CommandRunner;
import cn.qfys521.bot.command.RegisterCommand;
import cn.qfys521.bot.config.ConfigApplication;
import cn.qfys521.bot.config.CoreConfigApplication;
import cn.qfys521.bot.core.Bot;
import cn.qfys521.bot.core.plugin.JavaPlugin;
import cn.qfys521.bot.core.plugin.PluginManager;
import cn.qfys521.bot.core.interactors.CoreInteractors;
import cn.qfys521.bot.core.loader.PluginLoader;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.Starter;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import static cn.qfys521.bot.exception.ThrowException.throwAs;

public class BotApplication {
    public static Starter starter;

    @SuppressWarnings("all")
    public static void main(String[] args) {

        try {
            init();
            start();
            loadPlugin();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }

    }

    @SuppressWarnings("all")
    private static File configInit() {
        File file = new File("log/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;

    }

    private static void loadPlugin() {
        var pluginMainClassList = readPlugin();
        if (pluginMainClassList.isEmpty()) {
            getLogger().info("插件列表为空,将跳过加载插件流程。");
        }
        for (Class<? extends JavaPlugin> cl : pluginMainClassList) {
            try {
                var pl = cl.newInstance();
                pl.onLoad();
                pl.onEnable();
                PluginManager.getJavaPlugins().add(pl);
            } catch (IllegalAccessException | InstantiationException e) {
                throwAs(e);
            }
        }
    }

    private static ArrayList<Class<? extends JavaPlugin>> readPlugin() {
        ArrayList<Class<? extends JavaPlugin>> classes = new ArrayList<>();

            final var fileList = new File("./plugins").listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    try {
                        classes.add(PluginLoader.loadJar(f.getAbsolutePath()));
                    } catch (IOException | ClassNotFoundException e) {
                        throwAs(e);
                    }
                }
            }
        return classes;
    }

    private static void start() {
        ConfigApplication configApplication = new CoreConfigApplication(new LoginApplication(), "login.json");
        LoginApplication loginApplication = (LoginApplication) configApplication.getDataOrFail();
        System.out.print("正在准备启动程序...\n");
        registerCommand();
        File file = configInit();
        starter = Bot.login(loginApplication.getAppid(), loginApplication.getToken(), loginApplication.getSecret());
        starter.APPLICATION.logger.setOutFile(file.getAbsolutePath());
        starter.registerListenerHost(CommandRunner.listenerHost);
        starter.run();
    }

    private static void init() {
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());
        System.out.println(1);
        ConfigApplication configApplication = new CoreConfigApplication(new LoginApplication(), "login.json");
        File pluginDir = new File("./plugins");
        if (!pluginDir.exists()) pluginDir.mkdirs();
        LoginApplication loginApplication = (LoginApplication) configApplication.getDataOrFail();
        if ((Objects.equals(loginApplication.getAppid(), "")) || (loginApplication.getAppid() == null) ||
                (Objects.equals(loginApplication.getToken(), "")) || (loginApplication.getSecret() == null) ||
                (loginApplication.getSecret().equals("")) || (loginApplication.getToken() == null)) {
            System.out.printf("啊这,,,您的登陆配置文件有误...\n");
            System.out.printf("让我来为您启动引导程序吧!\n");
            System.out.printf("请输入您Bot的Appid:\n");
            String appid = scanner.nextLine();//获取appid
            System.out.printf("请输入您Bot的AppToken:\n");
            String token = scanner.nextLine();//获取token
            System.out.printf("最后一步,输入您的ClientSecret就可以啦!\n");
            String secret = scanner.nextLine();//获取secret
            loginApplication.setAppid(appid);
            loginApplication.setToken(token);
            loginApplication.setSecret(secret);
            configApplication.saveOrFail();
            clearConsole();
            System.out.flush();
        }
    }

    private static void registerCommand() {
        RegisterCommand.registerCommand(new Class[]{
                CoreInteractors.class
        });
    }

    public static Logger getLogger() {
        return starter.getBot().logger;
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // 异常处理代码
        }
    }

}

@Data
class LoginApplication {
    private String appid;
    private String token;
    private String secret;
}