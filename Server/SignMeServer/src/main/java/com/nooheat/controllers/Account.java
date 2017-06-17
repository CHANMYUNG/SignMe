package com.nooheat.controllers;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.params.RequestBody;
import com.github.aesteve.vertx.nubes.annotations.routing.http.POST;
import com.nooheat.manager.Manager;
import com.nooheat.manager.UserManager;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NooHeat on 16/06/2017.
 */

/*
사용자 계정에 관련된 요청을 처리하는 컨트롤러 클래스
 */

@Controller("/account")
public class Account {

    @API(category = Category.ACCOUNT, title="로그인", parameters = "id : String, password : String")
    @POST("/login")
    public void login(RoutingContext context, @RequestBody String params){
        JSONObject req = new JSONObject(params);
        HttpServerResponse res = context.response();

        String id = null;
        String password = null;
        try {
            id = req.getString("id");
            password = req.getString("password");
        }catch(JSONException e){
            e.printStackTrace();
            res.setStatusCode(401).end();
        }
        if(UserManager.login(id, password)) res.setStatusCode(200).end();
        else res.setStatusCode(400).end();
    }

}
