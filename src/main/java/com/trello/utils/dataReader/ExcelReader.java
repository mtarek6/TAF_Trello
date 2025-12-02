package com.trello.utils.dataReader;

import com.trello.utils.logs.LogsManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static final String TEST_DATA_PATH = "src/test/resources/test-data/";

    // row and col > 0
    // note: using rowNum/colNum starting from 0 (Apache POI is 0-based)
    public static String getExcelData(String excelFilename, String sheetName, int rowNum, int colNum) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String cellData;

        try (FileInputStream fis = new FileInputStream(TEST_DATA_PATH + excelFilename)) {
            workBook = new XSSFWorkbook(fis);
            sheet = workBook.getSheet(sheetName);
            cellData = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
            return cellData;
        } catch (IOException e) {
            LogsManager.error("Error reading Excel file: ", excelFilename, e.getMessage());
            return "";
        }
    }
}
