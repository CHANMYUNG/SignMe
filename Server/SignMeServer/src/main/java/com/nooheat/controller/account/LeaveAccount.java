package com.nooheat.controller.account;

import com.nooheat.manager.JWT;
import com.nooheat.manager.UserManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 16/10/2017.
 */
@API(category = Category.ACCOUNT, summary = "계정 탈퇴", requestBody = "type : String(ADMIN, USER)", etc = "서버 오류 : 500, 잘못된 요청 : 400, 비로그인 : 401", successCode = 200, failureCode = 401)
@URIMapping(uri = "/account/leave", method = HttpMethod.POST)
public class LeaveAccount implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        String type = req.getFormAttribute("type");
        System.out.println(type);
        if (!(type.toUpperCase().equals("ADMIN") || type.toUpperCase().equals("USER"))) {
            res.setStatusCode(400).end();
            return;
        }

        try {
            UserManager.deleteAccount(token.getUid(), type);
            res.setStatusCode(200).end();
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }
    }
}
