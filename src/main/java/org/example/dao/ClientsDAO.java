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
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Update the client
            session.update(client);

            // Update the name in the associated TransportCompany
            TransportCompany company = client.getCompany();
            if (company != null) {
                company.getClients().forEach(c -> {
                    if (c.getId() == client.getId()) {
                        c.setName(client.getName());
                    }
                });
                session.update(company);
            }

            transaction.commit();
        }
    }

    public static void updateClientNameById(long clientId, String newName) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the client
            Client clientToUpdate = session.get(Client.class, clientId);

            if (clientToUpdate != null) {
                // Update the name
                clientToUpdate.setName(newName);

                // Update the name in the associated TransportCompany
                TransportCompany company = clientToUpdate.getCompany();
                if (company != null) {
                    company.getClients().forEach(c -> {
                        if (c.getId() == clientToUpdate.getId()) {
                            c.setName(newName);
                        }
                    });
                    session.update(company);
                }

                session.update(clientToUpdate);
            } else {
                System.out.println("Client with ID " + clientId + " not found.");
            }

            transaction.commit();
        }
    }
    public static void deleteClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Remove the client from the associated TransportCompany
            TransportCompany company = client.getCompany();
            if (company != null) {
                company.getClients().remove(client);
            }

            // Now delete the client
            session.delete(client);

            transaction.commit();
        }
    }

    public static void deleteClientById(long clientId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the client
            Client clientToDelete = session.get(Client.class, clientId);

            if (clientToDelete != null) {
                // Remove the client from the associated TransportCompany
                TransportCompany company = clientToDelete.getCompany();
                if (company != null) {
                    company.getClients().remove(clientToDelete);
                }

                // Now delete the client
                session.delete(clientToDelete);
            } else {
                System.out.println("Client with ID " + clientId + " not found.");
            }

            transaction.commit();
        }
    }
}
