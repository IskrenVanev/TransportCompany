package org.example.dao;

import org.example.CustomExceptions.NoClientException;
import org.example.CustomExceptions.NotEnoughFundsException;
import org.example.DTO.ClientDTO;
import org.example.Models.Client;
import org.example.Models.Obligation;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientsDAO {
    private final SessionFactory sessionFactory;
    public ClientsDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    public static void addTransportCompany(TransportCompany company, Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Add the company to the client's transportCompanies list
            client.getTransportCompanies().add(company);

            // Save or update the client, which will cascade to the junction table
            session.saveOrUpdate(client);

            // Commit the transaction
            transaction.commit();
        }
    }
    //TODO:Check if correct after change of the relationship between tc and client
    public static void createClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save or update the referenced TransportCompany
            var company = client.getTransportCompanies();
            for (TransportCompany tc : company) {
                session.saveOrUpdate(tc);
            }

            // Now you can save the Client
            session.saveOrUpdate(client);

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
//TODO:Check if correct after change of the relationship between tc and client
    public static List<ClientDTO> getClientsDTO(long companyId) {
        List<ClientDTO> clients;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            clients = session.createQuery(
                            "SELECT NEW org.example.DTO.ClientDTO(c.id, c.Name, c.finances) " +
                                    "FROM Client c " +
                                    "JOIN c.transportCompanies tc " +
                                    "WHERE tc.id = :companyId", ClientDTO.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
            transaction.commit();
        }
        return clients;
    }
    //TODO:Check if correct after change of the relationship between tc and client
    public static void deleteClient(Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Get the list of associated companies
            List<TransportCompany> companies = client.getTransportCompanies();

            // Remove the client from each associated company
            for (TransportCompany company : companies) {
                company.getClients().remove(client);
            }

            // Clear the list of associated companies from the client
            client.getTransportCompanies().clear();

            // Now delete the client
            session.delete(client);

            transaction.commit();
        }
    }
    //TODO:Check if correct after change of the relationship between tc and client
    public static void deleteClientById(long clientId) throws NoClientException {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the client
            Client clientToDelete = session.get(Client.class, clientId);

            if (clientToDelete != null) {
                // Get the associated companies
                List<TransportCompany> companies = clientToDelete.getTransportCompanies();

                // Remove the client from each associated company
                for (TransportCompany company : companies) {
                    company.getClients().remove(clientToDelete);
                }

                // Clear the list of associated companies from the client (optional, depending on your requirements)
                clientToDelete.getTransportCompanies().clear();

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



    //TODO:Check if correct after change of the relationship between tc and client
    public static void payObligation(int obligationId, Client client, TransportCompany tc) throws NotEnoughFundsException {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check if the provided transport company matches the client's associated company
            if (!client.getTransportCompanies().contains(tc)) {
                // The provided company does not match the client's associated company
                throw new IllegalArgumentException("The provided company does not match the client's associated company.");
            }

            // Load the obligation
            Obligation obligation = session.get(Obligation.class, obligationId);

            if (obligation != null && !obligation.isDeleted()) {
                double amountToPay = obligation.getAmount();

                if (client.getFinances() >= amountToPay) {
                    // Update the company's income
                    tc.setIncome(tc.getIncome() + (long) amountToPay);
                    session.merge(tc);

                    // Update the client's finances
                    client.setFinances(client.getFinances() - amountToPay);
                    session.merge(client);

                    // Mark the obligation as deleted
                    obligation.setDeleted(true);
                    session.saveOrUpdate(obligation);

                    transaction.commit();
                } else {
                    throw new NotEnoughFundsException(client.getId());
                }
            }
        }
    }
    //TODO:Check if correct after change of the relationship between tc and client
    public static void PayAllObligations(Client client, TransportCompany tc)throws NotEnoughFundsException{
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //List<Obligation> obligations = client.getObligations();
            //TransportCompany tc = client.getCompany();
            List<Obligation> obligations = tc.getClients().stream()
                    .filter(c -> c.equals(client))
                    .findFirst()
                    .map(Client::getObligations)
                    .orElse(Collections.emptyList());


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
                // Set isDeleted to true only for obligations with isDeleted = false
                obligations.stream()
                        .filter(obligation -> !obligation.isDeleted())
                        .forEach(obligation -> obligation.setDeleted(true));

                for (Obligation obligation : obligations){
                    session.saveOrUpdate(obligation);
                }
               // obligations.clear();

                session.saveOrUpdate(client);
                // Remove all obligations associated with the client


                tc.setIncome(tc.getIncome() + (long) totalAmount);//continue this logic
                session.saveOrUpdate(tc);

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
          if (NotPaidObligations != null && !NotPaidObligations.stream().allMatch(Obligation::isDeleted)){
              System.out.println("There are more obligations that the client has to pay for");
          }
          else {
              System.out.println("All obligations are paid");
          }

        }


    }
    //TODO:Check if correct after change of the relationship between tc and client
    public static void isThereObligationsThatAreNotPaidForASpecificCompany(Client client, TransportCompany tc) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            List<Obligation> notPaidObligations = client.getObligations().stream()
                    .filter(obligation -> tc.getClients().contains(client) && !obligation.isDeleted())
                    .collect(Collectors.toList());

            if (!notPaidObligations.isEmpty()) {
                System.out.println("There are obligations that the client has not paid for in the specified company.");
                // You might want to print or handle the list of not paid obligations here
            } else {
                System.out.println("All obligations are paid in the specified company.");
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