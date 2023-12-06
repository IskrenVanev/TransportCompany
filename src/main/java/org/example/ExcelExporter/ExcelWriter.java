
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
import java.time.format.DateTimeFormatter;
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
            headerRow.createCell(3).setCellValue("Departure Starting Point");
            headerRow.createCell(4).setCellValue("Departure Arrival Point");
            headerRow.createCell(5).setCellValue("Price for Mission");
            headerRow.createCell(6).setCellValue("Vehicle id");
            headerRow.createCell(7).setCellValue("Vehicle Type");
            headerRow.createCell(8).setCellValue("Content Type");
            headerRow.createCell(9).setCellValue("Weight");
            headerRow.createCell(10).setCellValue("Driver id");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Populate data
            for (int i = 0; i < missionData.size(); i++) {
                Row row = sheet.createRow(i + 1);
                TransportVehicleMission rowData = missionData.get(i);

                row.createCell(0).setCellValue(rowData.getId());
                row.createCell(1).setCellValue(rowData.getDateOfDeparture().format(formatter));
                row.createCell(2).setCellValue(rowData.getDateOfArrival().format(formatter));
                row.createCell(3).setCellValue(rowData.getDepartureStartingPoint().toString());
                row.createCell(4).setCellValue(rowData.getDepartureArrivalPoint().toString());
                row.createCell(5).setCellValue(rowData.getPriceForMission());
                row.createCell(6).setCellValue(rowData.getVehicle().getId());
                row.createCell(7).setCellValue(rowData.getVehicle().getVehicleType().toString());
                row.createCell(8).setCellValue(rowData.getContent().toString());
                row.createCell(9).setCellValue(rowData.getWeight() != null ? rowData.getWeight().toString() : "");
                row.createCell(10).setCellValue(rowData.getDriver() != null ? rowData.getDriver().getId() : 0);
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
