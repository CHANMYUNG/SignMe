package com.nooheat.controller.account;

import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import com.nooheat.util.SessionManager;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 12/07/2017.
 */

@URIMapping(uri = "/account/sign/out", method = HttpMethod.GET)
@API(category = Category.ACCOUNT, summary = "로그아웃", requestBody = "id : String, password : String", successCode = 200, failureCode = 400)
public class SignOut implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        if(context.session() == null) {
            context.response().setStatusCode(200).end();
            return;
        }
        else{
            context.session().destroy();
        }
    }
}
