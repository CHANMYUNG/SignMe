package com.nooheat.manager;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class RequestManager {

    public static boolean paramValidationCheck(Object... params) {
        for (Object param : params) {
            if (param == null) return false;
        }

        return true;
    }
}
