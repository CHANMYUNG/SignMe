package com.nooheat.controller.account;

import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 14/10/2017.
 */
@URIMapping(uri = "/account/sign/out", method = HttpMethod.POST)
@API(category = Category.ACCOUNT, summary = "로그아웃", requestBody = "id : String, password : String, type : String", successCode = 201, failureCode = 400)
public class SignOut implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext event) {

    }
}
