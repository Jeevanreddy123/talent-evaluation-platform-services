
package com.talentEvaluation.helper;

import com.talentEvaluation.enums.DifficultyLevel;
import com.talentEvaluation.entity.Question;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Question> excelToQuestions(InputStream is, String technology) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            DataFormatter formatter = new DataFormatter();
            List<Question> questions = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();
                Question question = new Question();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    String cellValue = formatter.formatCellValue(currentCell);
                    switch (cellIdx) {
                        case 0:
                            question.setQuestionText(cellValue);
                            break;
                        case 1:
                            question.setAnswer(cellValue);
                            break;
                        case 2:
                            try {
                                DifficultyLevel level = DifficultyLevel.valueOf(cellValue.toUpperCase());
                                question.setDifficultyLevel(level);
                            } catch (IllegalArgumentException e) {
                                throw new RuntimeException("Invalid difficulty level '" + cellValue + "' found in row " + (rowNumber + 1));
                            }
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                question.setTechnology(technology);
                questions.add(question);
            }
            workbook.close();
            return questions;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage(), e);
        }
    }
}
