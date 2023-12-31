package org.example;

import org.example.DTO.ClientDTO;
import org.example.DTO.DriverEmployeeDTO;
import org.example.DTO.TransportCompanyDTO;
import org.example.DTO.TransportVehicleDTO;
import org.example.ExcelExporter.ExcelWriter;
import org.example.Models.*;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.ExcelExporter.ExcelReader.readTransportationData;



//TODO: check when a driveremployee changes his company. He shouldn't be present in the old company that he worked for.
// Maybe you can do that by using cascade = CascadeType.ALL

//TODO:think about the case in which the client wants to stop being a client to a certain company


public class Main {
    public static void main(String[] args) {
       var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tc = TransportCompanyDAO.getCompanyById(1);



     var driver =   DriverEmployeeDAO.getDriverById(1);
        var tv = TransportVehicleDAO.getTransportVehicleById(1);
//ClientsDAO.addTransportCompany(tc, ClientsDAO.getClientById(6));



       // TransportVehicle transportVehicle = new TransportVehicle(VehicleType.BUS, tc, new ArrayList<TransportVehicleMission>());

       // TransportVehicleDAO.createTransportVehicle(transportVehicle, session);
        //TransportVehicleMission tvm,  TransportVehicle tv
  }

}




//        TransportVehicleMission tvm = new TransportVehicleMission(
//                "New York",
//                "Las Vegas",
//                LocalDate.of(2023, 12, 1),  // Date of Departure: December 1, 2023
//                LocalDate.of(2023, 12, 10), // Date of Arrival: December 10, 2023
//                1500.0,                      // Price for Mission
//                tv,     // Assuming you have a default constructor for TransportVehicle
//                ContentType.STOCK,       // Assuming you have a default constructor for TransportContent
//                1000.0,
//                driver
//
//        );
//
//       TransportCompanyDAO.addMission(tvm, tv);

//        var client = ClientsDAO.getClientById(6);
//        var obligation = new Obligation(client, 300.0);
//        TransportCompanyDAO.addObligation(obligation, client);



//  Client client = new Client("sssasdsas", 22222);
//   var client = ClientsDAO.getClientById(1);
// ClientsDAO.addTransportCompany(tc,client);
//ClientsDAO.createClient(client);


//       var tc = TransportCompanyDAO.getCompanyById(2);
//
//        var missions = TransportVehicleMissionDAO.getAllTransportVehicleMissions();
//        for(TransportVehicleMission tvm : missions){
//            System.out.println(tvm);
//        }
//        var sumPriceMissions = TransportVehicleMissionDAO.getSumOfPricesForMissions();
//        System.out.println(sumPriceMissions);
//
//
//        DriverEmployeeDAO.getDriverEmployees().forEach(System.out::println);
//        DriverEmployeeDAO.getDriverMissions(1).forEach(System.out::println);
//         var tv = TransportVehicleDAO.getTransportVehicleById(10);
// var driver =DriverEmployeeDAO.getDriverById(1);
//       //var calculateEarnings = TransportCompanyDAO.calculateEarningsForPeriodOfTime(driver, LocalDate.of(2023, 11, 12), LocalDate.of(2023 ,12, 17));
//       // System.out.println(calculateEarnings);
//        var drivers = tc.getDriverEmployees();
//   var calculateEarningsForAllDriverEmployees =  TransportCompanyDAO.calculateTotalEarningsForPeriodOfTime(drivers, LocalDate.of(2023, 11, 12), LocalDate.of(2023 ,12, 17));
//        System.out.println(calculateEarningsForAllDriverEmployees);





//       // var tv = TransportVehicleDAO.getTransportVehicleById(10);
//
//        String relativeFilePath = "src/main/java/org/example/Excel files/output.xlsx";
//        Path absolutePath = Paths.get(relativeFilePath).toAbsolutePath().normalize();
//        List<TransportVehicleMission> missionData = readTransportationData(absolutePath.toString());
//
//        for (TransportVehicleMission mission : missionData) {
//            System.out.println(mission);
//        }
//       var tv = TransportVehicleDAO.getTransportVehicleById(10);
//        //TransportContent transportContent = new TransportContent(ContentType.STOCK, null);
//
//
//
//        List<TransportVehicleMission> missionData = tv.getMissions();
//
//
//     String relativeFilePath = "src/main/java/org/example/Excel files/output.xlsx";
//       Path absolutePath = Paths.get(relativeFilePath).toAbsolutePath().normalize();
//
//       File file = absolutePath.toFile();
//       File parentDir = file.getParentFile();
//       if (!parentDir.exists() && !parentDir.mkdirs()) {
//           System.err.println("Failed to create directories: " + parentDir);
//           return;
//       }
//
//       ExcelWriter.writeTransportationData(missionData, absolutePath.toString());
//
//       System.out.println("Excel file generated successfully at: " + absolutePath);
//




// var list = TransportVehicleDAO.getTransportVehiclesDTO(1);
//   for (TransportVehicleDTO tv: list) {
//        System.out.println(tv);
//   }
// TransportCompanyDAO.sortMissionsByDistance(tc);

//  TransportContent transportContent1 = new TransportContent(ContentType.PEOPLE, null);
//  TransportContent transportContent2 = new TransportContent(ContentType.PEOPLE, null);
//  var driver = DriverEmployeeDAO.getDriverById(3);
//
//
//
//  TransportVehicle tv1 = new TransportVehicle(VehicleType.BUS, tc, new ArrayList<TransportVehicleMission>());
//  TransportVehicle tv2 = new TransportVehicle(VehicleType.BUS, tc, new ArrayList<TransportVehicleMission>());


