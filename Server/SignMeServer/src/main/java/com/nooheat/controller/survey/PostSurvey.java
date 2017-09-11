package com.nooheat.controller.survey;

import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by NooHeat on 04/09/2017.
 */

@API(category = Category.SURVEY, summary = "설문조사 생성", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/survey", method = HttpMethod.POST)
public class PostSurvey implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();
        System.out.println(req.formAttributes().toString());
        System.out.println(req.toString());
//        JsonObject ob = new JsonObject(req.getFormAttribute("a"));
        JsonArray list = new JsonArray(req.getFormAttribute("asdasd"));
        System.out.println(list.toString());
        System.out.println(list.getJsonObject(1).toString());
//        System.out.println(ob.toString());

        res.setStatusCode(200).end("asdad");


    }
}