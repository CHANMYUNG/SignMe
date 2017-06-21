package com.nooheat.util;

import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.impl.CookieImpl;
import io.vertx.ext.web.sstore.impl.SessionImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class SessionManager {

    // 세션 키 얻어옴
    public static String getNewSessionKey() {
        String sessionKey = UUID.randomUUID().toString();
        while (true) {
            ResultSet rs = DBManager.excute("Select * from account where sessionKey = ?", sessionKey);

            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sessionKey;
    }

    // 세션 추가
    public static void registerNewSessionKey(Session session, String key, String value) {

        session.put(key, value);

        String sessionKey = session.get(key);

    }

    // 특정 키를 가진 세션의 value 불러옴
    public static String getSessionValueByKey(Session session, String key) {
        return session.get(key);
    }

    // 특정 키를 가진 세션 삭제
    public static void deleteSession(RoutingContext context, String key) {
        context.session().remove(key);
    }

    // 세션 전체 삭제
    public static void rmAll(RoutingContext context) {
        context.session().destroy();
        Session session = new SessionImpl();

    }

    public static void createCookie(RoutingContext context){}
}
