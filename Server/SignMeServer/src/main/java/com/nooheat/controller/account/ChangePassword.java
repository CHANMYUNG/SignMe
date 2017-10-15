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
 * Created by NooHeat on 15/10/2017.
 */
@API(category = Category.ACCOUNT, summary = "비밀번호 변경", requestBody = "oldPassword : String, newPassword : String", etc = "현재비밀번호 일치 X : 204, 서버 오류 : 500, 잘못된 요청 : 400, 비로그인 : 401", successCode = 200, failureCode = 204)
@URIMapping(uri = "/change/password", method = HttpMethod.POST)
public class ChangePassword implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        String oldPassword = req.getFormAttribute("oldPassword");
        String newPassword = req.getFormAttribute("newPassword");

        if (!RequestManager.paramValidationCheck(oldPassword, newPassword)) {
            res.setStatusCode(400).end();
            return;
        }

        String oldPasswordInDB = null;
        try {
            oldPasswordInDB = UserManager.getPasswordByUid(token.getUid(), token.isAdmin() ? "ADMIN" : "USER");
            if (oldPassword.equals(oldPasswordInDB)) {
                UserManager.updatePasswordByUid(token.getUid(), newPassword, token.isAdmin() ? "ADMIN" : "USER");
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
