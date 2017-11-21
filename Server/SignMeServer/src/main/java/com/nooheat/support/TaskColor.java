package com.nooheat.support;

import java.util.Random;

/**
 * Created by NooHeat on 15/10/2017.
 */
public class TaskColor {
    public static String geneateColorCode() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int s = random.nextInt(16);


            sb.append(Integer.toHexString(s));
        }
        return ("#" + sb.toString()).toUpperCase();
    }
}
