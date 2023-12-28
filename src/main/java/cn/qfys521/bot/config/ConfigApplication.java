/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `ConfigApplication.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `ConfigApplication.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.config;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;

public class ConfigApplication{
    Object t;
    File file;
    @Getter
    ObjectMapper objectMapper = new ObjectMapper();
    public ConfigApplication(Object t , File file){
        this.t = t;
        this.file = file;
    }
    public ConfigApplication(Object t ,String fileName){
        this.t = t;
        this.file = new File(fileName);
    }

    public void saveOrFail(){
        try {
            objectMapper.writeValue(file , t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    public Object getDataOrFail(){
        createNewFile();
        try {
            return t = objectMapper.readValue(file , t.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("all")
    private void createNewFile(){
        if (!file.exists()){
            try {
                file.createNewFile();
                objectMapper.writeValue(file , t);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
    @SneakyThrows
    public String toString(){
        return objectMapper.writeValueAsString(t);
    }
}
