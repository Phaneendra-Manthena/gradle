package com.bhargo.user.generatefiles;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelUtils {
    public static final String TAG = "ExcelUtil";
    private static Cell cell;
    private static Sheet sheet;
    private static Workbook workbook;
    private static CellStyle headerCellStyle;

    private static List<List<String>> importedExcelData;

    /**
     * Import data from Excel Workbook
     *
     * @param context  - Application Context
     * @param fileName - Name of the excel file
     * @return importedExcelData
     */
    public static List<List<String>> readFromExcelWorkbook(Context context, String fileName) {
        return retrieveExcelFromStorage(context, fileName);
    }


    /**
     * Export Data into Excel Workbook
     *
     * @param context  - Pass the application context
     * @param filePath - Pass the desired fileName for the output excel Workbook
     * @param dataList - Contains the actual data to be displayed in excel
     */
    public static boolean exportDataIntoWorkbook(Context context, File filePath, String sheetName,
                                                 List<List<String>> dataList, List<DataTableColumn_Bean> dataTableColumn_beans, ControlObject controlObject) {
        boolean isWorkbookWrittenIntoStorage;

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = new HSSFWorkbook();

        setHeaderCellStyle();

        // Creating a New Sheet and Setting width for each column
        sheet = workbook.createSheet(sheetName);
        //sheet.setColumnWidth(0, (15 * 400));
        // sheet.setColumnWidth(1, (15 * 400));

        List<String> headerNames = new ArrayList<>();
        for (int j = 0; j < dataTableColumn_beans.size(); j++) {
            boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();
            if (isEnabled) {
                headerNames.add(controlObject.getDisplayName() + " " + dataTableColumn_beans.get(j).getColumnName());
            }
        }
        for (int i = 0; i < headerNames.size(); i++) {
            sheet.setColumnWidth(i, (15 * 400));
        }

        setHeaderRow(dataTableColumn_beans, controlObject, headerNames);
        fillDataIntoExcel(dataList);
        isWorkbookWrittenIntoStorage = storeExcelInStorage(context, filePath);

        return isWorkbookWrittenIntoStorage;
    }

    /**
     * Checks if Storage is READ-ONLY
     *
     * @return boolean
     */
    private static boolean isExternalStorageReadOnly() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState);
    }

    /**
     * Checks if Storage is Available
     *
     * @return boolean
     */
    private static boolean isExternalStorageAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(externalStorageState);
    }

    /**
     * Setup header cell style
     */
    private static void setHeaderCellStyle() {
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    /**
     * Setup Header Row
     */
    private static void setHeaderRow(List<DataTableColumn_Bean> dataTableColumn_beans,
                                     ControlObject controlObject, List<String> headerNames) {


        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headerNames.size(); i++) {
            cell = headerRow.createCell(i);
            cell.setCellValue(headerNames.get(i));
            cell.setCellStyle(headerCellStyle);
        }


    }

    /**
     * Fills Data into Excel Sheet
     * <p>
     * NOTE: Set row index as i+1 since 0th index belongs to header row
     *
     * @param dataList - List containing data to be filled into excel
     */
    private static void fillDataIntoExcel(List<List<String>> dataList) {
        for (int x = 0; x < dataList.size(); x++) {
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(x + 1);
            List<String> data = dataList.get(x);
            for (int y = 0; y < data.size(); y++) {
                // Create Cells for each row
                cell = rowData.createCell(y);
                cell.setCellValue(data.get(y));
            }
        }
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param filePath - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    private static boolean storeExcelInStorage(Context context, File filePath) {
        boolean isSuccess;
        //File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            Log.e(TAG, "Writing file" + filePath);
            isSuccess = true;
        } catch (IOException e) {
            Log.e(TAG, "Error writing Exception: ", e);
            isSuccess = false;
        } catch (Exception e) {
            Log.e(TAG, "Failed to save file due to Exception: ", e);
            isSuccess = false;
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return isSuccess;
    }

    /**
     * Retrieve excel from External Storage
     *
     * @param context  - application context
     * @param fileName - name of workbook to be read
     * @return importedExcelData
     */
    private static List<List<String>> retrieveExcelFromStorage(Context context, String fileName) {
        importedExcelData = new ArrayList<>();

        File file = new File(context.getExternalFilesDir(null), fileName);
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            Log.e(TAG, "Reading from Excel" + file);

            // Create instance having reference to .xls file
            workbook = new HSSFWorkbook(fileInputStream);

            // Fetch sheet at position 'i' from the workbook
            sheet = workbook.getSheetAt(0);

            // Iterate through each row
            for (Row row : sheet) {
                int index = 0;
                List<String> rowDataList = new ArrayList<>();
                //List<String> phoneNumberList = new ArrayList<>();

                if (row.getRowNum() > 0) {
                    // Iterate through all the columns in a row (Excluding header row)
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        // Check cell type and format accordingly
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:

                                break;
                            case Cell.CELL_TYPE_STRING:
                                rowDataList.add(index, cell.getStringCellValue());
                                index++;
                                break;
                        }
                    }

                    // Adding cells with phone numbers to phoneNumberList
                   /* for (int i = 1; i < rowDataList.size(); i++) {
                        rowDataList.add(rowDataList.get(i));
                    }*/
                    if (rowDataList.size() > 0) {
                        rowDataList.add(rowDataList.get(1));
                    }

                    /**
                     * Index 0 of rowDataList will Always have name.
                     * So, passing it as 'name' in ContactResponse
                     *
                     * Index 1 onwards of rowDataList will have phone numbers (if >1 numbers)
                     * So, adding them to phoneNumberList
                     *
                     * Thus, importedExcelData list has appropriately mapped data
                     */


                    importedExcelData.add(rowDataList);
                }

            }

        } catch (IOException e) {
            Log.e(TAG, "Error Reading Exception: ", e);

        } catch (Exception e) {
            Log.e(TAG, "Failed to read file due to Exception: ", e);

        } finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return importedExcelData;
    }

}
