package com.nooheat.controller.account;

import com.nooheat.manager.RequestManager;
import com.nooheat.manager.UserManager;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import com.nooheat.util.SessionManager;

import com.oracle.tools.packager.Log;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.impl.SessionImpl;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by NooHeat on 16/06/2017.
 */

/*
사용자 계정에 관련된 요청을 처리하는 컨트롤러 클래스
 */

@URIMapping(uri = "/account/sign/in", method = HttpMethod.POST)
@API(category = Category.ACCOUNT, summary = "로그인", requestBody = "id : String, password : String", successCode = 201, failureCode = 400)
public class SignIn implements Handler<RoutingContext> {


    @Override
    public void handle(RoutingContext context) {
        System.out.println(context.request().getParam("id"));


        String id = context.request().getFormAttribute("id");
        String password = context.request().getFormAttribute("password");
        boolean keepLogin = Boolean.parseBoolean(context.request().getFormAttribute("keepLogin"));


        // 파라미터가 유효하지 않을 때 (null 일 때)
        if (RequestManager.paramVaildationCheck(id, password, keepLogin) == false) {
            // 상태코드 400 반환 후 연결 해제
            System.out.println("parameters not available");
            context.response().setStatusCode(400).end();
            context.response().close();
            return;
        }
        System.out.println(AES256.encrypt(id) + " : " + SHA256.encrypt(password));

        try {
            // 로그인 성공시
            if (UserManager.login(id, password)) {
                if (keepLogin) {

                }
                SessionManager.createSession(context, AES256.encrypt(id), "sessionKey");
                context.response().setStatusCode(201).end("Logined");
            }

            // 실패시
            else {
                // 상태코드 400 반환 후 연결 해제
                context.response().setStatusCode(400).end("SignIn failed");
            }

            // 처리과정에서 오류 발생
        } catch (Exception e) {
            // 상태코드 500 반환 후 연결 해제
            e.printStackTrace();
            context.response().setStatusCode(500).end();
            context.response().close();
        }
    }
}
