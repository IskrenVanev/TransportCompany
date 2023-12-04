package org.example.dao;

import org.example.CustomExceptions.NoTransportVehicleFoundException;
import org.example.DTO.TransportCompanyDTO;
import org.example.DTO.TransportContentDTO;
import org.example.Models.Client;
import org.example.Models.Enums.ContentType;
import org.example.Models.Enums.VehicleType;
import org.example.Models.TransportCompany;
import org.example.Models.TransportContent;
import org.example.Models.TransportVehicle;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TransportVehicleDAO {
    public static void createTransportVehicle(TransportVehicle transportVehicle ,Session session) {
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
    public static TransportVehicle getTransportVehicleById(long id) {
        TransportVehicle transportVehicle;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportVehicle = session.get(TransportVehicle.class, id);
            transaction.commit();
        }
        return transportVehicle;
    }

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

    public static void updateVehicle(TransportVehicle vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(vehicle);
            transaction.commit();
        }
    }


    public static void deleteTransportVehicle(TransportVehicle transportVehicle, Session session) {
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

    public static void deleteTransportVehicleById(long transportVehicleId) throws NoTransportVehicleFoundException{
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