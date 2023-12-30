/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `ConfigApplicationTest.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `ConfigApplicationTest.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot;

import cn.qfys521.bot.config.ConfigApplication;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class ConfigApplicationTest {
    @SneakyThrows
    @Test
    public void ConfigWriteTest() {
        User user = new User();
        ConfigApplication configApplication = new ConfigApplication(user, "userData.json");
        user.setUser("Li Ming");
        user.setAge(17);
        configApplication.saveOrFail();
        System.out.println(configApplication);
    }

    @SneakyThrows
    @Test
    public void ConfigReadTest() {
        Coin coin = (Coin) new ConfigApplication(new Coin(), "coin.json").getDataOrFail();
        System.out.println(coin.getUser() + " has " + coin.getCoin() + " coins.");
    }

    @SneakyThrows
    @Test
    public void ConfigExTest() {
        Users user = new Users();
        ConfigApplication configApplication = new ConfigApplication(user, "ex.json");

        User user1 = new User();
        user1.setAge(17);
        user1.setUser("Li Ming");

        User user2 = new User();
        user2.setAge(18);
        user2.setUser("Xiao Hua");

        user.setUser(new User[]{user1, user2});
        configApplication.saveOrFail();
        System.out.println(configApplication);
    }
}

@Setter
@Getter
class User {
    private String user;
    private int age;
}

@Getter
@Setter
class Coin {
    private String user;
    private long coin;
}

@Getter
@Setter
class Users {
    private User[] user;
}