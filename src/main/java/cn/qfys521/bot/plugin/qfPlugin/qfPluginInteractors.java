package cn.qfys521.bot.plugin.qfPlugin;

import static cn.qfys521.bot.core.BotApplication.cause;
import static cn.qfys521.bot.core.BotApplication.getLogger;
import cn.qfys521.bot.core.annotation.Author;
import cn.qfys521.bot.core.annotation.Command;
import cn.qfys521.bot.core.annotation.Usage;
import cn.qfys521.bot.core.config.ConfigApplication;
import cn.qfys521.bot.core.config.DataConfigApplication;
import cn.qfys521.bot.core.core.app.SendEmail;
import cn.qfys521.bot.core.event.MessageEventKt;
import cn.qfys521.bot.core.exception.ThrowException;
import cn.qfys521.bot.plugin.core.utils.Base64Util;
import cn.qfys521.bot.plugin.qfPlugin.config.Coin;
import cn.qfys521.bot.plugin.qfPlugin.config.GetId;
import cn.qfys521.bot.plugin.qfPlugin.config.Jrrp;
import cn.qfys521.bot.plugin.qfPlugin.config.UserLOL;
import cn.qfys521.bot.plugin.qfPlugin.utils.FriendLink;
import cn.qfys521.bot.plugin.qfPlugin.utils.HttpUtils;
import cn.qfys521.bot.plugin.qfPlugin.utils.Link;
import cn.qfys521.bot.plugin.qfPlugin.utils.LuckAlgorithm;
import cn.qfys521.bot.plugin.qfPlugin.utils.minecraft.algorithm.FuzzyMatcher;
import cn.qfys521.bot.plugin.qfPlugin.utils.minecraft.algorithm.PrepopulatedList;
import cn.qfys521.bot.plugin.qfPlugin.utils.minecraft.all;
import cn.qfys521.drewImage.ItemKt;
import cn.qfys521.string.SuppressWarningsStrings;
import cn.qfys521.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.Markdown;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Author("qfys521")
@SuppressWarnings(SuppressWarningsStrings.UNUSED)
public class qfPluginInteractors {
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

    @Command({"/jrrp" , "/今日人品"})
    public void jrrp(MessageEvent event){
        event.send("该命令已被废弃，请使用 /签到 命令");
    }

