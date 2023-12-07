package org.example.dao;

import org.example.CustomExceptions.NoTransportVehicleFoundException;
import org.example.DTO.TransportCompanyDTO;

import org.example.DTO.TransportVehicleDTO;
import org.example.Models.Client;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.Models.TransportCompany;
//import org.example.Models.TransportContent;
import org.example.Models.TransportVehicle;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TransportVehicleDAO {
    /**
     * Creates a new transport vehicle and associates it with a specified transport company within the given Hibernate session.
     *
     * @param transportVehicle The transport vehicle to be created and saved.
     * @param session          The Hibernate session in which the operation is performed.
     * @throws IllegalArgumentException If the transportVehicle or session are null.
     * @throws org.hibernate.HibernateException       If there is an issue with the Hibernate operations.
     *                                  Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void createTransportVehicle(TransportVehicle transportVehicle ,Session session) {
        if (transportVehicle == null) {
            throw new IllegalArgumentException("The transport vehicle shouldn't be null");
        }
        if (session == null) {
            throw new IllegalArgumentException("The session shouldn't be null");
        }
        //try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Save or update the referenced TransportCompany within the same session
            TransportCompany company = transportVehicle.getCompany();
            session.saveOrUpdate(company);

            // Now you can save the TransportVehicle
            session.save(transportVehicle);

            transaction.commit();
        //}
    }
    /**
     * Retrieves a transport vehicle by its unique identifier (ID) within the specified Hibernate session.
     *
     * @param id The unique identifier of the transport vehicle to be retrieved.
     * @return The transport vehicle with the specified ID, or null if not found.
     * @throws IllegalArgumentException If the provided ID is less than or equal to 0.
     * @throws org.hibernate.HibernateException     If there is an issue with the Hibernate operations.
     *                                  Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static TransportVehicle getTransportVehicleById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id should be greater than 0");
        }
        TransportVehicle transportVehicle;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportVehicle = session.get(TransportVehicle.class, id);
            transaction.commit();
        }
        return transportVehicle;
    }
   // public static TransportVehicle getTransportVehicleByIdWithMissionsAndContent(long id) {
   //     TransportVehicle transportVehicle = null;
   //     try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
   //         Transaction transaction = session.beginTransaction();
//
   //         // Fetch the TransportVehicle along with missions and content
   //         transportVehicle = session.createQuery(
   //                         "SELECT tv FROM TransportVehicle tv LEFT JOIN FETCH tv.missions m LEFT JOIN FETCH m.content WHERE tv.id = :id",
   //                         TransportVehicle.class
   //                 )
   //                 .setParameter("id", id)
   //                 .uniqueResult();
//
   //         transaction.commit();
   //     } catch (Exception e) {
   //         // Handle or log the exception as needed
   //         e.printStackTrace();
   //     }
   //     return transportVehicle;
   // }
    /**
     * Retrieves a list of all transport vehicles within the specified Hibernate session.
     *
     * @return A list of transport vehicles, or an empty list if none are found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static List<TransportVehicle> getTransportVehicles() {
        List<TransportVehicle> transportVehicles;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportVehicles = session.createQuery("Select tv From TransportVehicle tv", TransportVehicle.class)
                    .getResultList();
            transaction.commit();
        }
        return transportVehicles;
    }
    /**
     * Retrieves a list of transport vehicle data transfer objects (DTOs) associated with a specific transport company.
     *
     * @param id The ID of the transport company for which DTOs are retrieved.
     * @return A list of transport vehicle DTOs, or an empty list if none are found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     * @throws IllegalArgumentException If the provided ID is less than or equal to 0.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static List<TransportVehicleDTO> getTransportVehiclesDTO(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id should be greater than 0");
        }
        List<TransportVehicleDTO> transportVehicles;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportVehicles = session.createQuery(
                            "Select new org.example.DTO.TransportVehicleDTO(tv.id, tv.vehicleType) " +
                                    "From TransportVehicle tv " + // Fix entity name here
                                    "where tv.company.id = :id", TransportVehicleDTO.class)
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
        }
        return transportVehicles;
    }



    /**
     * Updates the information of a transport vehicle in the database.
     *
     * @param vehicle The transport vehicle entity with updated information.
     * @throws IllegalArgumentException If the transportVehicle is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void updateVehicle(TransportVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("The transport vehicle shouldn't be null");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(vehicle);
            transaction.commit();
        }
    }



    /**
     * Deletes a transport vehicle from the database and removes it from the associated transport company.
     *
     * @param transportVehicle The transport vehicle entity to be deleted.
     * @param session          The Hibernate session to use for database operations.
     * @throws IllegalArgumentException If the transportVehicle or session are null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                            Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void deleteTransportVehicle(TransportVehicle transportVehicle, Session session) {
        if (transportVehicle == null) {
            throw new IllegalArgumentException("The transport vehicle shouldn't be null");
        }
        if (session == null) {
            throw new IllegalArgumentException("The session shouldn't be null");
        }
     //   try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Remove the transport vehicle from the associated TransportCompany
            TransportCompany company = transportVehicle.getCompany();
            if (company != null) {
                company.getVehicles().remove(transportVehicle);
            }

            // Now delete the transport vehicle
            session.delete(transportVehicle);

            transaction.commit();
        }
 //   }
    /**
     * Deletes a transport vehicle from the database by its ID and removes it from the associated transport company.
     *
     * @param transportVehicleId The ID of the transport vehicle to be deleted.
     * @throws NoTransportVehicleFoundException If no transport vehicle with the given ID is found.
     * @throws IllegalArgumentException If the provided ID is less than or equal to 0.
     * @throws org.hibernate.HibernateException               If there is an issue with the Hibernate operations.
     *                                          Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void deleteTransportVehicleById(long transportVehicleId) throws NoTransportVehicleFoundException{
        if (transportVehicleId <= 0) {
            throw new IllegalArgumentException("The id should be greater than 0");
        }
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the transport vehicle
            TransportVehicle transportVehicleToDelete = session.get(TransportVehicle.class, transportVehicleId);

            if (transportVehicleToDelete != null) {
                // Remove the transport vehicle from the associated TransportCompany
                TransportCompany company = transportVehicleToDelete.getCompany();
                if (company != null) {
                    company.getVehicles().remove(transportVehicleToDelete);
                }

                // Now delete the transport vehicle
                session.delete(transportVehicleToDelete);
            } else {
                throw new NoTransportVehicleFoundException(transportVehicleId);
            }

            transaction.commit();
        }
    }



}





//Old code for updating stuff. I realised that it is not the correct way.

//    public static void updateTransportVehicle(
//            long transportVehicleId,
//            VehicleType newType,
//            String newCompanyId,
//            String newCompanyName,
//            ContentType newtransportContentType,
//            Double weight
//    ) throws NoTransportVehicleFoundException{
//        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            // Retrieve the existing entities
//            TransportVehicle oldTransportVehicleToUpdate = session.get(TransportVehicle.class, transportVehicleId);
//
//
//
//            // Update the VehicleType
//            if (oldTransportVehicleToUpdate != null) {
//                if (newType != null && !newType.equals(oldTransportVehicleToUpdate.getVehicleType())) {
//                    oldTransportVehicleToUpdate.setVehicleType(newType);
//                }
//
//                // Update the TransportContent
//                if (newtransportContentType != null || weight != null) {
//                    TransportContent existingTransportContent = oldTransportVehicleToUpdate.getTransportContent();
//                    if (existingTransportContent != null && !existingTransportContent.getContent().equals(newtransportContentType)) {
//                        // If the content type is different, update the transport content
//                        existingTransportContent.setContent(newtransportContentType);
//                        if (existingTransportContent.getContent() != ContentType.PEOPLE && weight != null && weight != 0){
//                            existingTransportContent.setWeight(weight);
//                        }
//                        session.update(existingTransportContent);
//                    } else if (existingTransportContent == null) {
//                        // If there was no existing transport content, create a new one
//                        TransportContent transportContent = new TransportContent();
//                        transportContent.setContent(newtransportContentType);
//                        if (transportContent.getContent() != ContentType.PEOPLE && weight != null && weight != 0){
//                            transportContent.setWeight(weight);
//                        }
//                        oldTransportVehicleToUpdate.setTransportContent(transportContent);
//                    }
//                }
//
//
//
//                TransportCompany newCompany = session.get(TransportCompany.class, newCompanyId);
//                // Update the TransportCompany
//                if (newCompany != null) {
//                    TransportCompany oldCompany = oldTransportVehicleToUpdate.getCompany();
//                    if (!oldCompany.getName().equals(newCompanyName) && oldCompany != null) {
//                        // oldCompany.setName(newCompanyName);
//                        oldCompany.getVehicles().remove(oldTransportVehicleToUpdate);
//                        newCompany.getVehicles().add(oldTransportVehicleToUpdate);
//                        oldTransportVehicleToUpdate.setCompany(newCompany);
//
//                        session.update(oldCompany);
//                        session.update(oldTransportVehicleToUpdate);
//                        session.update(newCompany);
//                    } else {
//                        throw new NoTransportVehicleFoundException(transportVehicleId);
//                    }
//                }
//            } else {
//                throw new NoTransportVehicleFoundException(transportVehicleId);
//            }
//
//            transaction.commit();
//        }
//    }