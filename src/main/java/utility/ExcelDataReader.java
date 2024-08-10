package utility;



import constants.EnvConstants;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelDataReader extends EnvConstants {

	private static ExcelDataReader instance = null;
    static String TEST_DATASHEET_PATH =EnvHelper.getValue(EnvConstants.inputDataPath);
	static String RUNORDER_SHEET =EnvHelper.getValue(EnvConstants.inputRunOrderSheet);
	public static Map<String, String> constdata = null;

//    static {
//        try {
//            constdata = getTestData();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception as needed
//        }
//    }
	
    
	public static Map<String, String> getConstData() throws IOException {
        if (constdata == null) {
            constdata = getTestData();
        }
        return constdata;
    }
//    public static void main(String[] args) {
//        try {
//            Map<String, Object> testData = getTestData();
//            if (testData != null) {
//                System.out.println(testData);
//            } else {
//                System.out.println("No matching data found");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static Map<String, String> getTestData() throws IOException {
    	 System.out.println("***************Get Data****************");
        // Read the Excel file
    	// Load file using ClassLoader
    	
    	String excelFilePath =  TEST_DATASHEET_PATH;
	    System.out.println("excelFilePathgettestdata: " + excelFilePath);
//        ClassLoader classLoader = ExcelDataReader.class.getClassLoader();
//        System.out.println("TEST_DATASHEET_PATH "+ TEST_DATASHEET_PATH);

//        InputStream inputStream = classLoader.getResourceAsStream(TEST_DATASHEET_PATH);
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(RUNORDER_SHEET);
//        System.out.println(sheet.getSheetName());
        // Convert sheet to a list of maps
        List<Map<String, Object>> data = sheetToJSON(sheet);
       // System.out.println("data  " + data);
        // Find the row with the specified column value
        String testcase = TEST_CASE_ID ;
        
        System.out.println("TEST_CASE_ID: " + TEST_CASE_ID);
//        System.out.println("TEST_CASE_ID  " + TEST_CASE_ID);
        Map<String, Object> datarow = data.stream()
            .filter(obj -> testcase.equals(obj.get("pScriptName").toString().trim()))
            .findFirst()
            .orElse(null);
        
        Map<String, String> stringDataRow = datarow.entrySet()
        	    .stream()
        	    .collect(Collectors.toMap(
        	        Map.Entry::getKey,
        	        entry -> entry.getValue().toString().trim()
        	    ));

        workbook.close();
        inputStream.close();
     //   System.out.println("datarow  " + datarow);
        return stringDataRow;
    }
    
    
    public static ExcelDataReader getInstance() {
        if (instance == null) {
            instance = new ExcelDataReader();
        }
        return instance;
    }

    private static List<Map<String, Object>> sheetToJSON(Sheet sheet) {
        List<Map<String, Object>> data = new java.util.ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        Row headerRow = rowIterator.next();
        List<String> headers = new java.util.ArrayList<>();

        for (Cell cell : headerRow) {
            headers.add(cell.getStringCellValue());
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Map<String, Object> rowData = new HashMap<>();

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = row.getCell(i);
                if (cell != null) {
                    rowData.put(headers.get(i), getCellValue(cell));
                }
            }

            data.add(rowData);
        }

        return data;
    }

    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

	
}
