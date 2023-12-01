package org.example.dao;

import org.example.CustomExceptions.NoClientException;
import org.example.CustomExceptions.NotEnoughFundsException;
import org.example.Models.Client;
import org.example.Models.Obligation;
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

    public static void deleteClientById(long clientId) throws NoClientException {
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
                throw new NoClientException(clientId);
            }

            transaction.commit();
        }
    }

    public static void updateClient(Client client) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(client);
            transaction.commit();
        }
    }


//TODO:Test if this works




    public static void PayObligation(int obligationId , Client client) throws NotEnoughFundsException{
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Obligation getObligation = session.get(Obligation.class, obligationId);
            if (getObligation != null){
               // client.getObligation(obligationId);
                double amountToPay = getObligation.getAmount();
                if (client.getFinances() >= amountToPay){
                    client.setFinances(client.getFinances()-amountToPay);
                    transaction.commit();
                }
                else {
                    throw new NotEnoughFundsException(client.getId());
                }
            }


        }
    }
    public static void PayAllObligations(Client client)throws NotEnoughFundsException{
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            List<Obligation> obligations = client.getObligations();
            if (obligations.size() == 0){
                System.out.println("All obligations are paid");
                return;
            }
            double totalAmount = 0.0;
            for (Obligation obligation : obligations) {
                totalAmount += obligation.getAmount();
            }
            if (client.getFinances() >= totalAmount){
                client.setFinances(client.getFinances()-totalAmount);
                session.saveOrUpdate(client);
                // Remove all obligations associated with the client
                obligations.forEach(obligation -> obligation.setClient(null));
                obligations.clear();
//                obligations.removeAll(obligations);
//                session.saveOrUpdate(obligations);
                transaction.commit();
            }
            else {
                throw new NotEnoughFundsException(client.getId());
            }
        }
    }
    //Both the client and the company can che ck if the client has more obligations
    public static void IsThereObligationsThatAreNotPaid(Client client){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
          List<Obligation> NotPaidObligations =   client.getObligations();
          if (NotPaidObligations != null){
              System.out.println("There are more obligations that the client has to pay for");
          }
          else {
              System.out.println("All obligations are paid");
          }

        }


    }




}


//Old code for updating stuff. I realised that it is not the correct way.


//    public static void updateClient(Client client) {
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            // Update the client
//            session.update(client);
//
//            // Update the name in the associated TransportCompany
//            TransportCompany company = client.getCompany();
//            if (company != null) {
//                company.getClients().forEach(c -> {
//                    if (c.getId() == client.getId()) {
//                        c.setName(client.getName());
//                    }
//                });
//                session.update(company);
//            }
//
//            transaction.commit();
//        }
//    }
//
//    public static void updateClientNameById(long clientId, String newName) throws NoClientException {
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            // Retrieve the client
//            Client clientToUpdate = session.get(Client.class, clientId);
//
//            if (clientToUpdate != null) {
//                // Update the name
//                clientToUpdate.setName(newName);
//
//                // Update the name in the associated TransportCompany
//                TransportCompany company = clientToUpdate.getCompany();
//                if (company != null) {
//                    company.getClients().forEach(c -> {
//                        if (c.getId() == clientToUpdate.getId()) {
//                            c.setName(newName);
//                        }
//                    });
//                    session.update(company);
//                }
//
//                session.update(clientToUpdate);
//            } else {
//                throw new NoClientException(clientId);
//            }
//
//            transaction.commit();
//        }
//    }