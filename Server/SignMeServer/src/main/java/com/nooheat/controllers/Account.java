package com.nooheat.controllers;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.auth.Auth;
import com.github.aesteve.vertx.nubes.annotations.cookies.CookieValue;
import com.github.aesteve.vertx.nubes.annotations.cookies.Cookies;
import com.github.aesteve.vertx.nubes.annotations.params.RequestBody;
import com.github.aesteve.vertx.nubes.annotations.routing.http.DELETE;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.routing.http.POST;
import com.github.aesteve.vertx.nubes.auth.AuthMethod;
import com.nooheat.manager.RequestManager;
import com.nooheat.manager.UserManager;
import com.nooheat.secure.AES256;
import com.nooheat.secure.SHA256;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.util.SessionManager;

import com.oracle.tools.packager.Log;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
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

@Controller("/account")
public class Account {
    
    @Auth(method = AuthMethod.JWT, authority = "User")
    @API(category = Category.ACCOUNT, title = "로그인", parameters = "id : String, password : String")
    @POST("/login")
    public void login(RoutingContext context, @RequestBody String param) {

        // 각각의 파라미터 값을 읽어옴
        JSONObject params = new JSONObject(param);
        String id = params.getString("id");
        String password = params.getString("password");
        boolean keepLogin = params.getBoolean("keepLogin");



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
                if(keepLogin){

                }

            }

            // 실패시
            else {
                // 상태코드 400 반환 후 연결 해제

            }

            // 처리과정에서 오류 발생
        } catch (Exception e) {
            // 상태코드 500 반환 후 연결 해제
            e.printStackTrace();
            context.response().setStatusCode(500).end();
            context.response().close();
        }

    }


    @GET("/sessionCheck")
    public void sessionCheck(RoutingContext context) {

    }

    @API(category = Category.ACCOUNT, title = "로그아웃")
    @DELETE("/logout")
    public void logout(RoutingContext context){

    }



}