//     TransportVehicleMission mission = new TransportVehicleMission(
//             "New York",
//             "Las Vegas",
//             LocalDate.of(2023, 12, 1),  // Date of Departure: December 1, 2023
//             LocalDate.of(2023, 12, 10), // Date of Arrival: December 10, 2023
//             1500.0,                      // Price for Mission
//             tv1,     // Assuming you have a default constructor for TransportVehicle
//             transportContent1       // Assuming you have a default constructor for TransportContent
//     );
// TransportVehicleMission mission2 = new TransportVehicleMission(
//         "New York",
//         "Las Vegas 2",
//         LocalDate.of(2023, 11, 3),  // Date of Departure: December 1, 2023
//         LocalDate.of(2023, 12, 14), // Date of Arrival: December 10, 2023
//         1500.0,                      // Price for Mission
//         tv2,     // Assuming you have a default constructor for TransportVehicle
//         transportContent2      // Assuming you have a default constructor for TransportContent
// );

//  TransportCompanyDAO.addMission(mission2, tv2);


// var qualification = new Qualification("D1", driver);
// DriverEmployeeDAO.addQualification(qualification, driver);


//  var employee = new DriverEmployee("IskrenDriver2", tc);
// var qualification = new Qualification("C1", employee);
// DriverEmployeeDAO.createDriverEmployee(employee);
// DriverEmployeeDAO.addQualification(qualification, employee);

// TransportCompanyDAO.SortCompaniesByIncome();


//   Obligation obligation = new Obligation(client, 300.0);
//   TransportCompanyDAO.addObligation(obligation, client);

//  ClientsDAO.PayObligation(18, client);
//ClientsDAO.IsThereObligationsThatAreNotPaid(client);

// TransportCompanyDAO.IsThereObligationsThatAreNotPaid(client);

//        this.finances = finances;
//        this.obligations = new ArrayList<>();
//        this.Name = name;
//        this.company = company;


// Client client = new Client( "Ivancho", tc,1000);
//
//   Obligation obligation = new Obligation(getclient, 300.0);
//   TransportCompanyDAO.addObligation(obligation, getclient);
//   ClientsDAO.PayAllObligations(getclient);
//TransportCompanyDAO.IsThereObligationsThatAreNotPaid(client);

//        ClientsDAO.PayAllObligations(client);
//        Obligation obligation = new Obligation(client, 300.0);
//        TransportCompanyDAO.addObligation(obligation, client);
//        TransportCompany tc = new TransportCompany("Qskr2");
//        TransportCompanyDAO.createCompany(tc);
//        TransportCompanyDAO.SortCompaniesByName();
//var client = ClientsDAO.getClientById(1);
//ClientsDAO.PayObligation(1, client);
//TransportCompanyDAO.IsThereObligationsThatAreNotPaid(client);
//System.out.println( ClientsDAO.getClients());
 //       System.out.println("Hello world!");
//  ClientsDAO.deleteClientById(2);
//TransportCompany company1 = new TransportCompany("IskrenOOD");
// Client client = new Client("Ivancho" ,company1);
//ClientsDAO.createClient(client);
// ClientsDAO.updateClientNameById(3 , "Petar");


 //  TransportContent transportContent = new TransportContent();
 //      transportContent.setContent(ContentType.PEOPLE);
 //              TransportVehicle tv = new TransportVehicle();
 //              tv.setVehicleType(VehicleType.BUS); // Replace YOUR_ENUM_VALUE with the desired VehicleType
 //              tv.setCompany(tc); // Replace yourTransportCompanyInstance with the actual TransportCompany instance
 //              tv.setTransportContent(transportContent);
 //              TransportVehicleDAO.createTransportVehicle(tv, session);


//  TransportCompany company1 = new TransportCompany("IskrenOOD");

//TransportVehicleDAO.updateTransportVehicleById();




//  var tvs = TransportVehicleDAO.getTransportVehicles();
//   for (TransportVehicle var: tvs) {
//       System.out.println(var);
//   }




//    TransportCompany tc = TransportCompanyDAO.getCompanyById(1);
//  DriverEmployee de = new DriverEmployee("Iskren", tc);
//  DriverEmployeeDAO.createDriverEmployee(de);
// DriverEmployee driver =   DriverEmployeeDAO.getDriverById(1);

//  DriverEmployeeDAO.updateDriverEmployee(driver);



//       VehicleType vt = VehicleType.BUS;
//        TransportContent transportContent = new TransportContent();
//        TransportCompany tc = TransportCompanyDAO.getCompanyById(1);
//        TransportVehicle tv = new TransportVehicle(vt,tc, transportContent, null);
//
//        TransportVehicleDAO.createTransportVehicle(tv, session);



// Client client = ClientsDAO.getClientById();
//        Obligation obligation = new Obligation(client, 200.0, tc);
//
//        TransportCompanyDAO.addObligation(obligation, client);


//   this.client = client;
//    this.amount = amount;
//        TransportCompany tc = TransportCompanyDAO.getCompanyById(1);
//        Client client = new Client("Ivan", tc, 5000);
//        ClientsDAO.createClient(client);
