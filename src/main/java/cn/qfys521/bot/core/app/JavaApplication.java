/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `JavaApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `JavaApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.app;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class JavaApplication {
    public abstract void onEnable();

    public abstract void onDisable();

    public abstract AppData appData();
}

@Data
class AppData {
    String name;
    String version;
    long versionCode;
    ArrayList<String> authors;

    static class Builder {
        AppData appData = new AppData();

        public Builder name(String name) {
            appData.setName(name);
            return this;
        }

        public Builder version(String version) {
            appData.setVersion(version);
            return this;
        }

        public Builder versionCode(long versionCode) {
            appData.setVersionCode(versionCode);
            return this;
        }

        public Builder author(String author) {
            var author1 = new ArrayList<String>();
            author1.add(author);
            appData.setAuthors(author1);
            return this;
        }

        public Builder authors(String... authors) {
            if (authors == null) {
                appData.setAuthors(new ArrayList<>());
                return this;
            }
            var author1 = new ArrayList<>(Arrays.asList(authors));
            appData.setAuthors(author1);
            return this;
        }

        public AppData Build() {
            return appData;
        }
    }
}
