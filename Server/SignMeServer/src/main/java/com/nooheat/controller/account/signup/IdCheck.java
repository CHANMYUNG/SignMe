package com.nooheat.controller.account.signup;

import com.nooheat.database.DBManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 17/07/2017.
 */
@URIMapping(uri = "/account/id/check", method = HttpMethod.GET)
@API(category = Category.ACCOUNT, summary = "아이디 유효성 검사", requestBody = "id : String", successCode = 200, failureCode = 400)
public class IdCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        String id = context.request().getFormAttribute("id");

        ResultSet userRs = DBManager.execute("SELECT COUNT(*) FROM USER WHERE id = ? AND id IS NULL AND password IS NULL", id);
        ResultSet adminRs = DBManager.execute("SELECT COUNT(*) FROM ADMIN WHERE id = ? AND id IS NULL AND password IS NULL", id);
        try {
            // user 테이블이나 admin 테이블에 이미 아이디가 존재할 경우
            if (userRs.getInt(0) != 0 && adminRs.getInt(0) != 0) {
                context.response().setStatusCode(400).end();
                return;
            } else {
                context.response().setStatusCode(200).end();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
