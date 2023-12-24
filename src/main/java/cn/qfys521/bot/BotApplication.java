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
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.Starter;
import lombok.Data;

import java.io.File;
import java.io.IOException;

public class BotApplication {
    public static Starter starter;

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginApplication loginApplication = objectMapper.readValue(new File("login.json"), LoginApplication.class);
            regCmd();
            starter = Bot.login(loginApplication.getAppid(), loginApplication.getToken(), loginApplication.getSecret());
            starter.registerListenerHost(CommandRunner.listenerHost);
            starter.run();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private static void regCmd() {
        RegisterCommand.registerCommand(new Class[]{Interactor.class});
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