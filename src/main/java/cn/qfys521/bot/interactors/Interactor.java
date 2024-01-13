/*
 * Copyright (c) qfys521 2024.
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
import cn.qfys521.bot.annotation.Usage;
import cn.qfys521.bot.config.ConfigApplication;
import cn.qfys521.bot.config.DataConfigApplication;
import cn.qfys521.bot.event.MessageEventKt;
import cn.qfys521.bot.interactors.config.Coin;
import cn.qfys521.bot.interactors.config.GetId;
import cn.qfys521.bot.interactors.config.Jrrp;
import cn.qfys521.bot.interactors.utils.Base64Util;
import cn.qfys521.bot.interactors.utils.HttpUtils;
import cn.qfys521.bot.interactors.utils.LuckAlgorithm;
import cn.qfys521.bot.interactors.utils.minecraft.algorithm.FuzzyMatcher;
import cn.qfys521.bot.interactors.utils.minecraft.algorithm.PrepopulatedList;
import cn.qfys521.bot.interactors.utils.minecraft.all;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.Markdown;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static cn.qfys521.bot.BotApplication.starter;

@SuppressWarnings("unused")
@Author("qfys521")
public class Interactor {
    final HttpUtils get = new HttpUtils();
    StringBuilder sb = new StringBuilder();

    @Command(value = {"/重置jrrp", "/resetJrrp"}, inCommandList = false)
    @Usage({"/重置jrrp", "/resetJrrp"})
    public void resetJrrp(MessageEvent<?, ?> messageEvent) {
        ConfigApplication configApplication = new DataConfigApplication(new Jrrp(), "jrrp.json");
        Jrrp jrrp = (Jrrp) configApplication.getDataOrFail();
        jrrp.setKey(Base64Util.encode(UUID.randomUUID().toString()));
        configApplication.saveOrFail();
    }

    @SuppressWarnings("all")
    @Command({"/jrrp", "/今日人品"})
    @Usage({"/jrrp", "/今日人品"})
    public void jrrp(MessageEvent<?, ?> event) {
        long userID = event.getSender().getOpenid().hashCode();
        ConfigApplication configApplication = new DataConfigApplication(new Jrrp(), "jrrp.json");
        Jrrp jrrp = (Jrrp) configApplication.getDataOrFail();
        String KEY = null;
        if (jrrp.getKey() == null) {
            KEY = Base64Util.encode(UUID.randomUUID().toString());
            jrrp.setKey(KEY);
            configApplication.saveOrFail();
        } else {
            KEY = jrrp.getKey();
        }
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
    @Usage({"/time", "/时间"})
    public void time(MessageEvent<?, ?> event) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        event.send("now time is: " + sdf.format(date));
    }

    @Command({"/yulu", "/语录", "/随机语录"})
    @Usage({"/yulu", "/语录", "/随机语录"})
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
    @Usage({
            "/setu", "/涩图", "/色图", "/涩涩",
            "/setu <tag>", "/涩图 <tag>", "/色图 <tag>", "/涩涩 <tag>"
    })
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
    @Usage({"/tgou", "/舔狗", "/随机舔狗"})
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
    @Usage({"/du", "/毒鸡汤", "/毒汤"})
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
    @Usage({"/yiyan", "/一言", "/随机一言"})
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
    @Usage({"/getID <name>", "/获取ID <name>", "/ID对照 <name>"})
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

    @Command({"/签到", "/sign"})
    @Usage({"/签到", "/sign"})
    public void sign(MessageEvent<?, ?> event) {
        ConfigApplication configApplication = new DataConfigApplication(new Coin(), "coin.json");
        Coin coin = (Coin) configApplication.getDataOrFail();
        if (!coin.getLastSign(event.getSender().getOpenid())) {
            int c = Math.abs(new Random().nextInt(100));
            coin.addLastCoin(event.getSender().getOpenid(), c);
            event.send("签到成功!\n"
                    + "当前时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "\n"
                    + "您本次签到,获得了" + c + "枚Coin,您当前一共拥有" + coin.getCoinCount(event.getSender().getOpenid()) + "枚Coin."
            );
            coin.updateLastDate(event.getSender().getOpenid());
            configApplication.saveOrFail();
        } else {
            event.send("您已经签到过啦,请明天再试吧!\n" + "上一次签到时间:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(coin.getLastDate().get(event.getSender().getOpenid())) + "\n您的Coin数量:" + coin.getCoinCount(event.getSender().getOpenid()));
        }
        configApplication.saveOrFail();
    }

    @Command({"/getPlayerUUID", "/获取玩家UUID", "/玩家UUID获取"})
    @Usage({"/getPlayerUUID <PlayerName>", "/获取玩家UUID <PlayerName>", "/玩家UUID获取 <PlayerName>"})
    public void getPlayerUUID(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        String PlayerName = oriMessage.split(" ")[2].replaceAll(" ", "");
        String tmp = "OfflinePlayer:" + PlayerName;
        UUID object = UUID.nameUUIDFromBytes(tmp.getBytes());
        String offline = object.toString();
        try {
            String request = get.getUrlData("https://api.mojang.com/users/profiles/minecraft/" + PlayerName);
            event.send(request);

            ObjectMapper objectMapper = new ObjectMapper();
            GetId b = objectMapper.readValue(request, GetId.class);
            String online = b.getId();
            event.send(b.getId() + "\n" + b.getId() + "\n" + b.getClass());
            event.send("PlayerName:" + PlayerName + "\n" + "离线uuid为: " + offline.replaceAll("-", "") + "\n" + "正版uuid为:" + online);
        } catch (Exception e) {
            event.send("PlayerName:" + PlayerName + "\n" + "离线uuid为: " + offline.replaceAll("-", "") + "\n" + "啊这。。。。该玩家没有正版呢(悲)");
        }
    }

    @Command({"/类查询"})
    @Usage({"/类查询 <ClassForName>"})
    public void ReflectionInteractor(MessageEvent<?, ?> event) throws Exception {
        String name = MessageEventKt.getOriginalContent(event).split(" ")[2];
        Class<?> cl = Class.forName(name);
        Class<?> superclass = cl.getSuperclass();
        String modifiers = Modifier.toString(cl.getModifiers());
        if (!modifiers.isEmpty())
            sb.append("class ").append(name);
        if (superclass != null && superclass != Object.class)
            sb.append(" extends " + " ").append(superclass.getName());
        sb.append("\n{\n");
        try {
            Constructor<?>[] constructors = cl.getDeclaredConstructors();
            for (Constructor<?> c : constructors) {
                String cName = c.getName();
                sb.append("   ");
                String cModifier = Modifier.toString(c.getModifiers());
                if (!cModifier.isEmpty())
                    sb.append(cModifier).append(" ").append("\n");
                sb.append(cName).append("(");
                Class<?>[] parmaTypes = c.getParameterTypes();
                for (int j = 0; j < parmaTypes.length; j++) {
                    if (j > 0)
                        sb.append(", ");
                    sb.append(parmaTypes[j].getName());
                }
                sb.append(");" + "\n");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        sb.append("\n");
        try {
            Method[] methods = cl.getDeclaredMethods();
            for (Method m : methods) {
                Class<?> retType = m.getReturnType();
                String mName = m.getName();
                sb.append("    ");
                String s = Modifier.toString(m.getModifiers());
                if (!s.isEmpty())
                    sb.append(s).append(" ");
                sb.append(retType.getName()).append(" ").append(mName).append("(");
                Class<?>[] paramTypes = m.getParameterTypes();
                for (int j = 0; j < paramTypes.length; j++) {
                    if (j > 0)
                        sb.append("'");
                    sb.append(paramTypes[j].getName()).append(" ");
                }
                sb.append(");" + "\n");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        sb.append("\n");
        try {
            Field[] fields = cl.getDeclaredFields();

            for (Field f : fields) {
                Class<?> aClass = f.getType();
                String fName = f.getName();
                sb.append("   ");
                String _modifiers = Modifier.toString(f.getModifiers());
                if (!_modifiers.isEmpty())
                    sb.append(_modifiers).append(" ");
                sb.append(aClass.getName()).append(" ").append(fName).append(";").append("\n");
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        sb.append(" } " + "\n");
        event.send(String.valueOf(sb));
    }

    @Command({"/生成UUID"})
    @Usage({"/生成UUID"})
    public void newUUID(MessageEvent<?, ?> event) {
        event.send(UUID.randomUUID().toString());
    }

    @Command({"/批量UUID"})
    @Usage({"/批量UUID <count[0,50)>"})
    public void newUUID_(MessageEvent<?, ?> event) {
        int oriMessage = Integer.parseInt(MessageEventKt.getOriginalContent(event).split(" ")[2]);
        if (oriMessage > 0 && oriMessage <= 50) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < oriMessage; i++) {
                stringBuilder.append(UUID.randomUUID()).append("\n");
            }
            event.send(stringBuilder.toString());
        } else event.send("数量必须为[1,50]");
    }
}
