/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `FuzzyMatcher.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `FuzzyMatcher.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.interactors.utils.minecraft.algorithm;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author qfys521
 */
@SuppressWarnings("unused")
public class FuzzyMatcher {

    private final String[] list;
    private float threshold;
    private int limit;

    /**
     * @param list list
     */
    public FuzzyMatcher(String[] list) {
        int len = list.length;
        this.list = Arrays.copyOf(list, len);
        this.threshold = Float.NaN;
        this.limit = -1;
    }

    /**
     * @param threshold threshold
     * @return this
     */
    @SuppressWarnings("all")
    public FuzzyMatcher setThreshold(float threshold) {
        this.threshold = threshold;
        return this;
    }

    /**
     * @return this
     */
    @SuppressWarnings("all")
    public FuzzyMatcher clearThreshold() {
        this.threshold = Float.NaN;
        return this;
    }

    /**
     * @param limit limit
     * @return this
     */
    public FuzzyMatcher setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * @return this
     */
    public FuzzyMatcher clearLimit() {
        this.limit = -1;
        return this;
    }

    /**
     * @param str str
     * @return matches
     */
    public List<Entry<String, Float>> find(String str) {
        int len = list.length;
        List<Entry<String, Float>> matches = new ArrayList<>(len);
        for (String s : list) {
            float distance = new FuzzyEvaluator(str, s).getDistance();
            if (Float.isNaN(this.threshold) || distance <= this.threshold)
                matches.add(new AbstractMap.SimpleEntry<>(s, distance));
        }
        matches.sort((o1, o2) -> Float.compare(o1.getValue(), o2.getValue()));
        if (this.limit > 0 && this.limit < matches.size()) matches = matches.subList(0, this.limit);
        return matches;
    }

}
