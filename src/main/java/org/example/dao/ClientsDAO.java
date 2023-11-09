package org.example.dao;

import org.example.Models.Client;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientsDAO {
    public static void createClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save or update the referenced TransportCompany
            TransportCompany company = client.getCompany();
            session.saveOrUpdate(company);

            // Now you can save the Client
            session.save(client);

            transaction.commit();
        }
    }


    public static Client getClientById(long id) {
        Client client;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            client = session.get(Client.class, id);
            transaction.commit();
        }
        return client;
    }

    public static List<Client> getClients() {
        List<Client> clients;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session.createQuery("Select c From Client c", Client.class)
                    .getResultList();
            transaction.commit();
        }
        return clients;
    }
    public static void updateClient(Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        }
    }
    public static void updateClientNameById(long clientId, String newName) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client clientToUpdate = session.get(Client.class, clientId);
            if (clientToUpdate != null) {
                clientToUpdate.setName(newName); // Set the new name
                session.update(clientToUpdate);   // Use update instead of merge

            } else {
                System.out.println("Company with ID " + clientId + " not found.");
            }
            transaction.commit();
        }
    }
    public static void deleteClient(Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
        }
    }
    public static void deleteClientById(long clientId) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Client clientToDelete = session.get(Client.class, clientId);
            if (clientToDelete != null) {
                session.delete(clientToDelete);
            } else {
                System.out.println("Company with ID " + clientId + " not found.");
            }
            transaction.commit();
        }
    }
}
