/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `CoreInteractors.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `CoreInteractors.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.interactors;

import cn.qfys521.bot.annotation.Author;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.annotation.Usage;
import cn.qfys521.bot.command.RegisterCommand;
import cn.qfys521.bot.core.interactors.utils.*;
import cn.qfys521.bot.core.plugin.JavaPlugin;
import cn.qfys521.bot.core.plugin.PluginManager;
import cn.qfys521.bot.event.MessageEventKt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.Markdown;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import cn.qfys521.bot.annotation.Author;
import cn.qfys521.bot.annotation.Command;
import cn.qfys521.bot.annotation.Usage;
import cn.qfys521.bot.config.ConfigApplication;
import cn.qfys521.bot.config.DataConfigApplication;
import cn.qfys521.bot.event.MessageEventKt;
import cn.qfys521.bot.core.interactors.interactors.config.Coin;
import cn.qfys521.bot.core.interactors.interactors.config.GetId;
import cn.qfys521.bot.core.interactors.interactors.config.Jrrp;
import cn.qfys521.bot.core.interactors.interactors.utils.*;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.algorithm.FuzzyMatcher;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.algorithm.PrepopulatedList;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.all;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.Keyboard;
import io.github.kloping.qqbot.entities.ex.Markdown;
import io.github.kloping.qqbot.entities.ex.MessageAsyncBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static cn.qfys521.bot.BotApplication.getLogger;
import static cn.qfys521.bot.BotApplication.starter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static cn.qfys521.bot.BotApplication.getLogger;

@SuppressWarnings("unused")
@Author("qfys521")
public class CoreInteractors {
    @Usage({"/help"})
    @Command({"/help"})
    public void helpMenu(MessageEvent<?, ?> messageEvent) {
//        ArrayList<Method> method = RegisterCommand.methodArrayList;
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("指令菜单\n");
//        for (Method methods : method) {
//            Command command = methods.getAnnotation(Command.class);
//            if (command != null) {
//                if (command.inCommandList()) {
//                    stringBuilder.append("\n")
//                            .append(Arrays.toString(methods.getAnnotation(Command.class).value()));
//                }
//            }
//        }
//        messageEvent.send(stringBuilder.toString());
        ArrayList<Method> commandMethods = RegisterCommand.methodArrayList;
        StringBuilder stringBuilder = new StringBuilder();
        for (Method method : commandMethods) {
            Command command = method.getAnnotation(Command.class);
            Usage usage = method.getAnnotation(Usage.class);
            if (command == null) continue;
            if (!command.inCommandList()) continue;
                StringBuilder cmdStr = new StringBuilder();
                for (String cmd : command.value()) {
                    cmdStr.append("\n").append(cmd).append(" ");
                }
                StringBuilder usageStr = new StringBuilder();

                if (usage == null) {
                    usageStr.append("暂无用法。");
                } else {
                    for (String usa : usage.value()) {
                        usageStr.append("\n").append(usa).append(" ");
                    }
                }
                stringBuilder.append("\n").append("命令: ").append(cmdStr).append("\n")
                        .append("用法: ").append(usageStr).append("\n");
            
        }
        messageEvent.send("菜单: \n" + stringBuilder);
    }


