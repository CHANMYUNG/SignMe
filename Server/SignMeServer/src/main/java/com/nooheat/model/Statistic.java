package com.nooheat.model;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.SQLException;

/**
 * Created by NooHeat on 03/10/2017.
 */
public interface Statistic {
    XSSFWorkbook getStatistic() throws SQLException;
}
