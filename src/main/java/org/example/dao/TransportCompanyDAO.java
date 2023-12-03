package org.example.dao;

import org.example.CustomExceptions.NoCompanyException;
import org.example.Models.Client;
import org.example.Models.Obligation;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TransportCompanyDAO {
    public static void createCompany(TransportCompany company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }


    public static TransportCompany getCompanyById(long id) {
        TransportCompany company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(TransportCompany.class, id);
            transaction.commit();
        }
        return company;
    }

    public static List<TransportCompany> getCompanies() {
        List<TransportCompany> companies;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("Select c From TransportCompany c", TransportCompany.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }
    public static void updateCompany(TransportCompany company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }

    public static void deleteCompany(TransportCompany company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }
    public static void deleteCompanyById(long companyId) throws NoCompanyException{
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            TransportCompany companyToDelete = session.get(TransportCompany.class, companyId);
            if (companyToDelete != null) {
                session.delete(companyToDelete);
            } else {
                throw new NoCompanyException(companyId);
            }
            transaction.commit();
        }
    }
    public static void addObligation(Obligation obligation , Client client) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


         //   company.addObligation(obligation);
            client.addObligation(obligation);
            obligation.setClient(client);
          //  obligation.setCompany(company);

           // session.saveOrUpdate(company);
            session.saveOrUpdate(obligation);
            session.saveOrUpdate(client);

            transaction.commit();
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
    public static void SortCompaniesByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to retrieve TransportCompany entities and order them by name
            String hql = "FROM TransportCompany ORDER BY name";
            Query<TransportCompany> query = session.createQuery(hql, TransportCompany.class);
            List<TransportCompany> companies = query.getResultList();

            // Iterate through the sorted list
            for (TransportCompany company : companies) {
                System.out.println(company.getName());
                // You can perform any additional operations with the sorted companies here
            }

            transaction.commit();
        }
    }
    public static void SortCompaniesByIncome() { //DESCENDING
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL query to retrieve TransportCompany entities and order them by name
            String hql = "FROM TransportCompany ORDER BY income DESC";
            Query<TransportCompany> query = session.createQuery(hql, TransportCompany.class);
            List<TransportCompany> companies = query.getResultList();

            // Iterate through the sorted list
            for (TransportCompany company : companies) {
                System.out.println(company.getName());
                // You can perform any additional operations with the sorted companies here
            }

            transaction.commit();
        }
    }
}




//Old code for updating stuff. I realised that it is not the correct way.


//    public static void updateCompanyNameById(long companyId, String newName) throws NoCompanyException {
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            TransportCompany companyToUpdate = session.get(TransportCompany.class, companyId);
//            if (companyToUpdate != null) {
//                companyToUpdate.setName(newName); // Set the new name
//                session.update(companyToUpdate);   // Use update instead of merge
//
//            } else {
//                throw new NoCompanyException(companyId);
//            }
//            transaction.commit();
//        }
//    }