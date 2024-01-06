/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `Liquid.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `Liquid.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.utils.minecraft.Blocks;

import java.util.HashMap;

/**
 * 未来可能会实现set()，现在已咕咕咕<br/>
 *
 * @author qfys521<br />
 * private String LiquidName;
 * private String LiquidChineseName;
 * <p>
 * 注:未来请新添加get()和set()，此版本的get(Str)请保留作为向下兼容使用。
 */
public class Liquid {


    /**
     * getLiquidName()
     *
     * @param LiquidChineseName 流体中文名<br/>
     * @return 流体名或者null <br/>
     * @author qfys521
     */
    public String getLiquidName(String LiquidChineseName) {
        HashMap<String, String> c2e = new HashMap<>();
        c2e.put("水", "water");
        c2e.put("流动的水", "flowing_water");
        c2e.put("岩浆", "lava");
        c2e.put("流动的岩浆", "flowing_lava");
        return c2e.get(LiquidChineseName);

    }

    /**
     * getLiquidChineseName()
     *
     * @param LiquidName 流体名<br/>
     * @return 流体中文名或者null <br/>
     * @author qfys521
     */
    public String getLiquidChineseName(String LiquidName) {
        HashMap<String, String> e2c = new HashMap<>();
        e2c.put("water", "水");
        e2c.put("flowing_water", "流动的水");
        e2c.put("lava", "岩浆");
        e2c.put("flowing_lava", "流动的岩浆");
        return e2c.get(LiquidName);
    }
}