    @Command({"/echo", "/复述", "/say", "/说"})
    @Usage({"/echo <Message>", "/say <Message>", "/说 <消息>", "/复述 <消息>" , "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void echo(MessageEvent<?, ?> event) {
        var tmp = MessageEventKt.getOriginalContent(event).split(" ");
        tmp[0] = tmp[1] = "";
        String oriMessage = Arrays.toString(tmp);
        event.send(oriMessage);
    }

    @Command({"/关于", "/about"})
    @Usage({"/关于", "/about"})
    public void about(MessageEvent<?, ?> messageEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = """
                                
                -={千枫Bot}=-
                为您带来一些Simple小功能
                ======作者======
                框架作者:qfys521
                java-sdk作者:kloping
                ===============
                """.trim();
        stringBuilder.append(a);
        ArrayList<Class<?>> classArrayList = RegisterCommand.classArrayList;
        for (Class<?> clazz : classArrayList) {
            Author author = clazz.getAnnotation(Author.class);
            int commandCount = 0;
            for (Method method : clazz.getMethods()) {
                Command command = method.getAnnotation(Command.class);
                if (command != null) {
                    commandCount++;
                }
            }
            if (author != null) {
                stringBuilder.append("\n").append(clazz.getSimpleName()).append("的").append(commandCount).append("条命令").append(":").append(author.value());
            }
        }
        messageEvent.send(stringBuilder.toString());
    }

    @Command("/Base64")
    @Usage("/Base64 <encode/decode> <text>")
    public void Base64(MessageEvent<?, ?> messageEvent) {
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(Base64Util.encode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(Base64Util.decode(input));
        } else {
            messageEvent.send("用法:/Base64 <encode/decode> <string>");
        }
    }

    @Command("/MD5")
    @Usage("/MD5 <text>")
    public void MD5(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        if (oriMessage.split(" ")[2] == null) {
            event.send("用法:/MD5 <string>");
        } else {
            event.send(new MD5Util().toMD5(oriMessage.split(" ")[2]));
        }
    }

    @Command({"/插件列表", "/plugins"})
    @Usage({"/插件列表", "/plugins"})
    public void plugin(MessageEvent<?, ?> event) {
        StringBuilder stringBuilder = new StringBuilder();
        for (JavaPlugin plugin : PluginManager.getJavaPlugins()) {
            stringBuilder.append("\n")
                    .append(plugin.getPluginInfo().getName())
                    .append(" : ")
                    .append(plugin.getPluginInfo().getVersion())
                    .append("(")
                    .append(plugin.getPluginInfo().getVersionCode())
                    .append(")");
        }
        event.send("当前插件有：" + stringBuilder);
    }

    @Command("/Unicode")
    @Usage("/Unicode <encode/decode> <text>")
    public void Unicode(MessageEvent<?, ?> messageEvent) {
        UnicodeUtil unicodeUtil = new UnicodeUtil();
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(unicodeUtil.unicodeEncode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(unicodeUtil.unicodeDecode(input));
        } else {
            messageEvent.send("用法:/Unicode <encode/decode> <string>");
        }
    }

    @Command("/Urlcode")
    @Usage("/Urlcode <decode/encode> <text>")
    public void UrlCode(MessageEvent<?, ?> messageEvent) {
        URLCodeUtil util = new URLCodeUtil();
        String oriMessage = MessageEventKt.getOriginalContent(messageEvent);
        String[] oriMsg = oriMessage.split(" ");
        String type = oriMsg[2];
        if (type == null) {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
            return;
        }
        String input = oriMsg[3];
        if (input == null) {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
            return;
        }
        if (type.equals("encode")) {
            messageEvent.send(util.URLCodeEncode(input));
        } else if (type.equals("decode")) {
            messageEvent.send(util.URLCodeDecode(input));
        } else {
            messageEvent.send("用法:/UrlCode <encode/decode> <string>");
        }
    }

    @Command({"/指令帮助", "/Command-Help"})
    @Usage({"/指令帮助", "/Command-Help", "/指令帮助 <指令>", "/Command-Help <Command>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void CommandHelp(MessageEvent<?, ?> event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        String arg = oriMessage.split(" ")[2];
        ArrayList<Method> methodArrayList = RegisterCommand.methodArrayList;
        for (Method m : methodArrayList) {
            if (m.getAnnotation(Command.class) != null) {
                for (String s : m.getAnnotation(Command.class).value()) {
                    if (s.equals(arg)) {
                        if (m.getAnnotation(Usage.class) == null) {
                            event.send("该指令尚未注册用法.");
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("指令").append(arg).append("的用法为:\n");
                            for (String string : m.getAnnotation(Usage.class).value()) {
                                stringBuilder.append(string)
                                        .append("\n");
                            }
                            event.send(stringBuilder.toString());
                        }
                    }
                }
            } else {
                event.send("找不到帮助消息");
            }
        }
    }
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
    public void jrrp(MessageEvent event) {
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
    @Usage({"/time", "/时间", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void time(MessageEvent event) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        event.send("now time is: " + sdf.format(date));
    }

    @Command({"/yulu", "/语录", "/随机语录"})
    @Usage({"/yulu", "/语录", "/随机语录"})
    public void yulu(MessageEvent event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/yulu/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command(value = {"/setu", "/涩图", "/色图", "/涩涩"}, inCommandList = false)
    @Usage({"/setu", "/涩图", "/色图", "/涩涩"})
    @SuppressWarnings("all")
    public void setu(MessageEvent event) {
/*
        try {
            Markdown markdown = new Markdown("102010154_1703343254");
            getLogger().info("markdown");
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                                .build();
                        return chain.proceed(requestWithUserAgent);
                    })
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.lolicon.app/setu/v2?size=original&r18=0&excludeAI=true")
                    .build();
            String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
            getLogger().info(result);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
            JSONObject urlsObject = data.getJSONObject("urls");
            String originalUrl = urlsObject.getString("original");
            markdown.addParam("title" , data.getString("title"))
                    .addParam("msg1" , "作者: "+data.getString("author"))
                    .addParam("msg2" , "pid: "+data.getString("pid"))
                    .addParam("msg3" , "标签: "+ Arrays.toString(data.getJSONArray("tags").toArray(new String[0])))
                    .addParam("msg4" , "上传时间: "+ LocalDateTime.ofEpochSecond(data.getLong("uploadDate")/1000, 0, ZoneOffset.ofHours(8)))
                    .addParam("msg5" , "所在页数: "+ data.getInteger("p")+1)
                    .addParam("img_name" , data.getString("title")).addParam("w" , String.valueOf(data.getInteger("width")))
                    .addParam("h" , String.valueOf(data.getInteger("height")))
                    .addParam("url" , originalUrl);
            event.send(markdown);
        } catch (Exception e) {
            event.send("请联系管理员.");
            getLogger().waring(e.toString());
        }
        */
        event.send("由于该机器人所在平台监管政策要求，该命令已被禁止使用。将在下一次版本更新后移除");
    }

    @Command({"/tgou", "/舔狗", "/随机舔狗"})
    @Usage({"/tgou", "/舔狗", "/随机舔狗"})
    public void tgou(MessageEvent event) {
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
    public void du(MessageEvent event) {
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
    public void yiyan(MessageEvent event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/yiyan/api.php"));
        } catch (Exception e) {
            event.send("请联系管理员."
                    + e.getMessage()
            );

        }
    }

    @Command({"/getID", "/获取ID", "/ID对照"})
    @Usage({"/getID <name>", "/获取ID <name>", "/ID对照 <name>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void getID(MessageEvent event) {
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
    public void sign(MessageEvent event) {
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
    @Usage({"/getPlayerUUID <PlayerName>", "/获取玩家UUID <PlayerName>", "/玩家UUID获取 <PlayerName>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void getPlayerUUID(MessageEvent event) {
        String oriMessage = MessageEventKt.getOriginalContent(event);
        String PlayerName = oriMessage.split(" ")[2].replaceAll(" ", "");
        String tmp = "OfflinePlayer:" + PlayerName;
        UUID object = UUID.nameUUIDFromBytes(tmp.getBytes());
        String offline = object.toString();
        try {
            String request = get.getUrlData("https://api.mojang.com/users/profiles/minecraft/" + PlayerName);
           // event.send(request);

            ObjectMapper objectMapper = new ObjectMapper();
            GetId b = objectMapper.readValue(request, GetId.class);
            String online = b.getId();
           // event.send(b.getId() + "\n" + b.getId() + "\n" + b.getClass());
            event.send("PlayerName:" + PlayerName + "\n" + "离线uuid为: " + offline.replaceAll("-", "") + "\n" + "正版uuid为:" + online);
        } catch (Exception e) {
            event.send("PlayerName:" + PlayerName + "\n" + "离线uuid为: " + offline.replaceAll("-", "") + "\n" + "啊这。。。。该玩家没有正版呢(悲)");
        }
    }

    @Command({"/类查询"})
    @Usage({"/类查询 <ClassForName>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void ReflectionInteractor(MessageEvent event) throws Exception {
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
    @Usage({"/生成UUID", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void newUUID(MessageEvent event) {
        event.send(UUID.randomUUID().toString());
    }

    @Command({"/批量UUID"})
    @Usage({"/批量UUID <count[0,5)>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void newUUID_(MessageEvent event) {
        int oriMessage = Integer.parseInt(MessageEventKt.getOriginalContent(event).split(" ")[2]);
        if (oriMessage > 0 && oriMessage <= 5) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < oriMessage; i++) {
                stringBuilder.append(UUID.randomUUID()).append("\n");
            }
            event.send(stringBuilder.toString());
        } else event.send("数量必须为[1,50]");
    }

    @Usage("/友情链接")
    @Command("/友情链接")
    public void friendLink(MessageEvent event){
        ConfigApplication configApplication = new DataConfigApplication(new FriendLink() , "friendLink.json");
        FriendLink friendLink = (FriendLink)configApplication.getDataOrFail();
        var arrayList = friendLink.getLinks();
        if (arrayList==null){
            event.send("暂未添加友情链接");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (var list:arrayList){
            stringBuilder.append("\n").append(list.getName()).append("(").append(list.getOfficialGroup()).append(")");
        }
        event.send(stringBuilder.toString());
    }

    @Command(value = "/添加友情链接" , inCommandList = false)
    @Usage("/添加友情链接 <名称> <群号>")
    public void addFriendLink(MessageEvent event) {
        ConfigApplication configApplication = new DataConfigApplication(new FriendLink(), "friendLink.json");
        FriendLink friendLink = (FriendLink) configApplication.getDataOrFail();
        ArrayList<Link> links = friendLink.getLinks();
        String[] tmp = MessageEventKt.getOriginalContent(event).trim().split(" "); // 去除多余空格并按空格分割
        if (tmp.length < 3) {
            // 至少需要名称和群号两个参数
            // 这里可以发送一个错误消息给用户
            return;
        }
        String name = tmp[1]; // 名称是第二个参数（索引为1，因为数组索引从0开始）
        String officialGroup = tmp[2]; // 群号是第三个参数
        Link link = new Link();
        link.setName(name);
        link.setOfficialGroup(officialGroup);
        links.add(link);
        // 保存更新后的 FriendLink 对象到配置文件
        configApplication.saveOrFail();
        event.send("已添加成功");
    }
}


