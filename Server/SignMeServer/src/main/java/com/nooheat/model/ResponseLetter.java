package com.nooheat.model;

import com.nooheat.database.DBManager;
import com.nooheat.manager.UserManager;
import com.nooheat.support.Category;
import com.nooheat.support.DateTime;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
    private static final String INSERT = "INSERT INTO responseLetter(letterNumber, writerUid,title,contents,openDate,closeDate) VALUES(?,?,?,?,?,?);";
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

    //    private static final String ANSWER_COUNT_STUDENT_BY_LETTERNUMBER_COLUMNINDEX_ANSWER = "SELECT COUNT(uid) " +
//            "FROM surveyAnswer AS A " +
//            "WHERE letterNumber = ? AND (SELECT identity FROM USER AS U WHERE A.uid = U.uid) = 'child' AND columnIndex = ? AND answer = ?;";
    private static final String YES_COUNT_STUDENT_ALL = "SELECT COUNT(uid) AS count " +
            "FROM letterAnswer AS A " +
            "WHERE letterNumber = ? AND A.answer = true AND (SELECT identity FROM USER AS U WHERE A.uid = U.uid) = 'child';";

    private static final String YES_COUNT_STUDENT = "SELECT COUNT(uid) AS count " +
            "FROM letterAnswer AS A " +
            "WHERE letterNumber = ? AND A.answer = true " +
            "AND (SELECT identity FROM USER  WHERE A.uid = uid) = 'child' " +
            "AND (SELECT stuNum FROM USER WHERE uid = A.uid AND identity = 'child') like ?;";

    private static final String YES_COUNT_PARENT_ALL = "SELECT COUNT(uid) AS count " +
            "FROM letterAnswer AS A " +
            "WHERE letterNumber = ? AND A.answer = true AND (SELECT identity FROM USER AS U WHERE A.uid = U.uid) = 'parent';";

    private static final String YES_COUNT_PARENT = "SELECT COUNT(uid) AS count " +
            "FROM letterAnswer AS A " +
            "WHERE letterNumber = ? AND A.answer = true " +
            "AND (SELECT identity FROM USER  WHERE A.uid = uid) = 'parent' " +
            "AND (SELECT stuNum FROM USER WHERE uid = A.uid AND identity = 'parent') like ?;";

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
        return DBManager.update(INSERT, letterNumber, writerUid, title, contents, openDate, closeDate) != -1;
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

    public ResponseLetter() throws SQLException {
        this.letterNumber = getNextLetterNumber();
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
        XSSFSheet firstSheet = statistic.createSheet("통계");
        firstSheet = createFirstSheet(firstSheet);

        XSSFSheet secondSheet = statistic.createSheet("세부 통계");
        secondSheet = createSecondSheet(secondSheet);

        return statistic;
    }

    private XSSFSheet createFirstSheet(XSSFSheet sheet) throws SQLException {
        DecimalFormat form = new DecimalFormat("#.##");

        int rowCount = 0;
        XSSFRow row = sheet.createRow(rowCount++);

        row.createCell(0).setCellValue("제목");
        row.createCell(1).setCellValue(title);
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("작성자");
        row.createCell(1).setCellValue(writerName);
        rowCount++;
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("응답 현황");
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("학생");
        row.createCell(1).setCellValue("1반");
        row.createCell(2).setCellValue("2반");
        row.createCell(3).setCellValue("3반");
        row.createCell(4).setCellValue("4반");
        row.createCell(5).setCellValue("총합");
        for (int i = 1; i <= 3; i++) {
            row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(i + "학년");
            for (int j = 1; j <= 4; j++) {
                if (UserManager.getChildCount(i, j) == 0) row.createCell(j).setCellValue("학생이 없음");
                else
                    row.createCell(j).setCellValue(getStudentAnswerCount(i, j) + " (" + form.format(getStudentAnswerCount(i, j) / (double) UserManager.getChildCount(i, j) * 100) + "%)");
            }
            if (UserManager.getChildCount(i) == 0) row.createCell(5).setCellValue("학생이 없음");
            else
                row.createCell(5).setCellValue(getStudentAnswerCount(i) + " (" + form.format(getStudentAnswerCount(i) / (double) UserManager.getChildCount(i) * 100) + "%)");
        }

        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("총합");
        row.createCell(5).setCellValue(getStudentAnswerCount() + " (" + form.format(getStudentAnswerCount() / (double) UserManager.getChildCount() * 100) + "%)");

        rowCount++;
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("학부모");
        row.createCell(1).setCellValue("1반");
        row.createCell(2).setCellValue("2반");
        row.createCell(3).setCellValue("3반");
        row.createCell(4).setCellValue("4반");
        row.createCell(5).setCellValue("총합");
        for (int i = 1; i <= 3; i++) {
            row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(i + "학년");
            for (int j = 1; j <= 4; j++) {
                if (UserManager.getParentCount(i, j) == 0) row.createCell(j).setCellValue("학부모 없음");
                else
                    row.createCell(j).setCellValue(getParentAnswerCount(i, j) + " (" + form.format(getParentAnswerCount(i, j) / (double) UserManager.getParentCount(i, j) * 100) + "%)");
            }
            if (UserManager.getChildCount(i) == 0) row.createCell(5).setCellValue("학부모 없음");
            else
                row.createCell(5).setCellValue(getParentAnswerCount(i) + " (" + form.format(getParentAnswerCount(i) / (double) UserManager.getParentCount(i) * 100) + "%)");
        }

        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("총합");
        row.createCell(5).setCellValue(getParentAnswerCount() + " (" + form.format(getParentAnswerCount() / (double) UserManager.getParentCount() * 100) + "%)");

        rowCount++;
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("YES 통계");

        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("학생");
        row.createCell(1).setCellValue("1반");
        row.createCell(2).setCellValue("2반");
        row.createCell(3).setCellValue("3반");
        row.createCell(4).setCellValue("4반");
        row.createCell(5).setCellValue("총합");
        for (int i = 1; i <= 3; i++) {
            row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(i + "학년");
            for (int j = 1; j <= 4; j++) {
                if (getStudentAnswerCount(i, j) == 0) row.createCell(j).setCellValue("학생이 없음");
                else
                    row.createCell(j).setCellValue(getStudentYesCount(i, j) + " (" + form.format(getStudentYesCount(i, j) / (double) getStudentAnswerCount(i, j) * 100) + "%)");
            }
            if (getStudentAnswerCount(i) == 0) row.createCell(5).setCellValue("학생이 없음");
            else
                row.createCell(5).setCellValue(getStudentYesCount(i) + " (" + form.format(getStudentYesCount(i) / (double) getStudentAnswerCount(i) * 100) + "%)");
        }

        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("총합");
        row.createCell(5).setCellValue(getStudentYesCount() + " (" + form.format(getStudentYesCount() / (double) getStudentAnswerCount() * 100) + "%)");

        rowCount++;
        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("학부모");
        row.createCell(1).setCellValue("1반");
        row.createCell(2).setCellValue("2반");
        row.createCell(3).setCellValue("3반");
        row.createCell(4).setCellValue("4반");
        row.createCell(5).setCellValue("총합");
        for (int i = 1; i <= 3; i++) {
            row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(i + "학년");
            for (int j = 1; j <= 4; j++) {
                if (getParentAnswerCount(i, j) == 0) row.createCell(j).setCellValue("학부모 없음");
                else
                    row.createCell(j).setCellValue(getParentYesCount(i, j) + " (" + form.format(getParentYesCount(i, j) / (double) getParentAnswerCount(i, j) * 100) + "%)");
            }
            if (getParentAnswerCount(i) == 0) row.createCell(5).setCellValue("학부모 없음");
            else
                row.createCell(5).setCellValue(getParentYesCount(i) + " (" + form.format(getParentYesCount(i) / (double) getParentAnswerCount(i) * 100) + "%)");
        }

        row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue("총합");
        row.createCell(5).setCellValue(getParentYesCount() + " (" + form.format(getParentYesCount() / (double) getParentAnswerCount() * 100) + "%)");

        return sheet;
    }

    private XSSFSheet createSecondSheet(XSSFSheet sheet) throws SQLException {
        int rowCount = 0;
        XSSFRow row = sheet.createRow(rowCount++);
        Iterator<Integer> stuNumIterator = UserManager.getStuNumIterator();
        row.createCell(1).setCellValue("학번");
        row.createCell(2).setCellValue("학생 이름");
        row.createCell(3).setCellValue("학생 응답");
        row.createCell(4).setCellValue("학부모 응답");

        while (stuNumIterator.hasNext()) {
            row = sheet.createRow(rowCount++);

            int stuNum = stuNumIterator.next();

            row.createCell(1).setCellValue(stuNum);
            ResultSet studentName = DBManager.execute("SELECT name FROM USER WHERE stuNum = ? AND identity = 'child';", stuNum);
            if (studentName.next()) {
                row.createCell(2).setCellValue(studentName.getString("name"));
            } else {
                row.createCell(2).setCellValue("UNKNOWN");
            }
            ResultSet studentAnswer = DBManager.execute("SELECT answer FROM letterAnswer WHERE letterNumber = ? AND uid = (SELECT uid FROM USER WHERE stuNum = ? AND identity = 'child');", letterNumber, stuNum);
            if (studentAnswer.next()) {
                row.createCell(3).setCellValue(studentAnswer.getBoolean("answer") ? "YES" : "NO");
            } else {
                row.createCell(3).setCellValue("미응답");
            }
            ResultSet parentAnswer = DBManager.execute("SELECT answer FROM letterAnswer WHERE letterNumber = ? AND uid = (SELECT uid FROM USER WHERE stuNum = ? AND identity = 'parent');", letterNumber, stuNum);
            if (parentAnswer.next()) {
                row.createCell(4).setCellValue(parentAnswer.getBoolean("answer") ? "YES" : "NO");
            } else {
                row.createCell(4).setCellValue("미응답");
            }
        }

        return sheet;
    }

    private int getStudentAnswerCount() throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_STUDENT_ALL, letterNumber);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    private int getStudentAnswerCount(int grade) throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_STUDENT, letterNumber, grade + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getStudentAnswerCount(int grade, int Class) throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_STUDENT, letterNumber, grade + "0" + Class + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getParentAnswerCount() throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_PARENT_ALL, letterNumber);
        rs.next();
        return rs.getInt("count");
    }

    private int getParentAnswerCount(int grade) throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_PARENT, letterNumber, grade + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getParentAnswerCount(int grade, int Class) throws SQLException {
        ResultSet rs = DBManager.execute(ANSWER_COUNT_PARENT, letterNumber, grade + "0" + Class + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getStudentYesCount() throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_STUDENT_ALL, letterNumber);
        if (rs.next())
            return rs.getInt("count");
        else return 0;
    }

    private int getStudentYesCount(int grade) throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_STUDENT, letterNumber, grade + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getStudentYesCount(int grade, int Class) throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_STUDENT, letterNumber, grade + "0" + Class + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getParentYesCount() throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_PARENT_ALL, letterNumber);
        rs.next();
        return rs.getInt("count");
    }

    private int getParentYesCount(int grade) throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_PARENT, letterNumber, grade + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getParentYesCount(int grade, int Class) throws SQLException {
        ResultSet rs = DBManager.execute(YES_COUNT_PARENT, letterNumber, grade + "0" + Class + "%");
        rs.next();
        return rs.getInt("count");
    }

    private int getNextLetterNumber() throws SQLException {
        ResultSet rs = DBManager.execute("SELECT max(letterNumber) AS max FROM responseLetter");
        if (rs.next()) {
            return rs.getInt("max") + 1;
        } else {
            return 1;
        }
    }

    public boolean saveUpdated() throws SQLException {
        boolean updated = DBManager.update("UPDATE responseLetter " +
                "SET title = ?, contents = ?, openDate = ?, closeDate = ? " +
                "WHERE letterNumber = ?", title, contents, openDate, closeDate, letterNumber) != -1;

        boolean answersDeleted = DBManager.update("DELETE FROM letterAnswer WHERE letterNumber = ?", letterNumber) != -1;

        return updated && answersDeleted;
    }

    public boolean delete() throws SQLException {
        boolean letterDeleted = DBManager.update("DELETE FROM responseLetter WHERE letterNumber = ?", letterNumber) != -1;
        boolean answersDeleted = DBManager.update("DELETE FROM letterAnswer WHERE letterNumber = ?", letterNumber) != -1;

        return letterDeleted && answersDeleted;
    }
}
