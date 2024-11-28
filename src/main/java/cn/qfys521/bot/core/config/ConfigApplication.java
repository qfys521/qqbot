/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `ConfigApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `ConfigApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.config;


import static cn.qfys521.bot.core.BotApplication.cause;
import cn.qfys521.bot.core.SendEmail;
import cn.qfys521.bot.core.exception.ConfigException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.SneakyThrows;

public abstract class ConfigApplication {
    Object t;
    @Getter
    File file;
    @Getter
    ObjectMapper objectMapper = new ObjectMapper();

    public ConfigApplication(Object t, String fileName) {
        this.t = t;
        this.file = new File(fileName);
    }

    public void saveOrFail() {
        try {
            objectMapper.writeValue(file, t);
        } catch (IOException e) {
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
        }
    }

    public Object getDataOrFail() {
        createNewFile();
        try {
            return t = objectMapper.readValue(file, t.getClass());
        } catch (IOException e) {
            if (e instanceof MismatchedInputException) {
                SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
                throw new ConfigException("Config file is empty or invalid", e);
            } else {
                throw new ConfigException("Failed to read config file", e);
            }
        }
    }


    @SuppressWarnings("all")
    private void createNewFile() {
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                objectMapper.writeValue(file, t);
            } catch (Exception e) {
                SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            }
        }
    }

    @SneakyThrows
    public String toString() {
        return objectMapper.writeValueAsString(t);
    }
}
