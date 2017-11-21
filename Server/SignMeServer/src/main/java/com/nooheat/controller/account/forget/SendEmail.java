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
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by NooHeat on 15/10/2017.
 */
@API(category = Category.ACCOUNT, summary = "이메일 인증코드 전송", requestBody= "email : String, type : String(ADMIN, USER)", etc = "없는 이메일 : 204, 서버 오류 : 500, 잘못된 요청 : 400", successCode = 200, failureCode = 204)
@URIMapping(uri = "/sendEmail", method = HttpMethod.POST)
public class SendEmail implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        String id = req.getFormAttribute("id");
        String type = req.getFormAttribute("type");

        if (RequestManager.paramValidationCheck(id, type) == false) {
            res.setStatusCode(400).end();
        } else if (!type.toUpperCase().equals("ADMIN") && !type.toUpperCase().equals("USER")) {
            res.setStatusCode(400).end();
        } else {
            try {
                String email = UserManager.getEmailById(id, type);
                if (email == null) {
                    res.setStatusCode(204).end();
                } else {
                    String verifyCode = generateVerifyCode();
                    if (UserManager.updateVerifyCode(email, verifyCode, type) &&
                            Mail.sendEmail(email, verifyCode)) {

                        res.setStatusCode(200).end();
                    } else {
                        res.setStatusCode(500).end();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res.setStatusCode(500).end();
            } catch (MessagingException e) {
                e.printStackTrace();
                res.setStatusCode(500).end();
            }
        }
    }

    private String generateVerifyCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
