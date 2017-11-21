package com.nooheat.controller.answer.response;

import com.nooheat.manager.JWT;
import com.nooheat.manager.RequestManager;
import com.nooheat.model.ResponseLetter;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

/**
 * Created by NooHeat on 06/10/2017.
 */
@API(category = Category.RESPONSELETTER, summary = "응답형 가정통신문 응답", requestBody = "answer : boolean", successCode = 200, failureCode = 400, etc = "잘못된 요청 : 400, 비로그인 : 401")
@URIMapping(uri = "/answer/response/:letterNumber", method = HttpMethod.PUT)
public class ModifyAnswerResponse implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);
        if (token == null) {
            res.setStatusCode(401).end();
            return;
        }
        if (token.isAdmin() == true) {
            res.setStatusCode(403).end();
            return;
        }
        try {
            int letterNumber = Integer.parseInt(req.getParam("letterNumber"));
            System.out.println(req.getFormAttribute("answer"));
            if (RequestManager.paramValidationCheck(req.getFormAttribute("answer")) == false) {
                res.setStatusCode(400).end();
                return;
            }
            boolean answer = Boolean.parseBoolean(req.getFormAttribute("answer"));
            ResponseLetter letter = ResponseLetter.findOne(letterNumber);
            if (letter.modifyAnswer(token.getUid(), answer)) {
                res.setStatusCode(200).end();
            } else {
                res.setStatusCode(400).end();
            }
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
        } catch (SQLException err) {
            err.printStackTrace();
            res.setStatusCode(500).end();
        }
    }
}
