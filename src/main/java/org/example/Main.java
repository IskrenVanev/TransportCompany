package org.example;

import org.example.Models.Client;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.TransportCompanyDAO;

public class Main {
    public static void main(String[] args) {
        SessionFactoryUtil.getSessionFactory().openSession();

        System.out.println( ClientsDAO.getClients());
        System.out.println("Hello world!");
    }

}
//  ClientsDAO.deleteClientById(2);
//TransportCompany company1 = new TransportCompany("IskrenOOD");
// Client client = new Client("Ivancho" ,company1);
//ClientsDAO.createClient(client);
// ClientsDAO.updateClientNameById(3 , "Petar");