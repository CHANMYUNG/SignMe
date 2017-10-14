package com.nooheat.manager;

import com.google.common.net.HttpHeaders;
import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.CookieImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

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
                String token = JWT.createToken(rs.getString("uid"), rs.getString("name"), isAdmin);
                context.addCookie(Cookie.cookie("signme-x-access-token", token).setMaxAge(100000000).setPath("/"));
                context.response().setStatusCode(201).end(new JsonObject().put("x-access-token", token).toString());

            } else context.response().setStatusCode(400).end();
        } catch (Exception e) {
            e.printStackTrace();
            context.response().setStatusCode(500).end();
        }
    }

    public static boolean createAccount(String uid, String id, String password, String email) throws SQLException {
        int userAffectedRows = DBManager.update("UPDATE USER SET id = ?, password = ?, email = ? WHERE uid = ? AND id IS NULL AND password IS NULL", AES256.encrypt(id), SHA256.encrypt(password), email, uid);
        int adminAffectedRows = DBManager.update("UPDATE ADMIN SET id = ?, password = ?, email = ? WHERE uid = ? AND id IS NULL AND password IS NULL", AES256.encrypt(id), SHA256.encrypt(password), email, uid);

        return (userAffectedRows + adminAffectedRows) == 1;
    }

    public static int getChildCount() throws SQLException {
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE identity = 'child';");
        if (rs.next())
            return rs.getInt("count");
        return 0;
    }

    public static int getChildCount(int grade) throws SQLException {
        String like = grade + "%";
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE stuNum like ? AND identity = 'child';", like);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    public static int getChildCount(int grade, int Class) throws SQLException {
        String like = "" + grade + "0" + Class + "%";
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE stuNum like ? AND identity = 'child';", like);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    public static int getParentCount() throws SQLException {
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE identity = 'parent';");
        if (rs.next())
            return rs.getInt("count");
        return 0;
    }

    public static int getParentCount(int grade) throws SQLException {
        String like = grade + "%";
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE stuNum like ? AND identity = 'parent';", like);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    public static int getParentCount(int grade, int Class) throws SQLException {
        String like = "" + grade + "0" + Class + "%";
        ResultSet rs = DBManager.execute("SELECT COUNT(DISTINCT stuNum) AS count FROM USER WHERE stuNum like ? AND identity = 'parent';", like);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    public static Iterator<Integer> getStuNumIterator() {
        ResultSet rs = DBManager.execute("SELECT DISTINCT stuNum FROM USER;");
        ArrayList<Integer> list = new ArrayList<>();

        try {
            while (rs.next()) {
                list.add(rs.getInt("stuNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return list.iterator();
        }
    }

}