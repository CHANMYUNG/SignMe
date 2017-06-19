package com.nooheat.manager;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import com.nooheat.util.SessionManager;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.impl.SessionImpl;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class UserManager {

    public static boolean login(String id, String password) throws SQLException {

        /*
        로그인 내부 로직 처리
         */

        ResultSet rs = DBManager.excute("select id, password from account where id = ? and password = ?", AES256.encrypt(id), SHA256.encrypt(password));

        if (rs.next()) return true;

        return false;

    }

    public static void registerSessionKey(RoutingContext context, String id, boolean keepLogin) throws Exception {
        String sessionKey = getSessionKeyFromId(id);

        if (sessionKey == null)
            sessionKey = createSessionKey();


        if (keepLogin) {
            SessionManager.createCookie(context, "UserSession", sessionKey);
        } else {
            SessionManager.createSession(context, "UserSession", sessionKey);
        }

        DBManager.update("UPDATE account SET sessionKey = ? where id = ?", SHA256.encrypt(sessionKey), AES256.encrypt(id));
    }

    private static String createSessionKey() {
        String sessionKey = null;

        while (true) {
            sessionKey = UUID.randomUUID().toString();
            ResultSet rs = DBManager.excute("select * from account where sessionKey = ?", sessionKey);
            try {
                if (!rs.next()) return sessionKey;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // id를 이용해 세션 키 얻어옴
    public static String getSessionKeyFromId(String id) {
        ResultSet rs = DBManager.excute("select * from account where id = ?", id);
        String sessionKey = null;
        try {
            rs.next();
            sessionKey = rs.getString("sessionKey");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessionKey;
    }

    public boolean checkIdExists(String id) {
        boolean check = false;
        try {
            int result = DBManager.excute("select count(*) from account where id=?", AES256.encrypt(id)).getInt(1);
            if (result == 1) {
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    /*

    로그인 체크
     */
    public static boolean isLogined(RoutingContext context) {
        return SessionManager.getSessionkey(context, "UserSession") != null;
    }

}
