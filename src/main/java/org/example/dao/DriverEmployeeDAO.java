package org.example.dao;

import org.example.CustomExceptions.NoCompanyException;
import org.example.CustomExceptions.NoDriverEmployeeFoundException;
import org.example.CustomExceptions.NoTransportVehicleFoundException;
import org.example.DTO.ClientDTO;
import org.example.DTO.DriverEmployeeDTO;
import org.example.Models.DriverEmployee;
import org.example.Models.Qualification;
import org.example.Models.TransportCompany;
import org.example.Models.TransportVehicleMission;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DriverEmployeeDAO {
    /**
     * Creates a new driver employee in the system.
     *
     * @param driverEmployee The driver employee to be created.
     * @throws IllegalArgumentException If the provided driver employee is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void createDriverEmployee(DriverEmployee driverEmployee) {
        if (driverEmployee == null) {
            throw new IllegalArgumentException("Driver employee cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(driverEmployee);
            transaction.commit();
        }
    }
    /**
     * Retrieves a driver employee from the system by its ID.
     *
     * @param id The ID of the driver employee to be retrieved.
     * @return The driver employee with the specified ID, or null if not found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     *  @throws IllegalArgumentException If the provided ID is 0 or negative.
     * @since 1.0
     */
    public static DriverEmployee getDriverById(long id) {
        if ( id <= 0) {
            throw new IllegalArgumentException("Driver employee ID cannot be 0 or negative.");
        }
        DriverEmployee driverEmployee;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            driverEmployee = session.get(DriverEmployee.class, id);
            transaction.commit();
        }
        return driverEmployee;
    }
    /**
     * Retrieves a list of all driver employees from the system.
     *
     * @return A list of driver employees in the system.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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
    /**
     * Retrieves a list of all driver DTO employees from the system.
     *
     * @return A list of driver DTO employees in the system.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     *   @throws IllegalArgumentException If the provided ID is 0 or negative.
     * @since 1.0
     */
    public static List<DriverEmployeeDTO> getDriverEmployeesDTO(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Transport company ID cannot be negative or 0.");
        }
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

    /**
     * Retrieves a list of transport vehicle missions associated with a specific driver employee.
     *
     * @param id The ID of the driver employee for whom missions are retrieved.
     * @return A list of transport vehicle missions associated with the specified driver employee.
     *         Returns null if no driver employee with the given ID is found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     * @throws IllegalArgumentException If the provided ID is 0 or negative.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static List<TransportVehicleMission> getDriverMissions(long id) {
        if ( id <= 0) {
            throw new IllegalArgumentException("Driver employee ID cannot be 0 or negative.");
        }
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            DriverEmployee driver = session.get(DriverEmployee.class, id);
            if (driver != null) {
                return driver.getMissions();
            }
            transaction.commit();
        }
        return null;
    }

    /**
     * Deletes a driver employee from the system.
     *
     * @param driverEmployee The driver employee to be deleted.
     * @throws NoDriverEmployeeFoundException If the provided driver employee is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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
    /**
     * Deletes a driver employee from the system by its ID.
     *
     * @param driverEmployeeId The ID of the driver employee to be deleted.
     * @throws NoDriverEmployeeFoundException If no driver employee with the specified ID is found in the system.
     * @throws IllegalArgumentException If the provided ID is 0 or negative.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void deleteDriverEmployeeById(long driverEmployeeId)throws NoDriverEmployeeFoundException {
        if (driverEmployeeId <= 0) {
            throw new IllegalArgumentException("Driver employee ID cannot be 0 or negative.");
        }
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
    /**
     * Updates a driver employee's information in the system.
     * If the driver employee does not exist in the database, it will be saved.
     *
     * @param driverEmployee The driver employee to be updated or saved in the system.
     * @throws IllegalArgumentException If the provided driver employee is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void updateDriverEmployee(DriverEmployee driverEmployee) {
        if (driverEmployee == null) {
            throw new IllegalArgumentException("Driver employee cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(driverEmployee);
            transaction.commit();
        }
    }
//Test
    /**
     * Adds a qualification to a driver employee.
     *
     * @param qualification The qualification to be added.
     * @param driverEmployee The driver employee to whom the qualification is added.
     * @throws IllegalArgumentException If either qualification or driverEmployee is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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