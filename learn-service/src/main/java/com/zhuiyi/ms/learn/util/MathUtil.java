package com.zhuiyi.ms.learn.util;

import java.util.Random;

public class MathUtil {
    /**
     * 获得从min到max的随机整数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumberInts(int min, int max){
        Random random = new Random();
        return random.ints(min,(max+1)).findFirst().getAsInt();
    }
}
