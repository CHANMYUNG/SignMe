package com.nooheat.controller.letter.responseless;

import com.nooheat.manager.JWT;
import com.nooheat.model.ResponselessLetter;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.DateTime;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 10/09/2017.
 */

@API(category = Category.RESPONSELESSLETTER, summary = "응답 없는 가정통신문 생성", requestBody = "title : String, contents : String, openDate : String", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401, 권한 없음(관리자 아님) : 403")
@URIMapping(uri = "/letter/responseless", method = HttpMethod.POST)
public class PostResponselessLetter implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        if (token.isAdmin() == false) {
            res.setStatusCode(403).end();
            return;
        }

        String writerUid = token.getUid();
        String title = req.getFormAttribute("title");
        String contents = req.getFormAttribute("contents");
        String openDate = DateTime.getDateNow();

        ResponselessLetter letter = new ResponselessLetter(writerUid, title, contents, openDate);

        boolean success = false;
        try {
            success = letter.save();
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
            return;
        }

        if (success) res.setStatusCode(201).end();
        else res.setStatusCode(400).end();
    }
}
