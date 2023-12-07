import org.example.configuration.SessionFactoryUtil;
import org.example.dao.TransportVehicleMissionDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportVehicleMissionDaoTest {
    @Test
    public void testGetSumOfPricesForMissions() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tvm = TransportVehicleMissionDAO.getSumOfPricesForMissions();

        assertEquals(4700, tvm);

    }
    @Test
    public void testGetAllTransportVehicleMissions() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tvm = TransportVehicleMissionDAO.getAllTransportVehicleMissions();

        assertEquals(3, tvm.size());
        assertEquals(1500, tvm.get(0).getPriceForMission());
    }
}
