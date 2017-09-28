package com.nooheat.controller.letter.response;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.ResponseLetter;
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
import java.util.Date;

/**
 * Created by NooHeat on 28/09/2017.
 */
@API(category = Category.RESPONSELETTER, summary = "응답형 가정통신문 생성", requestBody = "title : String, contents : String, closeDate : String", successCode = 201, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401, 권한 없음(관리자 아님) : 403")
@URIMapping(uri = "/letter/response", method = HttpMethod.POST)
public class PostResponseLetter implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        System.out.println(token.isAdmin());
        if (token.isAdmin() == false) {
            res.setStatusCode(403).end();
            return;
        }

        String writeruid = token.getUid();
        String title = req.getFormAttribute("title");
        String contents = req.getFormAttribute("contents");
        String openDate = DateTime.getDateNow();
        String closeDate = req.getFormAttribute("closeDate");

        if (RequestManager.paramValidationCheck(writeruid, title, contents, closeDate) == false) {
            res.setStatusCode(400).end();
            return;
        }

        ResponseLetter letter = new ResponseLetter();
        letter.setTitle(title);
        letter.setContents(contents);
        letter.setOpenDate(openDate);
        letter.setCloseDate(closeDate);
        letter.setWriterUid(writeruid);

        try {
            boolean success = letter.save();

            if (success) res.setStatusCode(201).end();
            else res.setStatusCode(400).end();

        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        }

    }
}
