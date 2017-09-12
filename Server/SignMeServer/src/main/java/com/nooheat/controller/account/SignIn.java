package com.nooheat.controller.account;

import com.nooheat.manager.RequestManager;
import com.nooheat.manager.UserManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONObject;

/**
 * Created by NooHeat on 16/06/2017.
 */

/*
사용자 계정에 관련된 요청을 처리하는 컨트롤러 클래스
 */

@URIMapping(uri = "/account/sign/in", method = HttpMethod.POST)
@API(category = Category.ACCOUNT, summary = "로그인", requestBody = "id : String, password : String, type : String", successCode = 201, failureCode = 400)
public class SignIn implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext context) {
        String id = context.request().getFormAttribute("id");
        String password = context.request().getFormAttribute("password");
        String type = context.request().getFormAttribute("type");
        System.out.println(id);
        System.out.println(password);
        System.out.println(type);
        // 파라미터가 유효하지 않을 때 (null 일 때)
        if (RequestManager.paramValidationCheck(id, password, type) == false) {
            // 상태코드 400 반환 후 연결 해제
            JSONObject object = new JSONObject().put("message", "params are not valid");
            context.response().setStatusCode(400).end(object.toString());
            return;
        }

        if (!(type.toUpperCase().equals("USER") || type.toUpperCase().equals("ADMIN"))) {
            context.response().setStatusCode(400).end();
            return;
        }

        UserManager.login(context, id, password, type);
    }
}
