package org.example;

import org.example.Models.Client;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.Models.TransportCompany;
import org.example.Models.TransportContent;
import org.example.Models.TransportVehicle;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.TransportCompanyDAO;
import org.example.dao.TransportVehicleDAO;

public class Main {
    public static void main(String[] args) {
       var session = SessionFactoryUtil.getSessionFactory().openSession();
      //  TransportCompany company1 = new TransportCompany("IskrenOOD");

        //TransportVehicleDAO.updateTransportVehicleById();



    //  var tvs = TransportVehicleDAO.getTransportVehicles();
    //   for (TransportVehicle var: tvs) {
    //       System.out.println(var);
    //   }

    }

}
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