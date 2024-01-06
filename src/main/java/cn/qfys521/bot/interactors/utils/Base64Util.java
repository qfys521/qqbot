/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `Base64Util.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Base64Util.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author qfys521
 */
public class Base64Util {

    final static Base64.Encoder encoder = Base64.getEncoder();
    final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 给字符串加密
     *
     * @param text t
     * @return encoder.encodeToString(text.getBytes ( StandardCharsets.UTF_8)
     */
    public static String encode(String text) {

        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将加密后的字符串进行解密
     *
     * @param encodedText t
     * @return new String(decoder.decode(encodedText), StandardCharsets.UTF_8)
     */
    public static String decode(String encodedText) {
        return new String(decoder.decode(encodedText), StandardCharsets.UTF_8);
    }
}
