package me.mpab.codingtest.dataset;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Michael Bailey - TopBlocCodingTest - me.mpab.codingtest.dataset - DataSet
 *
 * This class defines a data set from one Excel file with three different lists
 * as well as the multiply, divide, and concat operations
 */
public class DataSet {
    private List<Integer> numberSetOne;
    private List<Integer> numberSetTwo;
    private List<String> wordSet;

    public DataSet() {
        numberSetOne = new ArrayList<Integer>();
        numberSetTwo = new ArrayList<Integer>();
        wordSet = new ArrayList<String>();
    }

    public DataSet(String filePath) throws Exception {
        this();

        File excelFile = new File(filePath);
        Workbook wb;
        try {
            wb = WorkbookFactory.create(excelFile);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if(wb == null) {
            throw new Exception("Workbook creation failed");
        }

        Sheet sheet = wb.getSheetAt(0);

        createNumericList(sheet, 0, numberSetOne);
        createNumericList(sheet, 1, numberSetTwo);
        createStringList(sheet, 2, wordSet);

    }

    private void createNumericList(Sheet sheet, int columnIndex, List<Integer> list) {
        for(int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(columnIndex);
            list.add((int) cell.getNumericCellValue());
        }
    }

    private void createStringList(Sheet sheet, int columnIndex, List<String> list) {
        for(int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(columnIndex);
            list.add(cell.getStringCellValue());
        }
    }

    public List<Integer> getNumberSetOne() { return numberSetOne; }
    public List<Integer> getNumberSetTwo() { return numberSetTwo; }
    public List<String> getWordSet() { return wordSet; }

    public static List<Integer> multiply(DataSet a, DataSet b) {
        List<Integer> one = a.getNumberSetOne();
        List<Integer> two = b.getNumberSetOne();

        List<Integer> result = new ArrayList<Integer>(one.size());

        for(int i = 0; i < one.size(); i++) {
            result.add(one.get(i) * two.get(i));
        }

        return result;
    }

    public static List<Integer> divide(DataSet a, DataSet b) {
        List<Integer> one = a.getNumberSetTwo();
        List<Integer> two = b.getNumberSetTwo();

        List<Integer> result = new ArrayList<Integer>(one.size());

        for(int i = 0; i < one.size(); i++) {
            result.add(one.get(i) / two.get(i));
        }

        return result;
    }

    public static List<String> concat(DataSet a, DataSet b) {
        List<String> one = a.getWordSet();
        List<String> two = b.getWordSet();

        List<String> result = new ArrayList<String>(one.size());

        for(int i = 0; i < one.size(); i++) {
            result.add(String.format("%s %s", one.get(i), two.get(i)));
        }

        return result;
    }
}
