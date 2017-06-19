package com.nooheat.util;

import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class SessionManager {


    public static void createSession(RoutingContext context, String id, String sessionKey){
        context.session().put(id, sessionKey);
        DBManager.excute("update account set sessionKey = ? where id = ?",id, sessionKey);
    }


    public static String getSessionkey(RoutingContext context, String key){
        String sessionKey = null;
        if (context.session() != null) {
            sessionKey = context.session().get(key);
        }
        if (sessionKey == null && context.getCookie(key) != null) {
            sessionKey = context.getCookie(key).getValue();
        }
        if (sessionKey != null && sessionKey.equals("null"))
            sessionKey = null;
        return sessionKey;
    }

    public static void createCookie(RoutingContext context, String key, String sessionKey){
        Cookie cookie = Cookie.cookie(key, sessionKey);
        cookie.setPath("/");
        cookie.setMaxAge(365 * 24 * 60 * 60);
        context.addCookie(cookie);
    }

    public static void rmBoth(RoutingContext context, String key){
        if(context.getCookie(key) != null){
            context.getCookie(key).setValue("null");
            createCookie(context, key, "null");
        }

        context.session().remove(key);
    }

    public static boolean isSessionKeyExist(RoutingContext context, String key) throws SQLException{

        ResultSet rs = DBManager.excute("select (*) from account where sessionKey = ?", AES256.encrypt(key));

        //if(rs.next())
        return true;
    }
}
