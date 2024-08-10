/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `all.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `all.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.interactors.interactors.utils.minecraft;


import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Biomes.Biomes;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Blocks.Blocks;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Blocks.Liquid;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Effects.Effects;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Enchantments.Enchantments;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Entitys.Entitys;
import cn.qfys521.bot.core.interactors.interactors.utils.minecraft.Entitys.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qfys521
 */
public class all {

    /**
     * @param N n
     * @return List
     */
    public List<String> getInformation(String N) {
        Biomes biomes = new Biomes();
        Blocks blocks = new Blocks();
        Liquid liquid = new Liquid();
        Effects effects = new Effects();
        Enchantments enchantments = new Enchantments();
        Entitys entitys = new Entitys();
        Items items = new Items();

        List<String> list = new ArrayList<>();
        if (biomes.getBiomeChineseName(N) != null) {
            list.add("生物群系名称: " + biomes.getBiomeChineseName(N));
        }
        if (biomes.getBiomeName(N) != null) {
            list.add("生物群系ID: " + biomes.getBiomeName(N));
        }


        if (blocks.getBlockChineseName(N) != null) {
            list.add("方块名称: " + blocks.getBlockChineseName(N));
        }
        if (blocks.getBlockName(N) != null) {
            list.add("方块ID: " + blocks.getBlockName(N));
        }


        if (liquid.getLiquidChineseName(N) != null) {
            list.add("流体名称: " + liquid.getLiquidChineseName(N));
        }
        if (liquid.getLiquidName(N) != null) {
            list.add("流体ID: " + liquid.getLiquidName(N));
        }


        if (effects.getEffectChineseName(N) != null) {
            list.add("药水效果名称: " + effects.getEffectChineseName(N));
        }
        if (effects.getEffectName(N) != null) {
            list.add("药水效果ID: " + effects.getEffectName(N));
        }


        if (enchantments.getEnchantmentChineseName(N) != null) {
            list.add("魔咒名称: " + enchantments.getEnchantmentChineseName(N));
        }
        if (enchantments.getEnchantmentName(N) != null) {
            list.add("魔咒ID: " + enchantments.getEnchantmentName(N));
        }


        if (entitys.getEntityChineseName(N) != null) {
            list.add("实体名称: " + entitys.getEntityChineseName(N));
        }
        if (entitys.getEntityName(N) != null) {
            list.add("实体ID: " + entitys.getEntityName(N));
        }


        if (items.getItemChineseName(N) != null) {
            list.add("物品名称: " + items.getItemChineseName(N));
        }
        if (items.getItemName(N) != null) {
            list.add("物品ID: " + items.getItemName(N));
        }


        return list;
    }
}