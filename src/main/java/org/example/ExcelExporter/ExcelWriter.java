
package org.example.ExcelExporter;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Models.TransportVehicleMission;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class ExcelWriter {

    public static void writeTransportationData(List<TransportVehicleMission> missionData, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Transportation Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Date of Departure");
            headerRow.createCell(2).setCellValue("Date of Arrival");
            // Add more columns as needed

            // Populate data
            for (int i = 0; i < missionData.size(); i++) {
                Row row = sheet.createRow(i + 1);
                TransportVehicleMission rowData = missionData.get(i);

                row.createCell(0).setCellValue(rowData.getId());
                row.createCell(1).setCellValue(rowData.getDateOfDeparture().toString());
                row.createCell(2).setCellValue(rowData.getDateOfArrival().toString());
                // Add more cells as needed
            }

            // Write the workbook to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
