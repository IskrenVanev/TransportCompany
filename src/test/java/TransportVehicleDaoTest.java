import org.example.Models.Enums.VehicleType;
import org.example.Models.TransportCompany;
import org.example.Models.TransportVehicle;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.TransportCompanyDAO;
import org.example.dao.TransportVehicleDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportVehicleDaoTest {//VehicleType vehicleType, TransportCompany company,  List<TransportVehicleMission> missions
    @Test
    public void testDeleteTransportVehicleById() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        TransportVehicleDAO.deleteTransportVehicleById(3);
        assertEquals(1, TransportVehicleDAO.getTransportVehicles().size());
    }
    @Test
    public void testDeleteTransportVehicle() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        TransportVehicleDAO.deleteTransportVehicle(TransportVehicleDAO.getTransportVehicleById(2), session);
        assertEquals(1, TransportVehicleDAO.getTransportVehicles().size());
    }
    @Test
    public void testUpdateVehicle() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
       var vehicle = TransportVehicleDAO.getTransportVehicleById(1);
       vehicle.setVehicleType(VehicleType.TRUCK);
        TransportVehicleDAO.updateVehicle(vehicle);

        assertEquals(VehicleType.TRUCK, session.get(TransportVehicle.class, 1L).getVehicleType());
    }

    @Test
    public void testGetTransportVehiclesDTO() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var vehicles = TransportVehicleDAO.getTransportVehiclesDTO(1);

assertEquals(2, vehicles.size());
    }
    @Test
    public void testGetTransportVehicles() {

        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var vehicles = TransportVehicleDAO.getTransportVehicles();

        assertEquals(2, vehicles.size());
    }
    @Test
    public void testGetTransportVehicleById(){

        var session = SessionFactoryUtil.getSessionFactory().openSession();
        TransportVehicleDAO.getTransportVehicleById(1);
        assertEquals(1, session.get(TransportVehicle.class, 1L).getId());
        assertEquals(VehicleType.BUS, session.get(TransportVehicle.class, 1L).getVehicleType());

    }
    @Test
    public void testCreateTransportVehicle(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var vehicle = new TransportVehicle(VehicleType.BUS, TransportCompanyDAO.getCompanyById(1), null);
        TransportVehicleDAO.createTransportVehicle(vehicle, session);

        //uncomment these if U need to test the method

       // assertEquals(2, session.get(TransportVehicle.class, 2L).getId());
       //  assertEquals(VehicleType.BUS, session.get(TransportVehicle.class, 2L).getVehicleType());


    }
}
