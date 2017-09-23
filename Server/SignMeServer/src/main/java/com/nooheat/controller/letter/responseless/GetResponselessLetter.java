package com.nooheat.controller.letter.responseless;

import com.nooheat.manager.JWT;
import com.nooheat.model.ResponselessLetter;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by NooHeat on 13/09/2017.
 */
@API(category = Category.RESPONSELESSLETTER, summary = "비응답형 가정통신문 조회", response = "{letterNumber:2, writerUid:2, title:보건교육, contents:성교육임ㅋ, openDate:2017-09-14}", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400")
@URIMapping(uri = "/letter/responseless/:letterNumber", method = HttpMethod.GET)
public class GetResponselessLetter implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        int letterNumber = -1;
        try {
            letterNumber = Integer.parseInt(req.getParam("letterNumber"));
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }
        ResponselessLetter letter = ResponselessLetter.findOne(letterNumber);

        if (letter != null) res.setStatusCode(200).end(letter.toString());
        else res.setStatusCode(400).end();
    }
}
