package com.nooheat.controller.account.signup;

import com.nooheat.database.DBManager;
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

@URIMapping(uri = "/account/uid/check", method = HttpMethod.GET)
@API(category = Category.ACCOUNT, summary = "고유 키 검사", requestBody = "uid", response = "name : String, stuNum : String(Optional)", successCode = 200, failureCode = 400)
public class UidCheck implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext context) {
        String uid = context.request().getParam("uid");

        ResultSet userRs = DBManager.execute("SELECT * FROM USER WHERE uid = ? AND id IS NULL AND password IS NULL", uid);
        ResultSet adminRs = DBManager.execute("SELECT * FROM ADMIN WHERE uid = ? AND id IS NULL AND password IS NULL", uid);
        try {
            if (userRs.next() || adminRs.next()) {

                if (userRs.isLast()) {
                    JSONObject response = new JSONObject();
                    response.put("name", userRs.getString("name"));
                    response.put("stuNum", userRs.getString("stuNum"));
                    context.response().setStatusCode(200).end(response.toString());
                } else if (adminRs.isLast()) {
                    JSONObject response = new JSONObject();
                    response.put("name", adminRs.getString("name"));
                    context.response().setStatusCode(200).end(response.toString());
                }
            } else {
                // 데이터베이스에 해당 키에 부합하는 열이 없을 때 (=유효하지 않은 키일 때)
                context.response().setStatusCode(400).end();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
