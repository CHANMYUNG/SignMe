package com.nooheat.controller.letter.response;

import com.nooheat.manager.JWT;
import com.nooheat.model.ResponseLetter;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 06/10/2017.
 */
@API(category = Category.RESPONSELETTER, summary = "응답형 가정통신문 조회", response = "{letterNumber:2, writerUid:2, title:보건교육, contents:성교육임ㅋ, openDate:2017-09-14}", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400")
@URIMapping(uri = "/letter/response", method = HttpMethod.GET)
public class GetResponseLetterList implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }

        try {
            JsonArray response = ResponseLetter.findAll(token.getUid());
            res.setStatusCode(200).end(response.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }

    }
}
