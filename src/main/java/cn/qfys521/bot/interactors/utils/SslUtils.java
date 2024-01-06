/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `SslUtils.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `SslUtils.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.utils;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

/**
 * java 信任SSL证书
 *
 * @author Administrator
 */
public class SslUtils {

    public static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    /**
     * 忽略HTTPS请求的SSL证书，必须在openConnection之前调用
     *
     * @throws Exception ex
     */
    @SuppressWarnings("all")
    public static void ignoreSsl() throws Exception {
        HostnameVerifier hv = (urlHostName, session) -> true;
        System.out.println("忽略HTTPS请求的SSL证书");
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    @SuppressWarnings("all")
    static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @SuppressWarnings("all")
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        @SuppressWarnings("all")
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        @SuppressWarnings("all")
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        @SuppressWarnings("all")
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }
    }
}