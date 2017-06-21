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



}
