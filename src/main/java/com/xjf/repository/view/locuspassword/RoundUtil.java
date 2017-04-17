package com.xjf.repository.view.locuspassword;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/10/17——20:57
 * Function:
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class RoundUtil {

    /**
     * 点在圆肉
     *
     * @param sx
     * @param sy
     * @param r
     * @param x
     * @param y
     * @return
     */
    public static boolean checkInRound(float sx, float sy, float r, float x,
                                       float y) {
        return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
    }

}
