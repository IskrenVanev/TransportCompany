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
import java.util.*;
import java.util.stream.Collectors;

public class TransportCompanyDAO {
    /**
     * Creates a new transport company in the system.
     *
     * @param company The transport company to be created.
     * @throws IllegalArgumentException If the provided transport company is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void createCompany(TransportCompany company) {
        if (company == null) {
            throw new IllegalArgumentException("Transport company cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    /**
     * Retrieves a transport company from the system by its ID.
     *
     * @param id The ID of the transport company to be retrieved.
     * @return The transport company with the specified ID.
     *         Returns null if no transport company with the given ID is found.
     * @throws IllegalArgumentException If the provided ID is negative or null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static TransportCompany getCompanyById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID cannot be negative or null.");
        }
        TransportCompany company;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(TransportCompany.class, id);
            transaction.commit();
        }
        return company;
    }
    /**
     * Retrieves a list of all transport companies in the system.
     *
     * @return A list of transport companies.
     *         Returns an empty list if no companies are found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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
    /**
     * Retrieves a list of transport company DTOs containing specific information.
     *
     * @return A list of transport company DTOs containing ID and name.
     *         Returns an empty list if no companies are found.
     * @throws  org.hibernate.HibernateException  If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static List<TransportCompanyDTO> getCompaniesDTO() {
        List<TransportCompanyDTO> transportCompanyDTOS;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transportCompanyDTOS = session.createQuery(
                            "Select new org.example.DTO.TransportCompanyDTO(tc.id, tc.name) " +
                                    "From TransportCompany tc " , TransportCompanyDTO.class)
                    .getResultList();
            transaction.commit();
        }
        return transportCompanyDTOS;
    }

    /**
     * Updates an existing transport company in the system.
     *
     * @param company The transport company to be updated.
     * @throws IllegalArgumentException If the provided transport company is null.
     * @throws org.hibernate.HibernateException  If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */

