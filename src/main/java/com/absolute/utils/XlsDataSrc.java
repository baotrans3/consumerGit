package com.absolute.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
/**
 * 
 *
 *
 */
public class XlsDataSrc {

    /**
     * Get the sheet name of sheetNo in fileName
     *
     * @param fileName
     * @param sheetNo
     * @return
     */
    public String getSheetName(String fileName, int sheetNo) {

        String[] names = null;
        try {
            names = getTotalSheetNames(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return names[sheetNo];
    }

    /**
     * Get array names of all sheets in fileName
     *
     * @param fileName
     * @return
     */
    public String[] getTotalSheetNames(String fileName) throws IOException {
        BufferedInputStream input = null;
        Workbook wb = null;
        int NoSheets = 1;
        try {
            input = new BufferedInputStream(new FileInputStream(fileName));
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            NoSheets = wb.getNumberOfSheets();

            String[] names = new String[NoSheets];
            for (int i = 0; i < NoSheets; i++) {
                names[i] = wb.getSheetName(i);
            }
            // Total sheet in an xls file			
            return names;
        } finally {
            input.close();

        }

    }

    /**
     * Read all rows in a sheet of excel file
     *
     * @param fileName
     * @param sheetName
     * @return list of String[], each line is a String[]
     */
    public List<String[]> readXLSFile(String fileName, String sheetName) throws IOException {
        List<String[]> contents = new ArrayList<String[]>();
        BufferedInputStream input = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(fileName));
            try {
                wb = WorkbookFactory.create(input);
                if (wb instanceof HSSFWorkbook) {
                    HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
                }
                if (wb instanceof XSSFWorkbook) {
                    XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) wb);
                }
            } catch (IOException ex) {
                System.out.println("Cannot read file(" + fileName + "): " + ex.getMessage());
            }
            Sheet sheet = wb.getSheet(sheetName);
            int col = sheet.getRow(0).getLastCellNum();
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // Iterator<Cell> cells = row.cellIterator();
                String[] rowsData = new String[col];
                for (int i = 0; i < col; i++) {
                    Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            rowsData[i] = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            rowsData[i] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date d = cell.getDateCellValue();
                                rowsData[i] = new SimpleDateFormat("MM/dd/yyyy").format(d);
                            } else {
                                rowsData[i] = String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", "");
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowsData[i] = cell.getStringCellValue();
                    }
                }
                contents.add(rowsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            input.close();
        }
        return contents;
    }

    /**
     * Read all rows in a sheet of excel file
     *
     * @param fileName
     * @param sheetIndex
     * @param dateFormat
     * @return list of String[], each line is a String[]
     * @throws java.io.IOException
     */
    public List<String[]> readXLSFile(String fileName, int sheetIndex, String... dateFormat) throws IOException, InvalidFormatException {
        List<String[]> contents = new ArrayList<String[]>();
        BufferedInputStream input = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(fileName));
            wb = WorkbookFactory.create(input);
            if (wb instanceof HSSFWorkbook) {
                HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            }
            if (wb instanceof XSSFWorkbook) {
                XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook) wb);
            }
            Sheet sheet = wb.getSheetAt(sheetIndex);
            int col = sheet.getRow(0).getLastCellNum();
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // Iterator<Cell> cells = row.cellIterator();
                String[] rowsData = new String[col];
                for (int i = 0; i < col; i++) {
                    Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            rowsData[i] = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            rowsData[i] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date d = cell.getDateCellValue();
                                if (dateFormat.length == 1) {
                                    rowsData[i] = new SimpleDateFormat(dateFormat[0]).format(d);
                                } else {
                                    rowsData[i] = new SimpleDateFormat("MM/dd/yyyy").format(d);
                                }
                            } else {
                                rowsData[i] = String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", "");
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowsData[i] = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            if (cell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC) {
                                rowsData[i] = String.valueOf(cell.getNumericCellValue());
                            }
                            if (cell.getCachedFormulaResultType() == Cell.CELL_TYPE_STRING) {
                                rowsData[i] = cell.getRichStringCellValue().getString();
                            }
                            break;
                    }
                }
                contents.add(rowsData);
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return contents;
    }

    public List<String[]> readXLSFileDynCol(String filename, String sheetName) throws IOException, InvalidFormatException {
        List<String[]> allTestData = new ArrayList<>();
        BufferedInputStream input = null;
        // String[][] arrayOut = null;
        Workbook wb;
        try {
            input = new BufferedInputStream(new FileInputStream(filename));
            wb = WorkbookFactory.create(input);
            int sheetNo = wb.getSheetIndex(sheetName);
            Sheet sheet = wb.getSheetAt(sheetNo);

            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                List<String> rowsData = new ArrayList<>();

                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            rowsData.add("");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            rowsData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            // cell.getNumericCellValue().
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date d = cell.getDateCellValue();
                                rowsData.add(new SimpleDateFormat("MM/dd/yyyy").format(d));
                            } else if (cell.getCellStyle().getDataFormatString().contains("%")) {
                                // Detect Percent Values
                                String percent = Double.toString(cell.getNumericCellValue());
                                // Float value = Float.parseFloat(percent) * 100;
                                Float value = new Float(percent);
                                BigDecimal bd = new BigDecimal(Float.toString(value * 100));

                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                value = bd.floatValue();
                                // value = Math.round(value*100);
                                // Double value = cell.getNumericCellValue() * 100;
                                String str = value.toString();
                                if (percent.equals("1.0")) {
                                    rowsData.add("100.00%");
                                } else {
                                    String substr = str.substring(str.indexOf("."));
                                    if (substr.length() == 3) {
                                        rowsData.add(value.toString() + "%");
                                    } else if (substr.length() > 3) {
                                        rowsData.add(str.substring(0, 5) + "%");
                                    } else {
                                        rowsData.add((str + "00").substring(0, 5) + "%");
                                    }
                                }

                            } else {
                                rowsData.add(String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", ""));
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowsData.add(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                String[] arrRows = new String[rowsData.size()];
                for (int j = 0; j < rowsData.size(); j++) {
                    arrRows[j] = rowsData.get(j);
                }
                allTestData.add(arrRows);
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return allTestData;
        // return arrayOut;
    }

    /**
     * Why we need this function?????
     *
     * @param filename
     * @param col
     * @param sheetName
     * @return
     */
    public List<String[]> readXLSFileWithEmptyCell(String filename, int col, String sheetName) throws IOException, InvalidFormatException {
        List<String[]> allTestData = new ArrayList<>();
        BufferedInputStream input = null;
        // String[][] arrayOut = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(filename));
            wb = WorkbookFactory.create(input);
            int sheetNo = wb.getSheetIndex(sheetName);
            Sheet sheet = wb.getSheetAt(sheetNo);
            // wb.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);
            for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
                Row row = sheet.getRow(rn);
                String[] rowsData = new String[col];
                if (row == null) {
                    // There is no data in this row, handle as needed
                } else {
                    // Row "rn" has data
                    for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                        Cell cell = row.getCell(cn);
                        if (cell == null) {
                            // This cell is empty/blank/un-used, handle as needed
                            rowsData[cn] = null;
                        } else if (Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                            rowsData[cn] = null;
                        } else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
                            rowsData[cn] = String.valueOf(cell.getBooleanCellValue());
                        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            // cell.getNumericCellValue().
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date d = cell.getDateCellValue();
                                rowsData[cn] = new SimpleDateFormat("MM/dd/yyyy").format(d);
                            } else {
                                rowsData[cn] = String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", "");
                            }
                        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            rowsData[cn] = cell.getStringCellValue();
                        } else if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
                            switch (cell.getCachedFormulaResultType()) {
                                case Cell.CELL_TYPE_STRING:
                                    rowsData[cn] = cell.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    rowsData[cn] = String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", "");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                allTestData.add(rowsData);
            }
        } finally {
            input.close();
        }
        return allTestData;
    }

    /**
     * Read all rows in excel file: consider date format
     *
     * @param filename
     * @param col
     * @return list of String[], each line is a String[]
     */
    public List<String[]> readXLSFile(String filename, int col, String sheetName) throws IOException, InvalidFormatException {
        List<String[]> allTestData = new ArrayList<>();
        BufferedInputStream input = null;
        // String[][] arrayOut = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(filename));
            wb = WorkbookFactory.create(input);

            int sheetNo = wb.getSheetIndex(sheetName);
            Sheet sheet = wb.getSheetAt(sheetNo);

            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                String[] rowsData = new String[col];
                int i = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BLANK:
                            rowsData[i] = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            rowsData[i] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            // cell.getNumericCellValue().
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date d = cell.getDateCellValue();
                                rowsData[i] = new SimpleDateFormat("MM/dd/yyyy").format(d);
                            } else {
                                rowsData[i] = String.valueOf(cell.getNumericCellValue()).replaceFirst("\\.0+$", "");
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            rowsData[i] = cell.getStringCellValue();
                            break;
                        default:
                            break;
                    }

                    i++;
                }
                allTestData.add(rowsData);
            }
            /*
             * arrayOut = new String[allTestData.size()][col]; for(int j=0;
             * j<allTestData.size();j++) arrayOut[j] = allTestData.get(j);
             */
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return allTestData;
        // return arrayOut;
    }

    /**
     * Read the comments in all rows of excel file, comments are strings
     *
     * @param filename
     * @param col
     * @return list of String[], if a cell has no comment -> it returns ""
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public List<String[]> readXLSFileGetComments(String filename, int col) throws IOException, InvalidFormatException {
        List<String[]> allTestData = new ArrayList<>();
        BufferedInputStream input = null;
        Workbook wb;
        try {
            input = new BufferedInputStream(new FileInputStream(filename));
            wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                String[] rowsData = new String[col];

                int i = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    Comment comment;
                    if (cell != null) {
                        comment = cell.getCellComment();
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BLANK:
                                rowsData[i] = "";
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                rowsData[i] = "";
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (comment != null) {
                                    rowsData[i] = comment.getString().toString();
                                } else {
                                    rowsData[i] = "";
                                }
                                break;
                            case Cell.CELL_TYPE_STRING:
                                if (comment != null) {
                                    rowsData[i] = comment.getString().toString();
                                } else {
                                    rowsData[i] = "";
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        rowsData[i] = "";
                    }
                    i++;
                }
                allTestData.add(rowsData);
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return allTestData;
    }

    /**
     * Write a value into 1 cell (row, col) in excel file outFile
     *
     * @param outFile
     * @param sheetName
     * @param row
     * @param col
     * @param value
     * @throws java.io.IOException
     */
    public void writeValue2XLSFile(String outFile, String sheetName, int row, short col, String value) throws IOException {
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        Workbook wb;
        try {
            input = new BufferedInputStream(new FileInputStream(outFile));
            wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheet(sheetName);
            Row content = sheet.getRow(row);
            content.createCell(col).setCellValue(value);
            output = new BufferedOutputStream(new FileOutputStream(outFile));
            wb.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    public void writeValue2XLSFile(String outFile, int sheetNum, int row, short col, String value) throws IOException {
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(outFile));
            // POIFSFileSystem fs = new POIFSFileSystem( input );
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Sheet sheet = wb.getSheetAt(sheetNum);
            Row content = sheet.getRow(row);
            content.createCell(col).setCellValue(value);

            /*			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
             for(int sheetNum = 0; sheetNum < wb.getNumberOfSheets(); sheetNum++) {
             sheet = wb.getSheetAt(sheetNum);
             for(Row r : sheet) {
             for(Cell c : r) {
             if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
             evaluator.evaluateFormulaCell(c);
             }
             }
             }
             }*/
            output = new BufferedOutputStream(new FileOutputStream(outFile));
            wb.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * Write list of String[] values into excel file outFile
     *
     * @param outFile
     * @param allTestData
     */
    public void writeXLSFile(String outFile, String sheetName, String[] outHeader, List<String[]> allTestData) throws IOException {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File(outFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.createSheet(sheetName);
        BufferedOutputStream fileOut = null;
        // Create a row and put some cells in it. Rows are 0 based.
        // Should create a header row with dynamic size of column
        Row header = sheet.createRow((short) 0);
        for (int i = 0; i < outHeader.length; i++) {
            header.createCell(i).setCellValue(outHeader[i]);
        }

        for (int key = 0; key < allTestData.size(); key++) {
            String[] values = allTestData.get(key);
            Row row = sheet.createRow(key + 1);
            // Create a cell and put a value in it.
            for (int k = 0; k < values.length; k++) {
                row.createCell(k).setCellValue(values[k]);
            }
        }
        try {
            // Write the output to a file
            fileOut = new BufferedOutputStream(new FileOutputStream(outFile));
            wb.write(fileOut);

        } finally {
            fileOut.close();
        }
    }

    /**
     * Return selected values of a type group with the determined headerNo.
     *
     * @param datasrc
     * @par @param type
     * @param headerNo tell the row index
     * @return the selected views of compiler
     */
    public String[] selectedValueOfType(List<String[]> datasrc, String type, int headerNo, int rowNo) {
        if (headerNo > rowNo || rowNo > datasrc.size()) {
            return null;
        }
        String[] headers = datasrc.get(headerNo - 1);
        String[] data = datasrc.get(headerNo);
        String[] selectedValues = datasrc.get(rowNo - 1);
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(type)) {
                int start = i;
                int end = start + 1;
                while ("".equals(headers[end])) {
                    end++;
                }
                for (int j = start; j < end; j++) {
                    if (selectedValues[j].equals("x")) {
                        result.add(data[j]);
                    }
                }
                break;
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public void replaceAll(String fileName, int sheetNum, String oldValue, String newValue) throws IOException {

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(fileName));
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Sheet sheet = wb.getSheetAt(sheetNum);
            int col;
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                col = row.getLastCellNum();
                for (int i = 0; i < col; i++) {
                    Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().equals(oldValue)) {
                        cell.setCellValue(newValue);
                    }
                }
            }
            output = new BufferedOutputStream(new FileOutputStream(fileName));
            wb.write(output);
        } finally {
            output.close();
            input.close();
        }
    }

    public void replaceAll2003(String fileName, int sheetNum, String oldValue, String newValue) throws IOException {

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(fileName));
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Sheet sheet = wb.getSheetAt(sheetNum);
            int col;
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                col = row.getLastCellNum();
                for (int i = 0; i < col; i++) {
                    Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().equals(oldValue)) {
                        cell.setCellValue(newValue);
                    }
                }
            }
            output = new BufferedOutputStream(new FileOutputStream(fileName));
            wb.write(output);
        } finally {
            output.close();
            input.close();
        }
    }

    public String[] compareCells(String file1, String file2, int sheetNo1, int sheetNo2, int rowNo, int colNo) {

        BufferedInputStream input = null;
        Workbook wb = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file1));
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Sheet sheet = wb.getSheetAt(sheetNo1);
            sheet.getSheetName();
            Row row = sheet.getRow(rowNo);
            Cell cell = row.getCell((short) colNo);
            Double value1 = cell.getNumericCellValue();
            input.close();
            input = new BufferedInputStream(new FileInputStream(file2));
            try {
                wb = WorkbookFactory.create(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sheet = wb.getSheetAt(sheetNo2);
            row = sheet.getRow(rowNo);
            cell = row.getCell((short) colNo);
            Double value2 = cell.getNumericCellValue();
            input.close();
            if (Math.round(value2) == Math.round(value1)) {
                return null;
            } else {
                return new String[]{String.valueOf(Math.round(value1)), String.valueOf(Math.round(value2))};
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{"F"};
        }

    }

    public static int toNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length(); i++) {
            number = number * 26 + (name.charAt(i) - ('A' - 1));
        }
        return number - 1;
    }

    public static String toName(int number) {
        StringBuilder sb = new StringBuilder();
        while (number-- > 0) {
            sb.append((char) ('A' + (number % 26)));
            number /= 26;
        }
        return sb.reverse().toString();
    }

}
