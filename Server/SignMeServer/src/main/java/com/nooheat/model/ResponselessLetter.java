package com.nooheat.model;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.nooheat.database.DBManager;
import com.nooheat.support.Category;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.ws.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 10/09/2017.
 */

public class ResponselessLetter extends Letter {

    final static String FINDALL = "SELECT letterNumber, title, contents, openDate, a.name, r.writerUid, a.name AS writerName FROM responselessLetter AS r LEFT JOIN ADMIN as a ON r.writerUid = a.uid;";
    final static String SAVE = "INSERT INTO responselessLetter(writerUid, title, contents, openDate) VALUES(?, ?, ?, ?)";
    final static String FINDONE = "SELECT letterNumber, title, contents, openDate, a.name AS writerName, a.uid AS writerUid FROM responselessLetter AS r LEFT JOIN ADMIN AS a ON r.writerUid = a.uid WHERE letterNumber = ?";
    final static String UPDATE = "UPDATE responselessLetter SET title = ?, contents = ?, openDate = ? WHERE letterNumber = ?";
    final static String DELETE = "DELETE FROM responselessLetter WHERE letterNumber = ?";

    int letterNumber;

    public int getLetterNumber() {
        return letterNumber;
    }

    public String getWriterUid() {
        return writerUid;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getOpenDate() {
        return openDate;
    }

    String writerUid;
    String title;
    String contents;
    String writerName;

    public ResponselessLetter() {
        this.type = Category.RESPONSELESSLETTER;
    }

    public ResponselessLetter(String writerUid, String title, String contents, String openDate) {
        this.writerUid = writerUid;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;
    }

    public ResponselessLetter(int letterNumber, String writerUid, String writerName, String title, String contents, String openDate) {
        this.type = Category.RESPONSELESSLETTER;
        this.letterNumber = letterNumber;
        this.writerUid = writerUid;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;
    }


    public ResponselessLetter(String writerUid, String writerName, String title, String contents, String openDate) {
        this.type = Category.RESPONSELESSLETTER;
        this.writerUid = writerUid;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;

    }

    public static JsonArray findAll() {

        JsonArray response = null;

        ResultSet rs = DBManager.execute(FINDALL);

        try {
            while (rs.next()) {
                response = new JsonArray();
                JsonObject object = new JsonObject();
                object.put("letterNumber", rs.getString("letterNumber"));
                object.put("writerUid", rs.getString("writerUid"));
                object.put("title", rs.getString("title"));
                object.put("contents", rs.getString("contents"));
                object.put("openDate", rs.getString("openDate"));
                object.put("writerName", rs.getString("writerName"));
                response.add(object);
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }


    public static ResponselessLetter findOne(int letterNumber) {
        ResultSet rs = DBManager.execute(FINDONE, letterNumber);
        System.out.println(letterNumber);
        try {
            if (rs.next()) {

                String writerUid = rs.getString("writerUid");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                String openDate = rs.getString("openDate");
                String writerName = rs.getString("writerName");
                return new ResponselessLetter(letterNumber, writerUid, writerName, title, contents, openDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean save() throws SQLException {
        return DBManager.update(SAVE, writerUid, title, contents, openDate) == 1;
    }

    public ResponselessLetter update(String title, String contents) {
        this.title = title;
        this.contents = contents;

        return this;
    }

    public static List<ResponselessLetter> getLetterList() throws SQLException {
        ResultSet rs = DBManager.execute(FINDALL);
        List<ResponselessLetter> letters = new ArrayList<>();
        while (rs.next()) {
            int letterNumber = rs.getInt("letterNumber");
            String writerUid = rs.getString("writerUid");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            String openDate = rs.getString("openDate");
            String writerName = rs.getString("writerName");
            ResponselessLetter letter = new ResponselessLetter(letterNumber, writerUid, writerName, title, contents, openDate);

            letters.add(letter);
        }

        return letters;
    }

    public boolean saveUpdated() throws SQLException {
        return DBManager.update(UPDATE, title, contents, openDate, letterNumber) == 1;
    }

    public boolean delete() throws SQLException {
        return DBManager.update(DELETE, letterNumber) != -1;
    }

    @Override
    public String toString() {
        return new JsonObject()
                .put("type", this.type.name())
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("contents", this.contents)
                .put("openDate", this.openDate)
                .put("writerName", this.writerName)
                .toString();
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject()
                .put("type", this.type.name())
                .put("letterNumber", this.letterNumber)
                .put("writerUid", this.writerUid)
                .put("title", this.title)
                .put("contents", this.contents)
                .put("openDate", this.openDate)
                .put("writerName", this.writerName);
    }

}
