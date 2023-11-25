package org.example.dao;

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

    public static void updateTransportVehicle(
            long transportVehicleId,
            VehicleType newType,
            TransportCompanyDTO newCompany,
            TransportContentDTO newTransportContent
    ) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the existing entities
            TransportVehicle oldTransportVehicleToUpdate = session.get(TransportVehicle.class, transportVehicleId);



            // Update the VehicleType
            if (oldTransportVehicleToUpdate != null) {
                if (newType != null && !newType.equals(oldTransportVehicleToUpdate.getVehicleType())) {
                    oldTransportVehicleToUpdate.setVehicleType(newType);
                }

                // Update the TransportContent
                if (newTransportContent != null) {
                    TransportContent existingTransportContent = oldTransportVehicleToUpdate.getTransportContent();
                    if (existingTransportContent != null && !existingTransportContent.getContent().equals(newTransportContent.getContent())) {
                        // If the content type is different, update the transport content
                        existingTransportContent.setContent(newTransportContent.getContent());
                        if (existingTransportContent.getContent() != ContentType.PEOPLE){
                            existingTransportContent.setWeight(newTransportContent.getWeight());
                        }
                        session.update(existingTransportContent);
                    } else if (existingTransportContent == null) {
                        // If there was no existing transport content, create a new one
                        TransportContent transportContent = new TransportContent();
                        transportContent.setContent(newTransportContent.getContent());
                        if (transportContent.getContent() != ContentType.PEOPLE){
                            transportContent.setWeight(newTransportContent.getWeight());
                        }
                        oldTransportVehicleToUpdate.setTransportContent(transportContent);
                    }
                }




                // Update the TransportCompany
                if (newCompany != null) {
                    long companyIdFromDTO = newCompany.getId();

                    // Check if the company with the given ID exists in the database
                    TransportCompany newCompanyToUpdate = session.get(TransportCompany.class, companyIdFromDTO);
                    if (newCompanyToUpdate != null) {
                        // Remove the vehicle from the old company
                        TransportCompany oldCompany = oldTransportVehicleToUpdate.getCompany();
                        if (oldCompany != null) {
                            oldCompany.getVehicles().remove(oldTransportVehicleToUpdate);
                            session.update(oldCompany);
                        }

                        // Add the vehicle to the new company
                        newCompanyToUpdate.getVehicles().add(oldTransportVehicleToUpdate);
                        oldTransportVehicleToUpdate.setCompany(newCompanyToUpdate);
                        session.update(newCompanyToUpdate);
                    } else {
                        System.out.println("TransportCompany with ID " + companyIdFromDTO + " not found.");
                    }
                }

                // Save or update the TransportVehicle
                session.update(oldTransportVehicleToUpdate);
            } else {
                System.out.println("TransportVehicle with ID " + transportVehicleId + " not found.");
            }

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

    public static void deleteTransportVehicleById(long transportVehicleId) {
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
                System.out.println("TransportVehicle with ID " + transportVehicleId + " not found.");
            }

            transaction.commit();
        }
    }
}


