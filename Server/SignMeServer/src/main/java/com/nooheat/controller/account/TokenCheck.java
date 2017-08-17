package com.nooheat.controller.account;

import com.nooheat.manager.JWT;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 10/07/2017.
 */
@API(category = Category.TEST, summary = "토큰 테스트", successCode = 200, failureCode = 401)
@URIMapping(uri="/jwt", method = HttpMethod.POST)
public class TokenCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        if(JWT.verify(context) == null){
            context.response().setStatusCode(401).end();
            return;
        }
        context.response().setStatusCode(200).end();
    }
}
