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
import io.github.kloping.spt.interfaces.Logger;
import io.github.kloping.qqbot.Starter;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;


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
    private static File loggerFileInit() {
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
        for (Class<? extends JavaPlugin> cl : pluginMainClassList) {
            try {
                var pl = cl.getDeclaredConstructor().newInstance();
                PluginManager.getJavaPlugins().add(pl);
                pl.onLoad();
                pl.onEnable();
            } catch (IllegalAccessException
                     | InstantiationException
                     | NoSuchMethodException
                     | InvocationTargetException e) {
                getLogger().error("发生了异常，请报告给管理员。\n");
                throwAs(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
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
        File file = loggerFileInit();
        starter = Bot.login(loginApplication.getAppId(), loginApplication.getToken(), loginApplication.getSecret());
        starter.APPLICATION.logger.setOutFile(file.getAbsolutePath());
        starter.registerListenerHost(CommandRunner.listenerHost);
        starter.run();
    }

    @SuppressWarnings("all")
    private static void init() {
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());
        ConfigApplication configApplication = new CoreConfigApplication(new LoginApplication(), "login.json");
        File pluginDir = new File("./plugins");
        if (!pluginDir.exists()) pluginDir.mkdirs();
        LoginApplication loginApplication = (LoginApplication) configApplication.getDataOrFail();
        if ((Objects.equals(loginApplication.getAppId(), "")) || (loginApplication.getAppId() == null) ||
                (Objects.equals(loginApplication.getToken(), "")) || (loginApplication.getSecret() == null) ||
                (Objects.equals(loginApplication.getSecret(), "")) || (loginApplication.getToken() == null)) {
            System.out.print("您的登陆配置文件有误或不存在\n");
            System.out.print("让我来为您启动引导程序吧!\n");

            System.out.print("请输入您Bot的AppId:\n");
            String appid = scanner.nextLine();//获取appid
            System.out.print("请输入您Bot的AppToken:\n");
            String token = scanner.nextLine();//获取token
            System.out.print("最后一步,输入您的ClientSecret就可以啦!\n");
            String secret = scanner.nextLine();//获取secret
            loginApplication.setAppId(appid);
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
            e.printStackTrace(System.out);
        }
    }

}

@Data
class LoginApplication {
    private String appId;
    private String token;
    private String secret;
}
