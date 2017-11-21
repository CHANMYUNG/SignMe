package com.nooheat.controller.account.signup;

import com.nooheat.database.DBManager;
import com.nooheat.secure.AES256;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NooHeat on 17/07/2017.
 */
@URIMapping(uri = "/account/id/check/:id", method = HttpMethod.GET)
@API(category = Category.ACCOUNT, summary = "아이디 유효성 검사", requestBody = "id : String",  successCode = 200, failureCode = 400)
public class IdCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {

        String id = context.request().getParam("id");

        ResultSet userRs = DBManager.execute("SELECT * FROM USER WHERE id = ?", AES256.encrypt(id));
        ResultSet adminRs = DBManager.execute("SELECT * FROM ADMIN WHERE id = ?", AES256.encrypt(id));

        try {
            if (userRs.next() || adminRs.next()) {

                if (userRs.isLast()) {
                    context.response().setStatusCode(400).end();
                } else if (adminRs.isLast()) {
                    context.response().setStatusCode(400).end();
                }
            } else {
                // 데이터베이스에 해당 키에 부합하는 열이 없을 때 (=유효하지 않은 키일 때)
                context.response().setStatusCode(200).end();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
