package org.example;

import org.example.Models.*;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.DriverEmployeeDAO;
import org.example.dao.TransportCompanyDAO;
import org.example.dao.TransportVehicleDAO;

import java.time.LocalDate;
import java.util.ArrayList;


//TODO: Think about the TransportCompany's properities (the collections) and their DAOS

//TODO: check when a driveremployee changes his company. He shouldn't be present in the old company that he worked for.
// Maybe you can do that by using cascade = CascadeType.ALL

//TODO:think about the case in which the client wants to change his company but still has obligations to the previous company
//TODO:what if the client is a client of a few companies think about that PayAllObligations

//TODO: use DTO's instead of real models from the database in DAO


public class Main {
    public static void main(String[] args) {
       var session = SessionFactoryUtil.getSessionFactory().openSession();

       var tc = TransportCompanyDAO.getCompanyById(1);

        TransportContent transportContent1 = new TransportContent();
        TransportContent transportContent2 = new TransportContent();
        var driver = DriverEmployeeDAO.getDriverById(3);
        TransportVehicle tv1 = new TransportVehicle();
        TransportVehicle tv2 = new TransportVehicle();


        TransportVehicleMission mission = new TransportVehicleMission(
                "New York",
                "Las Vegas",
                LocalDate.of(2023, 12, 1),  // Date of Departure: December 1, 2023
                LocalDate.of(2023, 12, 10), // Date of Arrival: December 10, 2023
                1500.0,                      // Price for Mission
                tv1,     // Assuming you have a default constructor for TransportVehicle
                transportContent1       // Assuming you have a default constructor for TransportContent
        );
        TransportVehicleMission mission2 = new TransportVehicleMission(
                "New York",
                "Las Vegas 2",
                LocalDate.of(2023, 12, 1),  // Date of Departure: December 1, 2023
                LocalDate.of(2023, 12, 14), // Date of Arrival: December 10, 2023
                1500.0,                      // Price for Mission
                tv2,     // Assuming you have a default constructor for TransportVehicle
                transportContent2      // Assuming you have a default constructor for TransportContent
        );

        TransportCompanyDAO.addMission(mission, tc, tv1);

    }

}

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
