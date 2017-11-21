package com.nooheat.controller.account.forget;

import com.nooheat.manager.Mail;
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

import javax.mail.MessagingException;
import java.sql.SQLException;

/**
 * Created by NooHeat on 15/10/2017.
 */
@API(category = Category.ACCOUNT, summary = "이메일 인증코드 확인", params = "verifyCode : String, type : String(ADMIN,USER)", etc = "없는 코드 : 204, 서버 오류 : 500, 잘못된 요청 : 400", successCode = 200, failureCode = 204)
@URIMapping(uri = "/authentication", method = HttpMethod.GET)
public class VerifyCodeAuthentication implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        String verifyCode = req.getParam("verifyCode");
        String type = req.getParam("type");
        if (RequestManager.paramValidationCheck(verifyCode, type) == false) {
            res.setStatusCode(400).end();
        } else if (!type.toUpperCase().equals("ADMIN") && !type.toUpperCase().equals("USER")) {
            res.setStatusCode(400).end();
        } else {
            try {
                if (UserManager.verifyCodeAuthentication(verifyCode, type)) {
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
}
