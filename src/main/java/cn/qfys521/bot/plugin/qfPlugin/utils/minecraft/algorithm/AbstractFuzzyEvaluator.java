/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `AbstractFuzzyEvaluator.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `AbstractFuzzyEvaluator.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.plugin.qfPlugin.utils.minecraft.algorithm;

/**
 * @author qfys521
 */
public abstract class AbstractFuzzyEvaluator {
    /**
     * 最大长度
     */
    public static final int MAX_LENGTH = 255;
    /**
     * 字典
     */
    private final float[][] distances;

    /**
     * @param str1 str
     * @param str2 str
     */
    public AbstractFuzzyEvaluator(String str1, String str2) {

        int len1 = str1.length();
        int len2 = str2.length();
        if (len1 > AbstractFuzzyEvaluator.MAX_LENGTH ||
                len2 > AbstractFuzzyEvaluator.MAX_LENGTH)
            throw new IllegalArgumentException("Input string is too long.");
        this.distances = new float[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 && j == 0) {
                    this.distances[i][j] = 0;
                    continue;
                }
                float distance = Float.POSITIVE_INFINITY;
                if (i > 0) {
                    float deletion = this.distances[i - 1][j] + this.getDeletionDistance(str1.charAt(i - 1));
                    distance = Math.min(distance, deletion);
                }
                if (j > 0) {
                    float insertion = this.distances[i][j - 1] + this.getInsertionDistance(str2.charAt(j - 1));
                    distance = Math.min(distance, insertion);
                }
                if (i > 0 && j > 0) {
                    char ch1 = str1.charAt(i - 1);
                    char ch2 = str2.charAt(j - 1);
                    float replacement = this.distances[i - 1][j - 1];
                    if (!this.isCharEqual(ch1, ch2)) replacement += this.getReplacementDistance(ch1, ch2);
                    distance = Math.min(distance, replacement);
                }
                if (i > 1 && j > 1) {
                    char ch1 = str1.charAt(i - 1);
                    char ch2 = str2.charAt(j - 1);
                    char ch_1 = str1.charAt(i - 2);
                    char ch_2 = str2.charAt(j - 2);
                    if (this.isCharEqual(ch1, ch_2) && this.isCharEqual(ch_1, ch2)) {
                        float transposition = this.distances[i - 2][j - 2] + this.getTranspositionDistance(ch_1, ch1);
                        distance = Math.min(distance, transposition);
                    }
                }
                this.distances[i][j] = distance;
            }
        }
    }

    /**
     * @return this.distances[this.distances.length - 1][this.distances[0].length - 1]
     */
    public float getDistance() {
        return this.distances[this.distances.length - 1][this.distances[0].length - 1];
    }

    /**
     * @param src  src
     * @param dest dest
     *
     * @return boolean
     */
    protected abstract boolean isCharEqual(char src, char dest);

    /**
     * @param ch ch
     *
     * @return float
     */
    protected abstract float getInsertionDistance(char ch);

    /**
     * @param ch ch
     *
     * @return float
     */
    protected abstract float getDeletionDistance(char ch);

    /**
     * @param src  src
     * @param dest dest
     *
     * @return float
     */
    protected abstract float getReplacementDistance(char src, char dest);

    /**
     * @param head head
     * @param tail tail
     *
     * @return float
     */
    protected abstract float getTranspositionDistance(char head, char tail);

}
