/*
 * Copyright (c) qfys521 2023.
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
import cn.qfys521.bot.core.Bot;
import cn.qfys521.bot.core.CoreInteractors;
import cn.qfys521.bot.interactors.Interactor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.Starter;
import lombok.Data;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

public class BotApplication {
    public static Starter starter;
    @SuppressWarnings("all")
    public static void main(String[] args) {
        Console console = System.console();
        ObjectMapper objectMapper = new ObjectMapper();
        LoginApplication loginApplication = new LoginApplication();
        try {
            File file = new File("login.json");
            if (!file.exists()){

                file.createNewFile();
                loginApplication.setAppid(null);
                loginApplication.setToken(null);
                loginApplication.setSecret(null);
                FileWriter fileWriter = new FileWriter(file);
                String jsonStr = objectMapper.writeValueAsString(loginApplication);
                fileWriter.write(jsonStr);
                fileWriter.close();
                console.printf("请填写login.json");
                System.exit(0);
            }
            loginApplication = objectMapper.readValue(file, LoginApplication.class);
            regCmd();
            starter = Bot.login(loginApplication.getAppid(), loginApplication.getToken(), loginApplication.getSecret());
            starter.registerListenerHost(CommandRunner.listenerHost);
            starter.run();
            if(Objects.equals(console.readLine(), "exit")){
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }

    }

    private static void regCmd() {
        RegisterCommand.registerCommand(new Class[]{
                Interactor.class,
                CoreInteractors.class
        });
    }

    public static Logger getLogger() {
        return starter.getBot().logger;
    }

}

@Data
class LoginApplication {
    private String appid;
    private String token;
    private String secret;
}