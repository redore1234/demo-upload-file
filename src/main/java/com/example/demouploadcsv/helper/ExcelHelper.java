package com.example.demouploadcsv.helper;

import com.example.demouploadcsv.domain.Tutorial;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type Excel helper.
 */
public class ExcelHelper {
    /**
     * The constant TYPE.
     */
    static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    /**
     * The headers.
     */
    static String[] headers = {"Id", "Title", "Description", "Published" };

    /**
     * The Sheet name.
     */
    static String sheetName = "Tutorials";

    /**
     * check if a file has Excel format or not
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**
     * read InputStream of a file, return a list of Tutorials
     *
     * @param inputStream the input stream
     * @return the list
     */
    public static List<Tutorial> excelToTutorials(InputStream inputStream) {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rows = sheet.iterator();

            List<Tutorial> tutorials = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellInRow = currentRow.iterator();

                Tutorial tutorial = new Tutorial();

                int cellIdx = 0;
                while (cellInRow.hasNext()) {
                    Cell currentCell = cellInRow.next();

                    switch (cellIdx) {
                        case 0:
                            tutorial.setTitle(currentCell.getStringCellValue());
                            break;
                        case 1:
                            tutorial.setDescription(currentCell.getStringCellValue());
                            break;
                        case 2:
                            tutorial.setPublished(currentCell.getBooleanCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                tutorials.add(tutorial);
            }
            workbook.close();

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    /**
     * Process a list of Tutorials and return a ByteArrayInputStream for Excel
     *
     * @param tutorials the tutorials
     * @return the byte array input stream
     */
    public static ByteArrayInputStream tutorialsToExcel(List<Tutorial> tutorials) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                Sheet sheet = workbook.createSheet(sheetName);

                // Header
                Row headerRow = sheet.createRow(0);

                for (int col = 0; col < headers.length; col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(headers[col]);
                }

                // Content
                int rowId = 1;
                for (Tutorial tutorial : tutorials) {
                    Row row = sheet.createRow(rowId++);

                    row.createCell(0).setCellValue(tutorial.getId());
                    row.createCell(1).setCellValue(tutorial.getTitle());
                    row.createCell(2).setCellValue(tutorial.getDescription());
                    row.createCell(3).setCellValue(tutorial.isPublished());
                }

                workbook.write(outputStream);
                return new ByteArrayInputStream(outputStream.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
            } finally {
                workbook.close();
            }
        }
    }
}
