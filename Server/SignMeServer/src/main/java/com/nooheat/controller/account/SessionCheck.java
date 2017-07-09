package com.nooheat.controller.account;

import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import com.nooheat.util.SessionManager;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 10/07/2017.
 */
@API(category = Category.TEST, summary = "세션 테스트", successCode = 200, failureCode = 401)
@URIMapping(uri="/session", method = HttpMethod.GET)
public class SessionCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {

        if(SessionManager.getSessionkey(context,"key") == null){
            context.response().setStatusCode(401).end();
        }
        else{
            context.response().setStatusCode(200).end();
        }

    }
}
