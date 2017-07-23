package com.nooheat.controller.account.signup;

import com.nooheat.database.DBManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 24/07/2017.
 */
@URIMapping(uri = "/account/email/check", method = HttpMethod.POST)
@API(category = Category.ACCOUNT, summary = "이메일 중복 검사", requestBody = "email : String", successCode = 200, failureCode = 400)
public class EmailCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        String email = context.request().getFormAttribute("email");

        ResultSet userRs = DBManager.execute("SELECT * FROM USER WHERE email = ? AND id IS NULL AND password IS NULL", email);
        ResultSet adminRs = DBManager.execute("SELECT * FROM ADMIN WHERE email = ? AND id IS NULL AND password IS NULL", email);
        try {
            if (userRs.next() || adminRs.next()) {
                context.response().setStatusCode(400).end();
            } else {
                // 데이터베이스에 해당 이메일이 없을 때 (등록 가능할 때)
                context.response().setStatusCode(200).end();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
