package com.nooheat.model;

import com.nooheat.database.DBManager;
import com.nooheat.support.Category;
import com.nooheat.support.DateTime;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 28/09/2017.
 */
public class ResponseLetter extends Letter implements Statistic {
    private static final String FINDALL = "SELECT letterNumber, l.writerUid, l.title, l.contents, l.openDate, l.closeDate, (SELECT count(*)>=1 " +
            "FROM letterAnswer AS LA " +
            "WHERE uid = ? AND la.letterNumber = l.letterNumber) AS isAnswered, a.name AS writerName FROM responseLetter AS l LEFT JOIN ADMIN as a ON l.writerUid = a.uid;";
    private static final String FINDONE = "SELECT letterNumber, l.writerUid, l.title, l.contents, l.openDate, l.closeDate, a.name AS writerName " +
            "FROM responseLetter AS l LEFT JOIN ADMIN as a ON l.writerUid = a.uid " +
            "WHERE letterNumber = ?;";
    private static final String INSERT = "INSERT INTO responseLetter(writerUid,title,contents,openDate,closeDate) VALUES(?,?,?,?,?);";
    private static final String ANSWER_SAVE = "INSERT INTO letterAnswer(uid, letterNumber, answer , responseDate) VALUES(?,?,?,?);";
    private static final String ANSWER_MODIFY = "UPDATE letterAnswer SET answer = ?, answerDate = ? WHERE uid = ? AND letterNumber = ?;";
    private static final String ANSWER_COUNT_STUDENT_ALL = "SELECT COUNT(a.uid) as count " +
            "FROM letterAnswer as a " +
            "WHERE letterNumber = ? AND (SELECT identity from USER as _U where _U.uid=a.uid) ='child'";
    private static final String ANSWER_COUNT_STUDENT = "SELECT COUNT(a.uid) as count " +
            "FROM letterAnswer as a " +
            "WHERE letterNumber = ?" +
            " AND (SELECT stuNum FROM USER as U WHERE a.uid = U.uid AND identity = 'child') like ?" +
            " AND (SELECT identity from USER as _U where _U.uid = a.uid) = 'child';";

    private static final String ANSWER_COUNT_PARENT = "SELECT COUNT(a.uid) as count " +
            "FROM letterAnswer as a " +
            "WHERE letterNumber = ?" +
            " AND (SELECT stuNum FROM USER as U WHERE a.uid = U.uid AND identity = 'parent') like ?" +
            " AND (SELECT identity from USER as _U where _U.uid = a.uid) = 'parent';";

    private static final String ANSWER_COUNT_PARENT_ALL = "SELECT COUNT(a.uid) as count " +
            "FROM letterAnswer as a " +
            "WHERE letterNumber = ? AND (SELECT identity from USER as _U where _U.uid=a.uid) ='parent'";
    private String contents;
    private String closeDate;
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

    public boolean answer(String uid, boolean answer) throws SQLException {
        return DBManager.update(ANSWER_SAVE, uid, this.letterNumber, answer, DateTime.getDateNow()) != -1;
    }

    public boolean modifyAnswer(String uid, boolean answer) throws SQLException {
        return DBManager.update(ANSWER_MODIFY, answer, DateTime.getDateNow(), uid, letterNumber) != -1;
    }

    public static JsonArray findAll(String uid) throws SQLException {
        ResultSet rs = DBManager.execute(FINDALL, uid);
        JsonArray response = new JsonArray();
        while (rs.next()) {
            JsonObject letter = new JsonObject();
            letter.put("title", rs.getString("title"));
            letter.put("writerUid", rs.getString("writerUid"));
            letter.put("writerName", rs.getString("writerName"));
            letter.put("openDate", rs.getString("openDate"));
            letter.put("closeDate", rs.getString("closeDate"));
            letter.put("letterNumber", rs.getInt("letterNumber"));
            letter.put("contents", rs.getString("contents"));
            letter.put("isAnswered", rs.getBoolean("isAnswered"));
            response.add(letter);
        }
        return response;
    }

