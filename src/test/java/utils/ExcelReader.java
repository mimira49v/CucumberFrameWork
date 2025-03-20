package utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    private static Workbook book;
    private static Sheet sheet;

    public static void openExcel(String filePath) {
//      opened the file
        try {
            FileInputStream fis = new FileInputStream(filePath);
            book = new HSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//  open the sheet in excell file
    public static void getSheet(String sheetName){
        // identified the sheet we gonna load the data from
        sheet = book.getSheet(sheetName);
    }
//  it will return total no of rows available in the worksheet
    public static int getRowCount(){
//      returns only those rows that have the data  (getPhysicalNumberOfRows)
        return sheet.getPhysicalNumberOfRows();
    }
//  it will return the total no of columns in every row
    public static int getColsCount(int rowIndex){
        return sheet.getRow(rowIndex).getPhysicalNumberOfCells();
    }
//  it will return the data from cell in string format
    public static String getCellData(int rowIndex, int columnIndex){
        return sheet.getRow(rowIndex).getCell(columnIndex).toString();
    }

    public static List<Map<String, String>> excellIntoMap(String filePath, String sheetName){

        openExcel(filePath);
        getSheet(sheetName);

        List<Map<String, String>> ListData = new ArrayList<>();
        // outer loop
        for (int row = 1; row<getRowCount(); row++){
            // creating a map for every row
            Map<String, String> map = new LinkedHashMap<>();
            for (int col = 0; col < getColsCount(row); col++) {
                map.put(getCellData(0,col), getCellData(row, col));
            }
            ListData.add(map);
        }
        return ListData;
    }
}

