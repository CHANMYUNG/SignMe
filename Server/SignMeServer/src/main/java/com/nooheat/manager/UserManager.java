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
import sun.security.provider.SHA;

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

        boolean isAdmin = type.toUpperCase().equals("ADMIN");

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

    public static String getIdByEmail(String email, String type) throws SQLException {
        ResultSet rs = DBManager.execute("SELECT id FROM " + type + " WHERE email = ?;", email);
        if (rs.next()) {
            return AES256.decrypt(rs.getString("id"));
        } else return null;
    }

    public static boolean verifyCodeAuthentication(String verifyCode, String type) throws SQLException {
        ResultSet rs = DBManager.execute("SELECT id FROM " + type + " WHERE verifyCode = ?;", verifyCode);
        if (rs.next()) {
            return true;
        } else return false;
    }

    public static boolean updateVerifyCode(String email, String verifyCode, String type) throws SQLException {
        return DBManager.update("UPDATE " + type + " SET verifyCode = ? WHERE email = ?", verifyCode, email) != -1;
    }

    public static String getEmailById(String id, String type) throws SQLException {
        ResultSet rs = DBManager.execute("SELECT email FROM " + type + " WHERE id = ?;", AES256.encrypt(id));
        if (rs.next()) {
            return rs.getString("email");
        } else return null;
    }

    public static void updatePasswordByUid(String uid, String password, String type) throws SQLException {
        DBManager.update("UPDATE " + type + " SET password = ? WHERE uid = ?", SHA256.encrypt(password), uid);
        return;
    }

    public static boolean updatePasswordByVerifyCode(String verifyCode, String New, String type) throws SQLException {
        return DBManager.update("UPDATE " + type + " SET password = ?, verifyCode = null WHERE verifyCode = ?", SHA256.encrypt(New), verifyCode) == 1;
    }

    public static String getPasswordByUid(String uid, String type) throws SQLException {
        ResultSet rs = DBManager.execute("SELECT password FROM " + type + " WHERE uid = ?;", uid);
        if (rs.next()) {
            return rs.getString("password");
        } else return null;
    }

    public static String getEmailByUid(String uid, String type) throws SQLException {
        ResultSet rs = DBManager.execute("SELECT email FROM " + type + " WHERE uid = ?;", uid);
        if (rs.next()) {
            return rs.getString("email");
        } else return null;
    }

    public static void updateEmailByUid(String uid, String email, String type) throws SQLException {
        DBManager.update("UPDATE " + type + " SET email = ? WHERE uid = ?", email, uid);
        return;
    }

    public static void deleteAccount(String uid, String type) throws SQLException {
        DBManager.update("UPDATE " + type + " SET id = null, password = null, email = null WHERE uid = ?", uid);
    }
}