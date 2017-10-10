package com.nooheat.controller.Statistics;

import com.nooheat.manager.JWT;
import com.nooheat.model.*;
import com.nooheat.support.API;
import com.nooheat.support.Category;
import com.nooheat.support.URIMapping;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by NooHeat on 03/10/2017.
 */
@API(category = Category.LETTER, summary = "통신문 통계 조회", successCode = 200, response = "[{type:RESPONSELESSLETTER, letterNumber:2, writerUid:2, title:보건교육, contents:성교육임ㅋ, openDate:2017-09-14}, {type:SURVEY, letterNumber:1, writerUid:2, title:앙, summary:브로들, items:[이잉, 이잉2], openDate:2017-09-17 00:00:00, closeDate:2017-09-27 00:00:00}]", failureCode = 500, etc = "[가정통신문은 type으로 구분.], 비로그인 : 401, 없는 letterNumber : 400, 서버 오류 : 500")
@URIMapping(uri = "/statistics/:type/:letterNumber", method = HttpMethod.GET)
public class GetLetterStatistics implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext ctx) {
        HttpServerRequest req = ctx.request();
        HttpServerResponse res = ctx.response();

        JWT token = JWT.verify(ctx);

//        if (token == null) {
//            res.setStatusCode(401).end();
//            return;
//        }
//
//        if (token.isAdmin() == false) {
//            res.setStatusCode(403).end();
//            return;
//        }
        try {

            String type = req.getParam("type");
            int letterNumber = Integer.parseInt(req.getParam("letterNumber"));

            if (!(type.equals("survey") || type.equals("response"))) {
                res.setStatusCode(400).end();
                return;
            }

            Statistic statistic = type.equals("survey") ? Survey.findOne(letterNumber) : ResponseLetter.findOne(letterNumber);

//            if(!((Letter)statistic).getWriterUid().equals(token.getUid())){
//                res.setStatusCode(403).end();
//                return;
//            }

            assert statistic != null;
            XSSFWorkbook workbook = statistic.getStatistic();/*statistic.getStatistic();*/

            workbook.write(new FileOutputStream(/*formattedTime+" "+*/ "asdf" + ".xlsx"));

            res
                    .putHeader("Content-Disposition", "filename=" + "TEST.xlsx"/*((Letter) statistic).getTitle()+".xlsx"*/)
                    .sendFile("./asdf.xlsx");


        } catch (NumberFormatException e) {
            e.printStackTrace();
            res.setStatusCode(400).end();
        } catch (SQLException e) {
            e.printStackTrace();
            res.setStatusCode(500).end();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