    @Command({"/签到", "/sign"})
    @Usage({"/签到", "/sign"})
    public void sign(MessageEvent<?, ?> event) {
        ConfigApplication application = new DataConfigApplication(new Coin(), "coin.json");
        Coin coin = (Coin) application.getDataOrFail();
        long userID = event.getSender().getOpenid().hashCode();
        ConfigApplication configApplication = new DataConfigApplication(new Jrrp(), "jrrp.json");
        Jrrp jrrp = (Jrrp) configApplication.getDataOrFail();
        String KEY = null;
        StringBuilder stringBuilder = new StringBuilder();

        if (!coin.getLastSign(event.getSender().getOpenid())) {
            int c = Math.abs(new Random().nextInt(100));
            coin.addCoin(event.getSender().getOpenid(), c);
            stringBuilder.append("签到成功!\n"
                    + "当前时间为" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "\n"
                    + "您本次签到,获得了" + c + "枚Coin,您当前一共拥有" + coin.getCoinCount(event.getSender().getOpenid()) + "枚Coin."
            ).append("\n");
            coin.updateLastDate(event.getSender().getOpenid());
        } else {
            stringBuilder.append("您已经签到过啦!\n" + "上一次签到时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(coin.getLastDate().get(event.getSender().getOpenid())) + "\n您的Coin数量:" + coin.getCoinCount(event.getSender().getOpenid())).append("\n");
        }

        if (jrrp.getKey() == null) {
            KEY = Base64Util.encode(UUID.randomUUID().toString());
            jrrp.setKey(KEY);
            configApplication.saveOrFail();
        } else {
            KEY = jrrp.getKey();
        }
        int code = LuckAlgorithm.get(userID, KEY);
        if (code == 100) {
            stringBuilder.append("您的人品值为:100!100!100!");
        } else if (code == 0) {
            event.send("正在触发今日人品中...");
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
            stringBuilder.append("您的今日人品为: " + code);
        }

        event.send(stringBuilder.toString());
        configApplication.saveOrFail();
        application.saveOrFail();

    }

    @Command({"/time", "/时间"})
    @Usage({"/time", "/时间", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            event.send("请联系管理员."
                    + e.getMessage()
            );
        }
    }

    @Command(value = {"/随机东方图" }, inCommandList = false)
    public void meitu3(MessageEvent<? , ?> event){
        try {
            MessageChain messageChain = new MessageChain();
            messageChain.add(new Image("https://img.paulzzh.com/touhou/random"));

            event.send(messageChain);
        }catch (Exception e){
            event.send("请联系管理员.");
            getLogger().waring(e.toString());
        }
    }
    @Command(value = {"/meitu2" , "/美图2"}, inCommandList = false)
    public void meitu2(MessageEvent<? , ?> event){
        try {
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
                    .url("https://www.dmoe.cc/random.php?return=json")
                    .build();
            String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
            event.send( new Image(
                            JSON.parseObject(result).getString("imgurl")
                    )
            );
        }catch (Exception e){
            event.send("请联系管理员.");
            getLogger().waring(e.toString());
        }
    }

    @Command(value = {"/meitu", "/美图"}, inCommandList = false)
    @Usage({"/meitu", "/美图"})
    @SuppressWarnings("all")
    public void meitu(MessageEvent<?, ?> event) {

        try {
//            Markdown markdown = new Markdown("102010154_1703343254");
//            getLogger().info("markdown");
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
                    .url("https://moe.jitsu.top/api/?type=json")
                    .build();
            String result = Objects.requireNonNull(client.newCall(request).execute().body()).string();
           // getLogger().info(result);
            JSONObject jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("pics");
            MessageChain messageChain = new MessageChain();
            messageChain.add(new Image(jsonArray.getString(0)));

//            markdown.addParam("title", data.getString("title"))
//                    .addParam("msg1", "作者: " + data.getString("author"))
//                    .addParam("msg2", "pid: " + data.getString("pid"))
//                    .addParam("msg3", "标签: " + Arrays.toString(data.getJSONArray("tags").toArray(new String[0])))
//                    .addParam("msg4", "上传时间: " + LocalDateTime.ofEpochSecond(data.getLong("uploadDate") / 1000, 0, ZoneOffset.ofHours(8)))
//                    .addParam("msg5", "所在页数: " + data.getInteger("p") + 1)
//                    .addParam("img_name", data.getString("title")).addParam("w", String.valueOf(data.getInteger("width")))
//                    .addParam("h", String.valueOf(data.getInteger("height")))
//                    .addParam("url", originalUrl);
//            event.send(markdown);
            event.send(messageChain);
        } catch (Exception e) {
            event.send("请联系管理员.");
            getLogger().waring(e.toString());
        }
    }

    @Command({"/tgou", "/舔狗", "/随机舔狗"})
    @Usage({"/tgou", "/舔狗", "/随机舔狗"})
    public void tgou(MessageEvent<?, ?> event) {
        try {
            event.send(get.getUrlData("https://api.oick.cn/dog/api.php"));
        } catch (Exception e) {
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            event.send("请联系管理员."
                    + e.getMessage()
            );

        }
    }

    @Command({"/getID", "/获取ID", "/ID对照"})
    @Usage({"/getID <name>", "/获取ID <name>", "/ID对照 <name>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
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


    @Command({"/getPlayerUUID", "/获取玩家UUID", "/玩家UUID获取"})
    @Usage({"/getPlayerUUID <PlayerName>", "/获取玩家UUID <PlayerName>", "/玩家UUID获取 <PlayerName>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void getPlayerUUID(MessageEvent<?, ?> event) {
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            event.send("PlayerName:" + PlayerName + "\n" + "离线uuid为: " + offline.replaceAll("-", "") + "\n" + "啊这。。。。该玩家没有正版呢(悲)");
        }
    }

    @Command({"/类查询"})
    @Usage({"/类查询 <ClassForName>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
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
            SendEmail.sendEmail(e.toString(), cause(e.getStackTrace()));
            throw new Exception(e);
        }
        sb.append(" } " + "\n");
        event.send(String.valueOf(sb));
    }

    @Command({"/生成UUID"})
    @Usage({"/生成UUID", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void newUUID(MessageEvent<?, ?> event) {
        event.send(UUID.randomUUID().toString());
    }

    @Command({"/批量UUID"})
    @Usage({"/批量UUID <count[0,5)>", "*** 该命令为开发者命令，普通用户正常情况下无法遇见该命令，还请不要随意使用。"})
    public void newUUID_(MessageEvent<?, ?> event) {
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
    public void friendLink(MessageEvent<?, ?> event) {
        ConfigApplication configApplication = new DataConfigApplication(new FriendLink(), "friendLink.json");
        FriendLink friendLink = (FriendLink) configApplication.getDataOrFail();
        var arrayList = friendLink.getLinks();
        if (arrayList == null) {
            event.send("暂未添加友情链接");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (var list : arrayList) {
            stringBuilder.append("\n").append(list.getName()).append("(").append(list.getOfficialGroup()).append(")");
        }
        event.send(stringBuilder.toString());
    }

    @Command(value = "/添加友情链接", inCommandList = false)
    @Usage("/添加友情链接 <名称> <群号>")
    public void addFriendLink(MessageEvent<?, ?> event) {
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

    @Command("/抽奖")
    @Usage("/抽奖 <金币数量/梭哈>")
    synchronized public void chouJiang(MessageEvent<?, ?> event) {
        try {
            // 参数解析
            String[] args = MessageEventKt.getOriginalContent(event).split(" ");
            if (args.length != 2) {
                event.send("格式错误！正确用法：/抽奖 100 或 /抽奖 梭哈");
                return;
            }

            // 初始化数据
            String userId = event.getSender().getId();
            DataConfigApplication coinApp = new DataConfigApplication(new Coin(), "coin.json");
            Coin coinData = (Coin) coinApp.getDataOrFail();
            int userCoins = coinData.getCoinCount(userId);

            // 解析投入金额
            int x = parseBetAmount(args[1], userCoins);
            if (x <= 0 || x > userCoins) {
                event.send("投入金额无效！当前余额：" + userCoins);
                return;
            }

            // 动态调节因子
            double dynamicFactor = calculateDynamicFactor(coinData);
            boolean isHighRoller = userCoins > 50000;

            // 保底机制处理
            long lastGuarantee = coinData.getLastGuaranteeTime().getOrDefault(userId, 0L);
            int threshold = coinData.getFailThreshold().getOrDefault(userId, 3);
            int failCount = coinData.getFailCount().getOrDefault(userId, 0);

            // 保底衰减逻辑
            if (System.currentTimeMillis() - lastGuarantee < 259200000L) { // 3天
                threshold = Math.min(threshold + 2, 10);
            }

            // 抽奖结果生成
            int result;
            boolean useGuarantee = (failCount >= threshold);
            if (useGuarantee) {
                result = generateGuaranteedResult(x, isHighRoller);
                coinData.getLastGuaranteeTime().put(userId, System.currentTimeMillis());
                failCount = 0;
                threshold = 3;
            } else {
                result = generateDynamicResult(x, isHighRoller, dynamicFactor);
                failCount = result < 0 ? failCount + 1 : 0;
            }

            // 收益封顶处理
            result = applyProfitCap(result, x, isHighRoller);

            // 更新数据
            int newCoins = Math.max(userCoins + result, 0);
            coinData.getCoin().put(userId, newCoins);
            coinData.getFailCount().put(userId, failCount);
            coinData.getFailThreshold().put(userId, threshold);
            coinApp.saveOrFail();

            // 发送结果
            String msg = String.format("抽奖结果：%s%d金币（余额：%d）",
                    result >= 0 ? "+" : "", result, newCoins);
            event.send(msg);

        } catch (Exception e) {
            event.send("抽奖失败，请稍后重试");
            e.printStackTrace();
        }
    }

    // 投入金额解析
    private int parseBetAmount(String arg, int balance) {
        if ("梭哈".equals(arg)) return balance;
        try {
            int amount = Integer.parseInt(arg);
            return Math.min(amount, balance);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // 动态因子计算
    private double calculateDynamicFactor(Coin coinData) {
        int total = coinData.getCoin().values().stream().mapToInt(Integer::intValue).sum();
        return Math.max(0.5, 1.0 - (total - 1000000) / 5000000.0);
    }

    // 动态结果生成
    private int generateDynamicResult(int x, boolean isHighRoller, double factor) {
        Random rand = new Random();
        double baseProb = isHighRoller ? 0.2 : 0.4;
        double actualProb = baseProb * factor;

        if (rand.nextDouble() < actualProb) {
            return rand.nextInt((int)(x * 0.5)) + (int)(x * 0.5); // [0.5x, x]
        } else {
            int loss = rand.nextInt(isHighRoller ? (int)(x * 0.3) : (int)(x * 0.2))
                    + (isHighRoller ? (int)(x * 0.2) : (int)(x * 0.1));
            return -loss; // 高玩:[-0.5x, -0.2x], 普通:[-0.3x, -0.1x]
        }
    }

    // 保底结果生成
    private int generateGuaranteedResult(int x, boolean isHighRoller) {
        Random rand = new Random();
        return isHighRoller
                ? rand.nextInt((int)(x * 0.6)) + (int)(x * 0.4) // [0.4x, x]
                : rand.nextInt((int)(x * 0.8)) + (int)(x * 0.2); // [0.2x, x]
    }

    // 收益封顶
    private int applyProfitCap(int result, int x, boolean isHighRoller) {
        // 正收益封顶：0.5x ~ 1.2x
        int minGain = (int) (0.5 * x); // 最小正收益
        int maxGain = (int) (1.2 * x); // 最大正收益

        // 负收益封顶：-0.5x ~ 0
        int maxLoss = (int) (-0.5 * x); // 最大负收益

        // 确保结果在 [maxLoss, maxGain] 范围内
        result = Math.max(result, maxLoss); // 不低于最大负收益
        result = Math.min(result, maxGain); // 不超过最大正收益

        // 如果结果为正，确保不低于最小正收益
        if (result > 0) {
            result = Math.max(result, minGain);
        }

        return result;
    }
    @Command("/金币统计")
    @Usage("查看全服金币分布")
    synchronized public void coinStats(MessageEvent<?, ?> event) {
        try {
            DataConfigApplication coinApp = new DataConfigApplication(new Coin(  ), "coin.json");
            Coin coinData = (Coin) coinApp.getDataOrFail();

            // 全服统计
            int total = coinData.getCoin().values().stream().mapToInt(Integer::intValue).sum();

            // 排行榜
            List<Map.Entry<String, Integer>> top10 = coinData.getCoin().entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .toList();

            // 动态概率
            double factor = calculateDynamicFactor(coinData);
            String probInfo = String.format("普通用户正率: %.1f%%\n高玩用户正率: %.1f%%",
                    40.0 * factor, 20.0 * factor);

            // 构建消息
            StringBuilder sb = new StringBuilder();
            sb.append("=== 全服金币统计 ===\n");
            sb.append("总流通量: ").append(String.format("%,d", total)).append("\n\n");
            sb.append("🏆 富豪榜 TOP10:\n");

            for (int i = 0; i < top10.size(); i++) {
                String maskedId = maskUserId(top10.get(i).getKey());
                sb.append(i+1).append(". ").append(maskedId)
                        .append(" : ").append(String.format("%,d", top10.get(i).getValue()))
                        .append("\n");
            }

            sb.append("\n").append(probInfo);
            event.send(sb.toString());

        } catch (Exception e) {
            event.send("统计信息获取失败");
        }
    }

    // ID脱敏
    private String maskUserId(String id) {
        if (id.length() <= 6) return "******";
        return id.substring(0, 3) + "***" + id.substring(id.length()-3);
    }
    @Command("/我的信息")
    public void AboutMe(MessageEvent<?, ?> event) {
        ConfigApplication configApplication = new DataConfigApplication(new Coin(), "coin.json");
        Coin c = (Coin) configApplication.getDataOrFail();
        event.send(
                "您的Openid为: " + event.getSender().getOpenid()
                        + "\n您的Id为: " + event.getSender().getId()
                        + "\n您的金币数量为: " + c.getCoinCount(event.getSender().getOpenid())

        );
    }

    @Command("/异常测试")
    public void throwEx(MessageEvent<?, ?> event) {
        ThrowException.throwAs(new Exception());
    }

    @Command("/领取低保")
    public void HelpMe(MessageEvent<?, ?> event) {
        var userOpenId = event.getSender().getOpenid();
        var dataConfigApplication = new DataConfigApplication(new Coin(), "coin.json");
        var save = (Coin) dataConfigApplication.getDataOrFail();
        var lolConfigApplication = new DataConfigApplication(new UserLOL(), "userLol.json");
        var userLol = (UserLOL) lolConfigApplication.getDataOrFail();
        if (save.getCoinCount(userOpenId) <= 0) {
            if (userLol.getCount(event.getSender().getOpenid()) > 3 && userLol.getTime(event.getSender().getOpenid()) == new SimpleDateFormat("yyyy-MM-dd").format(new Date())) {
                event.send("您已经领取超过3次了!");
                return;
            }
            save.addCoin(event.getSender().getOpenid(), 1000);
            userLol.addCount(event.getSender().getOpenid(), 1);
            userLol.putTime(event.getSender().getOpenid(), System.currentTimeMillis());
            event.send("领取成功");
            dataConfigApplication.saveOrFail();
            lolConfigApplication.saveOrFail();
            return;
        }
        event.send("您尚未达成领取的条件或者您今日已经领取到达了3次！");

    }

    @Command({"/pay", "/支付", "/转账"})
    @Usage("/pay <someone> <count>")
    public void pay(MessageEvent<?, ?> event) {
        var cmd = MessageEventKt.getOriginalContent(event);
        var cmdArr = cmd.split(" ");
        if (cmdArr.length > 3) {
            event.send("不正确的用法");
            return;
        }
        var count = Integer.parseInt(cmdArr[2]);
        if (count <= 0) {
            event.send("不可以为0或者小于0哦");
        }
        var userOpenId = event.getSender().getOpenid();
        var dataConfigApplication = new DataConfigApplication(new Coin(), "coin.json");
        var save = (Coin) dataConfigApplication.getDataOrFail();
        save.addCoin(cmdArr[1], count);
        save.addCoin(userOpenId, -count);
        dataConfigApplication.saveOrFail();
        event.send("已成功转移");
    }

    @Command({"/mil", "/m"})
    @Usage("/mil b20")
    public void mil(MessageEvent<?, ?> event) {
        new Thread(() -> {
            try {
                // 获取用户主目录路径
                String homePath = System.getProperty("user.home");
                String scriptPath = homePath + "/milscore/";
                String imagePath = scriptPath;

                event.send("wait a moment...");
                ItemKt.drewImage(
                        scriptPath,
                        scriptPath + "saves.db",
                        20,
                        imagePath,
                        false
                );
                // 检查图片文件是否存在
                // sleep(3000);
                File imageFile = new File(imagePath + "/result.png");
                if (!imageFile.exists()) {
                    event.send("生成的图片未找到：" + imagePath);
                    getLogger().waring("Image file not found: " + imagePath);
                    return;
                }
                byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

                // 构建消息链并发送
                MessageChain messageChain = new MessageChain();

                messageChain.add(new Image(imageBytes));
                event.send(messageChain);
            } catch (Exception e) {
                // 错误处理
                event.send("出现了错误: " + e.getMessage());
                getLogger().error("执行出错");
                getLogger().error(e.toString());
            }
        }).start();
    }
}
