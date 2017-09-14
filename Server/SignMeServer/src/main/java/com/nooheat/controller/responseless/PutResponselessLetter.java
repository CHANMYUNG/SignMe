package com.nooheat.controller.responseless;


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
@API(category = Category.RESPONSELESSLETTER, summary = "비응답형 가정통신문 수정", params = "title : String, contents : String", successCode = 200, failureCode = 400)
@URIMapping(uri = "/letter/responseless/:letterNumber", method = HttpMethod.PUT)
public class PutResponselessLetter implements Handler<RoutingContext> {
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
        int letterNumber = -1;

        try {
            letterNumber = Integer.parseInt(req.getParam("letterNumber"));
        } catch (NumberFormatException e) {
            res.setStatusCode(400).end();
            return;
        }
        ResponselessLetter letter = ResponselessLetter.findOne(letterNumber);
        if(letter == null || !letter.getWriterUid().equals(writerUid)){
            res.setStatusCode(400).end();
        }
        String title = req.getFormAttribute("title");
        String contents = req.getFormAttribute("contents");


        boolean success = letter.update(writerUid, title, contents).saveUpdated();

        if (success) res.setStatusCode(200).end();
        else res.setStatusCode(400).end();

    }
}
