package org.example.dao;

import org.example.CustomExceptions.NoCompanyException;
import org.example.CustomExceptions.NoTransportVehicleFoundException;
import org.example.DTO.DriverEmployeeDTO;
import org.example.DTO.TransportCompanyDTO;
import org.example.Models.*;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static List<TransportCompanyDTO> getCompaniesDTO(long id) {
        List<TransportCompanyDTO> transportCompanyDTOS;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportCompanyDTOS = session.createQuery(
                            "Select new org.example.DTO.TransportCompanyDTO(tc.id, tc.name) " +
                                    "From TransportCompany tc " + // Add a space here
                                    "where tc.id = :id", TransportCompanyDTO.class)
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
        }
        return transportCompanyDTOS;
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


    //TODO:Check if correct after change of the relationship between tc and client
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
//TODO:Check if correct after change of the relationship between tc and client
public static void isThereObligationsThatAreNotPaid(Client client, TransportCompany tc) {
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

//Test these
    public static void sortByQualification(TransportCompany tc, DriverEmployee employee){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            if (employee == null) {
                throw new IllegalArgumentException("DriverEmployee cannot be null.");
            }


            // Get the qualifications and print them to the console
            Set<Qualification> qualifications = employee.getQualifications();
            if (qualifications != null) {
                for (Qualification qualification : qualifications) {
                    System.out.println("Qualification Name: " + qualification.getName());
                }
            } else {
                System.out.println("DriverEmployee has no qualifications.");
            }



            transaction.commit();
        }
    }
    public static void sortBySalary(TransportCompany tc){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            if (tc == null) {
                throw new IllegalArgumentException("TransportCompany cannot be null.");
            }
            tc.getDriverEmployees()
                    .stream()
                    .sorted(Comparator.comparing(DriverEmployee::getSalary).reversed())
                    .forEach(driverEmployee ->
                            System.out.println("DriverEmployee ID: " + driverEmployee.getId() +
                                    ", Salary: " + driverEmployee.getSalary()));


            transaction.commit();
        }
    }

//test this
    public static void addMission(TransportVehicleMission tvm,  TransportVehicle tv){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (tvm == null || tv == null)throw new IllegalArgumentException("parameters cannot be null.");
            tvm.setVehicle(tv);
            tv.getMissions().add(tvm);
            //tc.getVehicles().add(tv);

            session.saveOrUpdate(tvm);
            session.saveOrUpdate(tv);
           // session.saveOrUpdate(tc);


            transaction.commit();
        }
    }
    public static void sortMissionsByDistance(TransportCompany tc) throws NoTransportVehicleFoundException {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            LocalDate currentDate = LocalDate.now();
            var missions = tc.getVehicles()
                    .stream()
                    .flatMap(transportVehicle -> transportVehicle.getMissions().stream())
                    .sorted(Comparator.comparing(
                                    mission -> Math.abs(ChronoUnit.DAYS.between(mission.getDateOfArrival(), mission.getDateOfDeparture()))
                            )
                    )
                    .collect(Collectors.toList()); // Collect the sorted missions into a list

// Reverse the list
            Collections.reverse(missions);

// Print the reversed list to the console
            missions.forEach(mission ->
                    System.out.println("Mission ID: " + mission.getId() +
                            ", Date of Departure: " + mission.getDateOfDeparture()));
//                    .forEach(mission ->
//                            System.out.println("Mission ID: " + mission.getId() +
//                                    ", Date of Departure: " + mission.getDateOfDeparture()));

            transaction.commit();
        }
    }
    public static double calculateTotalEarningsForPeriodOfTime(Set<DriverEmployee> drivers, LocalDate startDate, LocalDate endDate) {
        double totalEarnings = 0.0;

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            for (DriverEmployee driver : drivers) {
                double driverEarnings = calculateEarningsForPeriodOfTime(driver, startDate, endDate);
                totalEarnings += driverEarnings;
            }

            transaction.commit();
        }

        return totalEarnings;
    }

    public static double calculateEarningsForPeriodOfTime(DriverEmployee driver, LocalDate startDate, LocalDate endDate) {
        double totalEarnings = 0.0;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
        List<TransportVehicleMission> driverMissions = driver.getMissions();

        for (TransportVehicleMission mission : driverMissions) {
            LocalDate missionDate = mission.getDateOfDeparture(); // Assuming the mission date is relevant

            // Check if the mission date is within the specified range
            if (missionDate.isAfter(startDate.minusDays(1)) && missionDate.isBefore(endDate.plusDays(1))) {
                totalEarnings += mission.getPriceForMission();
            }
        }
        transaction.commit();
        }

        return totalEarnings;
    }

//    public static double calculateSalar(DriverEmployee driver, LocalDate startDate, LocalDate endDate)
//    {
//
//    }

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