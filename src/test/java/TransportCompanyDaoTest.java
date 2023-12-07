import org.example.Models.*;
import org.example.Models.Enums.ContentType;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.DriverEmployeeDAO;
import org.example.dao.TransportCompanyDAO;
import org.example.dao.TransportVehicleDAO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportCompanyDaoTest {//(Obligation obligation , Client client)

    @Test
    public void testcalculateEarningsForPeriodOfTime() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
       var driver =  DriverEmployeeDAO.getDriverById(1);
       var result = TransportCompanyDAO.calculateEarningsForPeriodOfTime(driver, LocalDate.of(2021, 1, 1), LocalDate.of(2024, 1, 1));
        assertEquals(4700,result);
    }
    @Test
    public void testcalculateTotalEarningsForPeriodOfTime() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();

        var drivers = DriverEmployeeDAO.getDriverEmployees();
        Set<DriverEmployee> driverSet = convertListToSet(drivers);
        LocalDate localDate1 = LocalDate.of(2021, 1, 1);
        LocalDate localDate2 = LocalDate.of(2024, 1, 1);



        assertEquals(4700, TransportCompanyDAO.calculateTotalEarningsForPeriodOfTime(driverSet, localDate1, localDate2));
    }
    public static Set<DriverEmployee> convertListToSet(List<DriverEmployee> drivers) {//helper method for testcalculateTotalEarningsForPeriodOfTime
        return new HashSet<>(drivers);
    }


    @Test
    public void testSortMissionsByDistance() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tc = TransportCompanyDAO.getCompanyById(1);
        TransportCompanyDAO.sortMissionsByDistance(tc);
        //We can see the output in the console

    }
    @Test
    public void testAddMission() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tv = TransportVehicleDAO.getTransportVehicleById(1);
        var driver =   DriverEmployeeDAO.getDriverById(1);
        TransportVehicleMission tvm = new TransportVehicleMission(
                "New York",
                "Las Vegas",
                LocalDate.of(2023, 12, 1),  // Date of Departure: December 1, 2023
                LocalDate.of(2023, 12, 10), // Date of Arrival: December 10, 2023
                1600.0,                      // Price for Mission
                tv,     // Assuming you have a default constructor for TransportVehicle
                ContentType.STOCK,       // Assuming you have a default constructor for TransportContent
                1000.0,
                driver
        );
        TransportCompanyDAO.addMission(tvm, tv);

        assertEquals(1600, session.get(TransportVehicleMission.class, 3L).getPriceForMission());

    }

    @Test
    public void testSortBySalary() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tc = TransportCompanyDAO.getCompanyById(1);

        TransportCompanyDAO.sortBySalary(tc);
        //We can see the output in the console
    }

    @Test
    public void testSortByQualification() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tc = TransportCompanyDAO.getCompanyById(1);

        TransportCompanyDAO.sortByQualification(tc);
        //We can see the output in the console
    }
    @Test
    public void testSortCompaniesByIncome() { //sorts them from the richest to the poorest company
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        TransportCompanyDAO.SortCompaniesByIncome();
        //We can see the output in the console
    }
    @Test
    public void testSortCompaniesByName() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        TransportCompanyDAO.SortCompaniesByName();
        //We can see the output in the console

    }
    @Test
    public void testIsThereObligationsThatAreNotPaid() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var client = ClientsDAO.getClientById(6);
        var company = TransportCompanyDAO.getCompanyById(1);

       TransportCompanyDAO.isThereObligationsThatAreNotPaid(client, company);
       //We see the result printed in the console
    }
    @Test
    public void testAddObligation() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var client = ClientsDAO.getClientById(6);
        Obligation obligation = new Obligation(client,1000.);
        TransportCompanyDAO.addObligation(obligation, client);

        assertEquals(3, session.get(Client.class, 6L).getObligations().size());

    }
    @Test
    public void testDeleteCompanyById() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var company = TransportCompanyDAO.getCompanyById(3);
        TransportCompanyDAO.deleteCompanyById(3);

        var companies = TransportCompanyDAO.getCompaniesDTO();
        assertEquals(1, companies.size());
    }
    @Test
    public void testDeleteCompany() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();

        var company = TransportCompanyDAO.getCompanyById(2);
        TransportCompanyDAO.deleteCompany(company);

        var companies = TransportCompanyDAO.getCompaniesDTO();
        assertEquals(1, companies.size());
    }
    @Test
    public void testUpdateCompany() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var company = TransportCompanyDAO.getCompanyById(1);
        company.setName("IskrenOODUpdated");
        TransportCompanyDAO.updateCompany(company);
        assertEquals("IskrenOODUpdated", session.get(TransportCompany.class, 1L).getName());
    }
    @Test
    public void testGetCompaniesDTO() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var companies = TransportCompanyDAO.getCompaniesDTO();

        assertEquals(2, companies.size());
        assertEquals("IskrenOOD", companies.get(0).getName());
        assertEquals(1, companies.get(0).getId());
    }
    @Test
    public void testGetCompanies(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var companies = TransportCompanyDAO.getCompanies();

        assertEquals(2, companies.size());
        assertEquals("IskrenOOD", companies.get(0).getName());
    }

    @Test
    public void testGetCompanyById(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var company = TransportCompanyDAO.getCompanyById(1);

        assertEquals(1, company.getId());
        assertEquals("IskrenOOD", company.getName());
    }
    @Test
    public void testCreateCompany(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var company = new TransportCompany("Company2");
        TransportCompanyDAO.createCompany(company);


        //uncomment these if U need to test the method

        //assertEquals(2, session.get(TransportCompany.class, 2L).getId());
       // assertEquals("Company2", session.get(TransportCompany.class, 2L).getName());


    }
}