    @Override
    public XSSFWorkbook getStatistic() throws SQLException {
        XSSFWorkbook statistic = new XSSFWorkbook();
        XSSFSheet sheet = statistic.createSheet("통계");
        XSSFRow rowHead = sheet.createRow(0);
        rowHead.createCell(0).setCellValue("제목");
        rowHead.createCell(1).setCellValue(title);
        rowHead = sheet.createRow(1);
        rowHead.createCell(0).setCellValue("작성자");
        rowHead.createCell(1).setCellValue(writerName);
        rowHead = sheet.createRow(3);
        rowHead.createCell(0).setCellValue("학생");
        rowHead.createCell(1).setCellValue("1학년");
        rowHead.createCell(2).setCellValue("2학년");
        rowHead.createCell(3).setCellValue("3학년");
        rowHead.createCell(4).setCellValue("총합");
        rowHead = sheet.createRow(4);

        ResultSet rs = DBManager.execute(ANSWER_COUNT_STUDENT, letterNumber, "1%");
        rs.next();
        rowHead.createCell(1).setCellValue(rs.getInt("count"));

        rs = DBManager.execute(ANSWER_COUNT_STUDENT, letterNumber, "2%");
        rs.next();
        rowHead.createCell(2).setCellValue(rs.getInt("count"));
        // TODO: SQL변수로 빼기
        rs = DBManager.execute(ANSWER_COUNT_STUDENT, letterNumber, "3%");
        rs.next();
        rowHead.createCell(3).setCellValue(rs.getInt("count"));

        rs = DBManager.execute(ANSWER_COUNT_STUDENT_ALL, letterNumber);
        rs.next();
        rowHead.createCell(4).setCellValue(rs.getInt("count"));

        rowHead = sheet.createRow(6);
        rowHead.createCell(0).setCellValue("학부모");
        rowHead.createCell(1).setCellValue("1학년");
        rowHead.createCell(2).setCellValue("2학년");
        rowHead.createCell(3).setCellValue("3학년");
        rowHead.createCell(4).setCellValue("총합");

        rowHead = sheet.createRow(7);
        rowHead.createCell(0).setCellValue("응답 수");
        rs = DBManager.execute(ANSWER_COUNT_PARENT, letterNumber, "1%");
        rs.next();
        rowHead.createCell(1).setCellValue(rs.getInt("count"));

        rs = DBManager.execute(ANSWER_COUNT_PARENT, letterNumber, "2%");
        rs.next();
        rowHead.createCell(2).setCellValue(rs.getInt("count"));

        rs = DBManager.execute(ANSWER_COUNT_PARENT, letterNumber, "3%");
        rs.next();
        rowHead.createCell(3).setCellValue(rs.getInt("count"));

        rs = DBManager.execute(ANSWER_COUNT_PARENT_ALL, letterNumber);
        rs.next();
        rowHead.createCell(4).setCellValue(rs.getInt("count"));

        sheet = statistic.createSheet("세부통계");

        XSSFRow row;
        row = sheet.createRow(1);
        row.createCell(1).setCellValue("학번");
        row.createCell(2).setCellValue("학생응답");
        row.createCell(3).setCellValue("학부모응답");

        ResultSet stuNums = DBManager.execute("SELECT DISTINCT stuNum FROM USER ORDER BY stuNum DESC;");
        ResultSet studentAnswer;
        ResultSet parentAnswer;

        int rowCount = 2;
        while (stuNums.next()) {
            row = sheet.createRow(rowCount++);
            int stuNum = stuNums.getInt("stuNum");
            row.createCell(1).setCellValue(stuNum);
            studentAnswer = DBManager.execute("SELECT answer FROM letterAnswer WHERE letterNumber = ? AND uid = (SELECT uid FROM USER WHERE stuNum = ? AND identity = 'child');", letterNumber, stuNum);
            if (studentAnswer.next()) {
                row.createCell(2).setCellValue(studentAnswer.getBoolean("answer") ? "YES" : "NO");
            } else {
                row.createCell(2).setCellValue("미응답");
            }
            parentAnswer = DBManager.execute("SELECT answer FROM letterAnswer WHERE letterNumber = ? AND uid = (SELECT uid FROM USER WHERE stuNum = ? AND identity = 'parent');", letterNumber, stuNum);
            if (parentAnswer.next()) {
                row.createCell(3).setCellValue(parentAnswer.getBoolean("answer") ? "YES" : "NO");
            } else {
                row.createCell(3).setCellValue("미응답");
            }
        }
        return statistic;
    }
}
