package com.nooheat.controller.account;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
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
@API(category = Category.ACCOUNT, summary = "비밀번호 변경", requestBody = "oldEmail : String, newEmail : String", etc = "현재 이메일 일치 X : 204, 서버 오류 : 500, 잘못된 요청 : 400, 비로그인 : 401", successCode = 200, failureCode = 204)
@URIMapping(uri = "/change/email", method = HttpMethod.POST)
public class ChangeEmail implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        String oldEmail = req.getFormAttribute("oldEmail");
        String newEmail = req.getFormAttribute("newEmail");

        if (!RequestManager.paramValidationCheck(oldEmail, newEmail)) {
            res.setStatusCode(400).end();
            return;
        }

        String oldEmailInDB = null;
        try {
            oldEmailInDB = UserManager.getEmailByUid(token.getUid(), token.isAdmin() ? "ADMIN" : "USER");
            System.out.println(oldEmailInDB);
            if (oldEmail.equals(oldEmailInDB)) {
                UserManager.updateEmailByUid(token.getUid(), newEmail, token.isAdmin() ? "ADMIN" : "USER");
                res.setStatusCode(200).end();
            } else {
                res.setStatusCode(204).end();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }
    }
}
