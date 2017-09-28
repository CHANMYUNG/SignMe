package com.nooheat.controller.letter.survey;

import com.nooheat.manager.JWT;
import com.nooheat.model.Survey;
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
 * Created by NooHeat on 17/09/2017.
 */
@API(category = Category.SURVEY, summary = "설문조사 목록 조회", successCode = 200, response = "[{letterNumber:1, title:앙, summary:브로들, openDate:2017-09-17 00:00:00, closeDate:2017-09-27 00:00:00, items:[이잉, 이잉2]}]", failureCode = 500, etc = "서버 오류 : 500")
@URIMapping(uri = "/survey", method = HttpMethod.GET)
public class GetSurveyList implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
        }
        JsonArray result;
        try {
            result = Survey.findAll(token.getUid());
        } catch (SQLException e) {
            res.setStatusCode(500).end();
            return;
        }

        res.setStatusCode(200).end(result.toString());
    }
}
