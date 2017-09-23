package com.nooheat.manager;

import com.google.common.net.HttpHeaders;
import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.CookieImpl;

import java.sql.ResultSet;

/**
 * Created by NooHeat on 18/06/2017.
 */
public class UserManager {

    public static void login(RoutingContext context, String id, String password, String type) {

        /*
        로그인 내부 로직 처리
         */

        ResultSet rs = DBManager.execute("select * from " + type + " where id = ? and password = ?", AES256.encrypt(id), SHA256.encrypt(password));

        boolean isAdmin = type.equals("admin");

        try {

            if (rs.next()) {
                context.addCookie(Cookie.cookie("signme-x-access-token", JWT.createToken(rs.getString("uid"), rs.getString("name"), isAdmin)).setMaxAge(100000000).setPath("/"));
                context.response().setStatusCode(201).end();

            } else context.response().setStatusCode(400).end();
        } catch (Exception e) {
            e.printStackTrace();
            context.response().setStatusCode(500).end();
        }
    }

    public static boolean createAccount(String uid, String id, String password, String email) {
        int userAffectedRows = DBManager.update("UPDATE USER SET id = ?, password = ?, email = ? WHERE uid = ? AND id IS NULL AND password IS NULL", AES256.encrypt(id), SHA256.encrypt(password), email, uid);
        int adminAffectedRows = DBManager.update("UPDATE ADMIN SET id = ?, password = ?, email = ? WHERE uid = ? AND id IS NULL AND password IS NULL", AES256.encrypt(id), SHA256.encrypt(password), email, uid);

        return (userAffectedRows + adminAffectedRows) == 1;
    }
}