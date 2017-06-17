package com.nooheat.manager;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class UserManager {

    public static boolean login(String id, String password) {
        /*
        로그인 내부 로직 처리
         */

        if(id.equals("test") && password.equals("1234")) return true;
        else return false;
    }


    public void logout() {

    }
}
