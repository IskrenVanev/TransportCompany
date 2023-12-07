package org.example.ExcelExporter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.Models.TransportVehicle;
import org.example.Models.TransportVehicleMission;
import org.example.dao.DriverEmployeeDAO;
import org.example.dao.TransportVehicleDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.example.dao.TransportVehicleDAO.getTransportVehicleById;

public class ExcelReader {
    public static List<TransportVehicleMission> readTransportationData(String filePath) {
        List<TransportVehicleMission> missionData = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheet("Transportation Data");

            if (sheet != null) {
                Iterator<Row> iterator = sheet.iterator();

                // Skip header row
                if (iterator.hasNext()) {
                    iterator.next();
                }

                while (iterator.hasNext()) {
                    Row currentRow = iterator.next();
                    TransportVehicleMission rowData = createTransportVehicleMissionFromRow(currentRow);
                    missionData.add(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return missionData;
    }

    private static TransportVehicleMission createTransportVehicleMissionFromRow(Row row) {
        TransportVehicleMission mission = new TransportVehicleMission();

        mission.setId((long) row.getCell(0).getNumericCellValue());
        mission.setDateOfDeparture(LocalDate.parse(row.getCell(1).getStringCellValue()));
        mission.setDateOfArrival(LocalDate.parse(row.getCell(2).getStringCellValue()));
        mission.setDepartureStartingPoint(row.getCell(3).getStringCellValue());
        mission.setDepartureArrivalPoint(row.getCell(4).getStringCellValue());
        mission.setPriceForMission(row.getCell(5).getNumericCellValue());
        Cell vehicleIdCell = row.getCell(6);
        if (vehicleIdCell != null && vehicleIdCell.getCellType() == CellType.NUMERIC) {
            long vehicleId = (long) vehicleIdCell.getNumericCellValue();
            TransportVehicle vehicle = TransportVehicleDAO.getTransportVehicleById(vehicleId);
            mission.setVehicle(vehicle);
        }
        // Assuming getVehicleType() and getContent() return enums
        //mission.getVehicle().setVehicleType(VehicleType.valueOf(row.getCell(6).getStringCellValue()));
        mission.setContent(ContentType.valueOf(row.getCell(8).getStringCellValue()));

        // Handling the case where weight might be null in the Excel sheet
        if (row.getCell(9) != null) {
            Cell weightCell = row.getCell(9);
            if (weightCell.getCellType() == CellType.NUMERIC) {
                mission.setWeight(weightCell.getNumericCellValue());
            } else if (weightCell.getCellType() == CellType.STRING) {
                try {
                    // Attempt to parse the string value as a numeric value
                    double weightValue = Double.parseDouble(weightCell.getStringCellValue());
                    mission.setWeight(weightValue);
                } catch (NumberFormatException e) {
                    // Handle the case when the string cannot be parsed as a numeric value
                    // You may choose to set a default value or throw an exception based on your requirement
                    mission.setWeight(null); // or any default value you prefer
                }
            } else {
                // Handle the case when the cell is of an unexpected type
                mission.setWeight(null); // or any default value you prefer
            }
        } else {
            mission.setWeight(null); // or any default value you prefer
        }
        if (row.getCell(10) != null && row.getCell(10).getCellType() == CellType.NUMERIC){
            long driverId = (long) row.getCell(10).getNumericCellValue();
            mission.setDriver(DriverEmployeeDAO.getDriverById(driverId));
        }

        // Add more fields as needed

        return mission;
    }


}
