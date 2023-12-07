import org.example.Models.TransportVehicleMission;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.TransportVehicleMissionDAO;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.example.ExcelExporter.ExcelReader.readTransportationData;
import static org.example.ExcelExporter.ExcelWriter.writeTransportationData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelReaderWriterTest {//List<TransportVehicleMission> missionData, String filePath
    @Test
    public void testWrite() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        String relativeFilePath = "src/main/java/org/example/Excel files/output.xlsx";
        Path absolutePath = Paths.get(relativeFilePath).toAbsolutePath().normalize();
       var missionData = TransportVehicleMissionDAO.getAllTransportVehicleMissions();
        writeTransportationData(missionData, absolutePath.toString());


        //no need for assertions, we can see the output in the excel file

    }
    @Test
    public void testRead() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        String relativeFilePath = "src/main/java/org/example/Excel files/output.xlsx";
        Path absolutePath = Paths.get(relativeFilePath).toAbsolutePath().normalize();
        List<TransportVehicleMission> missionData = readTransportationData(absolutePath.toString());
        for (TransportVehicleMission mission : missionData) {
            System.out.println(mission);
        }
        //no need for assertions, we can see the output in the console

    }
}
