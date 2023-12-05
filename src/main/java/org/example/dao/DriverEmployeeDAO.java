package org.example.dao;

import org.example.CustomExceptions.NoCompanyException;
import org.example.CustomExceptions.NoDriverEmployeeFoundException;
import org.example.CustomExceptions.NoTransportVehicleFoundException;
import org.example.DTO.ClientDTO;
import org.example.DTO.DriverEmployeeDTO;
import org.example.Models.DriverEmployee;
import org.example.Models.Qualification;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DriverEmployeeDAO {
    public static void createDriverEmployee(DriverEmployee driverEmployee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(driverEmployee);
            transaction.commit();
        }
    }

    public static DriverEmployee getDriverById(long id) {
        DriverEmployee driverEmployee;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driverEmployee = session.get(DriverEmployee.class, id);
            transaction.commit();
        }
        return driverEmployee;
    }

    public static List<DriverEmployee> getDriverEmployees() {
        List<DriverEmployee> driverEmployees;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driverEmployees = session.createQuery("Select c From DriverEmployee c", DriverEmployee.class)
                    .getResultList();
            transaction.commit();
        }
        return driverEmployees;
    }
    public static List<DriverEmployeeDTO> getDriverEmployeesDTO(long id) {
        List<DriverEmployeeDTO> driverEmployees;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driverEmployees = session.createQuery(
                            "Select new org.example.DTO.DriverEmployeeDTO(de.id, de.name) " +
                                    "From DriverEmployee de join de.company tc " +
                                    "where tc.id = :id", DriverEmployeeDTO.class)
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
        }
        return driverEmployees;
    }

    public static void deleteDriverEmployee(DriverEmployee driverEmployee) throws  NoDriverEmployeeFoundException{
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (driverEmployee != null) {
                session.delete(driverEmployee);
            }
            else {
                throw new NoDriverEmployeeFoundException(driverEmployee.getId());
            }
            transaction.commit();
        }
    }
    public static void deleteDriverEmployeeById(long driverEmployeeId)throws NoDriverEmployeeFoundException {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            DriverEmployee DriverEmployeeToDelete = session.get(DriverEmployee.class, driverEmployeeId);
            if (DriverEmployeeToDelete != null) {
                session.delete(DriverEmployeeToDelete);
            } else {
                throw new NoDriverEmployeeFoundException(driverEmployeeId);
            }
            transaction.commit();
        }
    }


    //Update methods can update the drivers qualifications and the company he works for
    public static void updateDriverEmployee(DriverEmployee driverEmployee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(driverEmployee);
            transaction.commit();
        }
    }
//Test
    public static void addQualification(Qualification qualification, DriverEmployee driverEmployee) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (qualification == null) {
                throw new IllegalArgumentException("qualification cannot be null.");
            }
            if (driverEmployee == null) {
                throw new IllegalArgumentException("driverEmployee cannot be null.");
            }

            driverEmployee.getQualifications().add(qualification);
            session.saveOrUpdate(qualification);
            session.saveOrUpdate(driverEmployee);

            transaction.commit();
        }
    }



    }







//Old code for updating stuff. I realised that it is not the correct way.



//    public static void updateDriverEmployeeCompanyById(long driverEmployeeId, String newCompanyName) throws NoCompanyException {
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            // Retrieve the DriverEmployee
//            DriverEmployee driverEmployeeToUpdate = session.get(DriverEmployee.class, driverEmployeeId);
//
//            if (driverEmployeeToUpdate != null) {
//                // Retrieve the new company by its name
//                Query<TransportCompany> query = session.createQuery("FROM TransportCompany WHERE name = :name", TransportCompany.class);
//                query.setParameter("name", newCompanyName);
//                TransportCompany newCompany = query.uniqueResult();
//
//                if (newCompany != null) {
//                    // Remove the driver from the old company
//                    TransportCompany oldCompany = driverEmployeeToUpdate.getCompany();
//                    if (oldCompany != null) {
//                        oldCompany.getDriverEmployees().remove(driverEmployeeToUpdate);
//                        session.update(oldCompany);
//                    }
//
//                    // Add the driver to the new company
//                    newCompany.getDriverEmployees().add(driverEmployeeToUpdate);
//                    driverEmployeeToUpdate.setCompany(newCompany);
//
//                    // Update the DriverEmployee
//                    session.update(driverEmployeeToUpdate);
//                } else {
//                    throw new NoCompanyException(newCompany.getId());
//                }
//            } else {
//                throw new NoDriverEmployeeFoundException(driverEmployeeId);
//            }
//
//            transaction.commit();
//        }
//    }