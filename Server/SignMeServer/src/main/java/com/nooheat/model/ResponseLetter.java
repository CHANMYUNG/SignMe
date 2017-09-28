package com.nooheat.model;

import com.nooheat.database.DBManager;
import com.nooheat.support.Category;
import com.nooheat.support.DateTime;
import io.vertx.core.json.JsonObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 28/09/2017.
 */
public class ResponseLetter extends Letter {
    private static final String FINDALL = "SELECT letterNumber, l.writerUid, l.title, l.contents, l.openDate, l.closeDate, (SELECT count(*)>=1 FROM letterAnswer AS LA WHERE uid = ? AND la.letterNumber = l.letterNumber) AS isAnswered, a.name AS writerName FROM responseLetter AS l LEFT JOIN ADMIN as a ON l.writerUid = a.uid;";
    private static final String FINDONE = "SELECT letterNumber, l.writerUid, l.title, l.contents, l.openDate, l.closeDate, a.name AS writerName FROM responseLetter AS l LEFT JOIN ADMIN as a ON l.writerUid = a.uid WHERE letterNumber = ?;";
    private static final String INSERT = "INSERT INTO responseLetter(writerUid,title,contents,openDate,closeDate) VALUES(?,?,?,?,?);";
    private static final String ANSWER_SAVE = "INSERT INTO letterAnswer(uid, letterNumber, answer , responseDate) VALUES(?,?,?,?);";
    private String title;
    private String contents;
    private String closeDate;
    private String writerUid;
    private String writerName;
    private int letterNumber;
    private boolean isAnswered;
    private Category type;

    public Category getType() {
        return type;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public int getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(int letterNumber) {
        this.letterNumber = letterNumber;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterUid() {
        return writerUid;
    }

    public void setWriterUid(String writerUid) {
        this.writerUid = writerUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public boolean save() throws SQLException {
        return DBManager.update(INSERT, writerUid, title, contents, openDate, closeDate) != -1;
    }

    public static ResponseLetter findOne(int letterNumber) throws SQLException {
        ResultSet rs = DBManager.execute(FINDONE, letterNumber);
        if (rs.next()) {
            ResponseLetter letter = new ResponseLetter();
            letter.setTitle(rs.getString("title"));
            letter.setWriterUid(rs.getString("writerUid"));
            letter.setWriterName(rs.getString("writerName"));
            letter.setOpenDate(rs.getString("openDate"));
            letter.setCloseDate(rs.getString("closeDate"));
            letter.setLetterNumber(rs.getInt("letterNumber"));
            letter.setContents(rs.getString("contents"));
            return letter;
        } else return null;
    }

    public boolean answer(String uid, boolean answer) throws SQLException {
        return DBManager.update(ANSWER_SAVE, uid, this.letterNumber, answer, DateTime.getDateNow()) != -1;
    }

    @Override
    public String toString() {
        return new JsonObject()
                .put("type", type.name())
                .put("letterNumber", letterNumber)
                .put("writerUid", writerUid)
                .put("writerName", writerName)
                .put("closeDate", closeDate)
                .put("openDate", openDate)
                .put("title", title)
                .put("contents", contents)
                .put("isAnswered", isAnswered)
                .toString();
    }

    public ResponseLetter() {
        this.type = Category.RESPONSELETTER;
    }

    public ResponseLetter(int letterNumber, String writerUid, String writerName, String title, String contents, String openDate, String closeDate, boolean isAnswered) {
        this.type = Category.RESPONSELETTER;
        this.letterNumber = letterNumber;
        this.writerUid = writerUid;
        this.writerName = writerName;
        this.title = title;
        this.contents = contents;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.isAnswered = isAnswered;
    }

    public static List<ResponseLetter> getLetterList(String uid) throws SQLException {
        ResultSet rs = DBManager.execute(FINDALL, uid);
        List<ResponseLetter> letters = new ArrayList<>();
        while (rs.next()) {
            int letterNumber = rs.getInt("letterNumber");
            String writerUid = rs.getString("writerUid");
            String title = rs.getString("title");
            String contents = rs.getString("contents");
            String openDate = rs.getString("openDate");
            String closeDate = rs.getString("closeDate");
            String writerName = rs.getString("writerName");
            boolean isAnswered = rs.getBoolean("isAnswered");

            ResponseLetter letter = new ResponseLetter(letterNumber, writerUid, writerName, title, contents, openDate, closeDate, isAnswered);
            System.out.println(letter.toString());
            letters.add(letter);
        }

        return letters;
    }

    @Override
    public JsonObject toJson() {
        System.out.println(contents);
        return new JsonObject()
                .put("type", type.name())
                .put("letterNumber", letterNumber)
                .put("writerUid", writerUid)
                .put("writerName", writerName)
                .put("closeDate", closeDate)
                .put("openDate", openDate)
                .put("title", title)
                .put("contents", contents)
                .put("isAnswered", isAnswered);
    }
}
