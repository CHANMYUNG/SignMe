package com.nooheat.controller.account.forget;

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
@API(category = Category.ACCOUNT, summary = "비밀번호 초기화", requestBody = "verifyCode : String, new : String, type : String(ADMIN,USER)", etc = "없는 인증코드 : 204, 서버 오류 : 500, 잘못된 요청 : 400", successCode = 200, failureCode = 204)
@URIMapping(uri = "/reset/password", method = HttpMethod.POST)
public class ChangeForgetPassword implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        String verifyCode = req.getFormAttribute("verifyCode");
        String New = req.getFormAttribute("new");
        String type = req.getFormAttribute("type");

        if (!type.toUpperCase().equals("ADMIN") && !type.toUpperCase().equals("USER")) {
            res.setStatusCode(400).end();
            return;
        }

        if (RequestManager.paramValidationCheck(verifyCode, New) == false) {
            //UserManager.updatePasswordByOld(old, New, type);
            res.setStatusCode(400).end();
            return;
        }

        try {
            if (UserManager.updatePasswordByVerifyCode(verifyCode, New, type)) {
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
