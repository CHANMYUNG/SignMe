package com.nooheat.controller.account.signup;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.manager.UserManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 17/07/2017.
 */
@URIMapping(uri = "/account/sign/up", method = HttpMethod.POST)
@API(category = Category.ACCOUNT, summary = "회원가입", requestBody = "uid : uid, id : String, password : String, email : String", successCode = 201, failureCode = 400)
public class SignUp implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        String uid = context.request().getFormAttribute("uid");
        String id = context.request().getFormAttribute("id");
        String password = context.request().getFormAttribute("password");
        String email = context.request().getFormAttribute("email");


        if (RequestManager.paramValidationCheck(uid, id, password, email)) {
            try {
                if (UserManager.createAccount(uid, id, password, email)) {
                    context.response().setStatusCode(201).end();
                } else {
                    context.response().setStatusCode(400).end();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                context.response().setStatusCode(500).end();
            }

        }
        // 파라미터가 유효하지 않을 때 (null 일 때)
        else {
            // 상태코드 400 반환 후 연결 해제
            context.response().setStatusCode(400).end();
        }
    }
}