    public static void updateCompany(TransportCompany company) {
        if (company == null) {
            throw new IllegalArgumentException("Transport company cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }
    /**
     * Deletes an existing transport company from the system.
     *
     * @param company The transport company to be deleted.
     * @throws IllegalArgumentException If the provided transport company is null.
     * @throws org.hibernate.HibernateException  If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void deleteCompany(TransportCompany company) {
        if (company == null) {
            throw new IllegalArgumentException("Transport company cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }
    /**
     * Deletes an existing transport company from the system based on its ID.
     *
     * @param companyId The ID of the transport company to be deleted.
     * @throws IllegalArgumentException If the provided company ID is negative or null.
     * @throws NoCompanyException If no transport company with the given ID is found.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void deleteCompanyById(long companyId) throws NoCompanyException{
        if (companyId <= 0) {
            throw new IllegalArgumentException("Company ID cannot be negative or null.");
        }
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


    /**
     * Adds an obligation to a client in the system.
     *
     * @param obligation The obligation to be added.
     * @param client The client to whom the obligation is added.
     * @throws IllegalArgumentException If either the obligation or the client is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void addObligation(Obligation obligation , Client client) {
        if (obligation == null) {
            throw new IllegalArgumentException("Obligation cannot be null.");
        }
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
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
    /**
     * Checks if there are obligations that a client has not paid for in a specified transport company.
     *
     * @param client The client for whom to check unpaid obligations.
     * @param tc The transport company in which to check for unpaid obligations.
     * @throws IllegalArgumentException If either the client or the transport company is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */

public static void isThereObligationsThatAreNotPaid(Client client, TransportCompany tc) {
    if (client == null) {
        throw new IllegalArgumentException("Client cannot be null.");
    }
    if (tc == null) {
        throw new IllegalArgumentException("Transport company cannot be null.");
    }
    try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
        Transaction transaction = session.beginTransaction();

//        List<Obligation> notPaidObligations = client.getObligations().stream()
//                .filter(obligation -> tc.getClients().contains(client) && !obligation.isDeleted())
//                .collect(Collectors.toList());
        List<Obligation> obligations = client.getObligations();
        List<Obligation> notPaidObligations = new ArrayList<>();
        for(Obligation obligation : obligations){
            if(!obligation.isDeleted() && containsTransportCompany(obligation.getClient(), tc)){
                notPaidObligations.add(obligation);
            }
        }


        if (!notPaidObligations.isEmpty()) {
            System.out.println("There are obligations that the client has not paid for in the specified company.");
            // You might want to print or handle the list of not paid obligations here
        } else {
            System.out.println("All obligations are paid in the specified company.");
        }
    }
}
//helper method for the isThereObligationsThatAreNotPaid method
    private static boolean containsTransportCompany(Client client, TransportCompany tc) {
        // Check if the client's transport companies contain the specified transport company
        return client.getTransportCompanies().stream().anyMatch(company -> company.equals(tc));
    }




    /**
     * Sorts and retrieves a list of transport companies ordered by their names.
     *
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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
    /**
     * Sorts and retrieves a list of transport companies ordered by their income in descending order.
     *
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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
    /**
     * Sorts and prints the driver employees of a transport company based on the number of qualifications they have.
     * Prints each driver's name and qualifications.
     *
     * @param tc The transport company for which to sort and print driver employees.
     * @throws IllegalArgumentException If there are no drivers in the company or if the company is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void sortByQualification(TransportCompany tc){
        if (tc == null) {
            throw new IllegalArgumentException("TransportCompany cannot be null.");
        }
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            //var drivers = tc.getDriverEmployees();

            Set<DriverEmployee> driverSet = tc.getDriverEmployees();
            List<DriverEmployee> drivers = new ArrayList<>(driverSet);

            if (drivers == null) {
                throw new IllegalArgumentException("No drivers in the company.");
            }
            Collections.sort(drivers, Comparator.comparingInt(driver -> {
                if (driver.getQualifications() != null) {
                    return driver.getQualifications().size();
                } else {
                    return 0; // Drivers with no qualifications are considered to have 0 qualifications
                }
            }));
                System.out.println("Sorted Drivers by Qualification:");
                for (DriverEmployee driver : drivers) {
                    if (driver.getQualifications() != null) {
                        System.out.print(driver.getName() + " - Qualifications: ");

                        // Iterate over the qualifications and print each one
                        for (Qualification qualification : driver.getQualifications()) {
                            System.out.print(qualification.getName() + ", ");
                        }

                        System.out.println(); // Move to the next line for the next driver
                    }
                    else  System.out.println("DriverEmployee "+ driver.getName() + " has no qualifications.");
                }

            transaction.commit();
        }
    }
    /**
     * Sorts and prints the driver employees of a transport company based on their salaries in descending order.
     * Prints each driver's ID and salary.
     *
     * @param tc The transport company for which to sort and print driver employees.
     * @throws IllegalArgumentException If the transport company is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
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

    /**
     * Adds a transport vehicle mission to a transport vehicle.
     *
     * @param tvm The transport vehicle mission to be added.
     * @param tv The transport vehicle to which the mission will be added.
     * @throws IllegalArgumentException If the transport vehicle mission or the transport vehicle is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void addMission(TransportVehicleMission tvm,  TransportVehicle tv){
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (tvm == null) {
                throw new IllegalArgumentException("Transport vehicle mission cannot be null.");
            }

            if (tv == null) {
                throw new IllegalArgumentException("Transport vehicle cannot be null.");
            }
            tvm.setVehicle(tv);
            tv.getMissions().add(tvm);
            //tc.getVehicles().add(tv);

            session.saveOrUpdate(tvm);
            session.saveOrUpdate(tv);
           // session.saveOrUpdate(tc);


            transaction.commit();
        }
    }
    /**
     * Sorts and prints the transport vehicle missions of a transport company based on the absolute difference
     * in days between the date of arrival and the date of departure. The missions are sorted in descending order.
     *
     * @param tc The transport company for which to sort and print transport vehicle missions.
     * @throws NoTransportVehicleFoundException If no transport vehicles are found for the specified transport company.
     * @throws IllegalArgumentException If the transport company is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static void sortMissionsByDistance(TransportCompany tc) throws NoTransportVehicleFoundException {
        if (tc == null) {
            throw new IllegalArgumentException("TransportCompany cannot be null.");
        }
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
    /**
     * Calculates the total earnings for a set of driver employees within a specified period of time.
     *
     * @param drivers    The set of driver employees for which to calculate total earnings.
     * @param startDate  The start date of the period for which earnings are calculated.
     * @param endDate    The end date of the period for which earnings are calculated.
     * @return The total earnings for the specified set of driver employees within the given period.
     * @throws IllegalArgumentException If the set of drivers, the start date or the end date is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static double calculateTotalEarningsForPeriodOfTime(Set<DriverEmployee> drivers, LocalDate startDate, LocalDate endDate) {
        if (drivers == null) {
            throw new IllegalArgumentException("Set of drivers cannot be null.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
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
    /**
     * Calculates the earnings for a specific driver employee within a specified period of time based on their missions.
     *
     * @param driver     The driver employee for whom to calculate earnings.
     * @param startDate  The start date of the period for which earnings are calculated.
     * @param endDate    The end date of the period for which earnings are calculated.
     * @return The total earnings for the specified driver employee within the given period.
     * @throws IllegalArgumentException If the driver, the start date or the end date is null.
     * @throws org.hibernate.HibernateException If there is an issue with the Hibernate operations.
     *                           Check the nested exceptions for specific details.
     * @since 1.0
     */
    public static double calculateEarningsForPeriodOfTime(DriverEmployee driver, LocalDate startDate, LocalDate endDate) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null.");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
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