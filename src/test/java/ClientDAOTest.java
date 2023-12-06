//package java;// Import statements

import org.example.Models.Client;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.TransportCompanyDAO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ClientDAOTest {

//    @Mock
//    private SessionFactory mockSessionFactory;
//
//    @Mock
//    private Session mockSession;
//
//    @Mock
//    private Transaction mockTransaction;
//
//
//
//    @Before
//    public void setUp() {
//        mockSessionFactory = mock(SessionFactory.class);
//        mockSession = mock(Session.class);
//        mockTransaction = mock(Transaction.class);
//
//        when(mockSessionFactory.openSession()).thenReturn(mockSession);
//        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//    }


    @Test
    public void testCreateClient() {
        // Arrange
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        Client client = new Client("Iskren", 22222);

        // Act and Assert
        assertDoesNotThrow(() -> ClientsDAO.createClient(client));


            Client savedClient = session.get(Client.class, client.getId());
            assertEquals("Iskren", savedClient.getName());
            assertEquals(22222, savedClient.getFinances());


    }
    @Test
    public void testCreateClientWithCompany() {
        // Arrange
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var tc = TransportCompanyDAO.getCompanyById(1);
        Client client = new Client("Iskreeeen", 22232);
        ClientsDAO.addTransportCompany(tc,client);
        // Act and Assert
        assertDoesNotThrow(() -> ClientsDAO.createClient(client));


        Client savedClient = session.get(Client.class, client.getId());
        assertEquals("Iskreeeen", savedClient.getName());
        assertEquals(22232, savedClient.getFinances());
       assertTrue(savedClient.getTransportCompanies().stream().anyMatch(transportCompany -> transportCompany.getId() == 1));


    }
}
