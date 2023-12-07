import org.example.Models.*;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.DriverEmployeeDAO;
import org.example.dao.TransportCompanyDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverEmployeeDaoTest {


    @Test
    public void testAddQualification(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var driver = DriverEmployeeDAO.getDriverById(1);
        Qualification qualification = new Qualification("B", driver);
        DriverEmployeeDAO.addQualification(qualification, driver);

        assertEquals(1, session.get(DriverEmployee.class, 1L).getQualifications().size());

    }
    @Test
    public void testUpdateDriverEmployee(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var driver = DriverEmployeeDAO.getDriverById(1);
        driver.setName("IskrenDriverUpdated2");
        DriverEmployeeDAO.updateDriverEmployee(driver);

        assertEquals("IskrenDriverUpdated2", session.get(DriverEmployee.class, 1L).getName());
    }
    @Test
    public void testDeleteDriverEmployeeById(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        DriverEmployeeDAO.deleteDriverEmployeeById(3);

        assertNull(session.get(DriverEmployee.class, 3L));
    }

    @Test
    public void testDeleteDriverEmployee(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        DriverEmployeeDAO.deleteDriverEmployee(DriverEmployeeDAO.getDriverById(2));

        assertNull(session.get(DriverEmployee.class, 2L));



    }
    @Test
    public void testGetDriverMissions(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var missions =DriverEmployeeDAO.getDriverMissions(1);
        assertEquals(1, missions.size());
        assertEquals("IskrenDriver", missions.get(0).getDriver().getName());
        assertEquals(1500, missions.get(0).getPriceForMission());
//other tests

    }


    @Test
    public void testGetDriverEmployeesDTO(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var driverEmployees = DriverEmployeeDAO.getDriverEmployeesDTO(1);
        assertEquals(1, driverEmployees.size());
        assertEquals("IskrenDriver", driverEmployees.get(0).getName());
        assertEquals(1, driverEmployees.get(0).getId());

    }

    @Test
    public void testGetDriverEmployees(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var driverEmployees = DriverEmployeeDAO.getDriverEmployees();

        assertEquals(1, driverEmployees.size());
        assertEquals("IskrenDriver", driverEmployees.get(0).getName());
        assertEquals(1000, driverEmployees.get(0).getSalary());


    }
    @Test
    public void testGetDriverById(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        long id = 1;
        DriverEmployee driverEmployee = DriverEmployeeDAO.getDriverById(id);

        assertEquals("IskrenDriver", driverEmployee.getName());
        assertEquals(1000, driverEmployee.getSalary());

    }
    @Test
    public void testCreateDriverEmployee(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        // createDriverEmployee(DriverEmployee driverEmployee)
        TransportCompany transportCompany = TransportCompanyDAO.getCompanyById(1);
        DriverEmployee driverEmployee = new DriverEmployee("IskrenDriver2", transportCompany, 10020);

        assertDoesNotThrow(() ->   DriverEmployeeDAO.createDriverEmployee(driverEmployee));

        DriverEmployee savedDriver = session.get(DriverEmployee.class, driverEmployee.getId());
        assertEquals("IskrenDriver2", savedDriver.getName());
        assertEquals(10020, savedDriver.getSalary());

    }
}
