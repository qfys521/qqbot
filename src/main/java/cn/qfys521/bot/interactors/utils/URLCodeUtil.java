/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `URLCodeUtil.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `URLCodeUtil.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author qfys521
 */
@SuppressWarnings("all")
public class URLCodeUtil {
    /**
     * @param obj obj
     * @return URLEncode
     */
    public String URLCodeEncode(Object obj) {
        String URLEncode = URLEncoder.encode(obj.toString(), StandardCharsets.UTF_8);
        return URLEncode;
    }

    /**
     * @param obj obj
     * @return URLDecode
     */
    public String URLCodeDecode(Object obj) {
        String URLDecode = URLDecoder.decode(obj.toString(), StandardCharsets.UTF_8);
        return URLDecode;
    }
}
