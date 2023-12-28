/*
 * Copyright (c) qfys521 2023.
 *
 * 本文件 `Interactor.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Interactor.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors;

import cn.qfys521.bot.annotation.Author;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.event.MessageEventKt;
import cn.qfys521.bot.utils.HttpUtils;
import cn.qfys521.bot.utils.LuckAlgorithm;
import cn.qfys521.bot.utils.minecraft.algorithm.FuzzyMatcher;
import cn.qfys521.bot.utils.minecraft.algorithm.PrepopulatedList;
import cn.qfys521.bot.utils.minecraft.all;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.Markdown;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.qfys521.bot.BotApplication.starter;
@SuppressWarnings("unused")
@Author("qfys521")
public class Interactor {
    HttpUtils get = new HttpUtils();
    @SuppressWarnings("all")
    @Command({"/jrrp", "/今日人品"})
    public void jrrp(MessageEvent<?, ?> event) {
        long userID = event.getSender().getId().hashCode();
        final String KEY = "77yBQCPvv6Ul4oCm4oCmJirvv6UqJiXigKbigKYmJeKApuKApiYqJjk4N2Y5OHNmODlzOGdyZ2h3aXVnaHNyaXVnaGVzcml1";
        int code = LuckAlgorithm.get(userID, KEY);
        if (code == 100) {
            event.send("您的人品值为:100!100!100!");
        } else if (code == 0) {
            event.send(
                    """
                            请悉知:本插件绝对不会有任何对于任何用户有负面性的针对的影响。
                            本插件jrrp算法采用加密算法，且没有任何的set()方法。
                            在使用本插件时，请确保您有足够的心态，倘若因为该结果的原因导致您做出包括但不限于以下行为时:
                            如：砸坏您的电子设备、对于其他人造成不可逆的影响等一切行为时，
                            本插件一律不付任何的责任
                            本插件开发者绝对不会做出任何的干预行为
                            """.trim());
            event.send("很抱歉，您的今日人品值为0。");
        } else {
            event.send("您的今日人品为: " + code);
        }
    }

    @Command({"/time", "/时间"})
    public void time(MessageEvent<?, ?> event) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        event.send("now time is: " + sdf.format(date));
    }

    @Command({"/yulu", "/语录", "/随机语录"})
    public void yulu(MessageEvent<?, ?> event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/yulu/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command(value = {"/setu", "/涩图", "/色图", "/涩涩"}, inCommandList = false)
    public void setu(MessageEvent<?, ?> event) {
        Markdown markdown = new Markdown("102010154_1703343254");

        try {
            String result = get.getUrlData("https://api.lolicon.app/setu/v2?size=original&r18=0&excludeAI=true");
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject urlsObject = jsonObject.getJSONArray("data").getJSONObject(0).getJSONObject("urls");
            event.send("如果发不出去就说明进小黑屋了QAQ");
            starter.getBot().logger.info(jsonObject.toString());
            starter.getBot().logger.info(urlsObject.toString());
            String originalUrl = urlsObject.getString("original");
            starter.getBot().logger.info(originalUrl);
            event.send(new Image(originalUrl));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command({"/tgou", "/舔狗", "/随机舔狗"})
    public void tgou(MessageEvent<?, ?> event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/dog/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command({"/du", "/毒鸡汤", "/毒汤"})
    public void du(MessageEvent<?, ?> event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/dutang/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command({"/yiyan", "/一言", "/随机一言"})
    public void yiyan(MessageEvent<?, ?> event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/yiyan/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );

        }
    }

    @Command({"/getID", "/获取ID", "/ID对照"})
    public void getID(MessageEvent<?, ?> event) {
        String[] oriMessage = MessageEventKt.getOriginalContent(event).split(" ");
        if (oriMessage[2] == null) {
            event.send("用法: /getID <参数>");
        } else {
            all all = new all();
            List<String> something = all.getInformation(oriMessage[2]);
            if (something.isEmpty()) {
                event.send("无法找到关于\"" + oriMessage[2] + "\"的ID，请检查您输入内容是否正确或者该物品/魔咒/效果/生物群系等是否存在。");
                event.send("猜您想问:");
                String[] list = PrepopulatedList.getEverything();
                StringBuilder stringBuilder = new StringBuilder();
                FuzzyMatcher matcher = new FuzzyMatcher(list).setThreshold(75.0f).setLimit(10);
                matcher.find(oriMessage[2]).forEach(t -> {
                    String sb = t.getKey() +
                            " （" +
                            t.getValue() + "）\n";
                    stringBuilder.append(sb);
                });
                event.send(String.valueOf(stringBuilder));
            } else {
                StringBuilder sb = new StringBuilder();
                for (String s : something) {
                    sb.append(s);
                    sb.append("\n");
                }
                event.send(
                        "ID:" + oriMessage[2] + "\n" + sb);
            }

        }
    }
}
