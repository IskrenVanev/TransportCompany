//package java;// Import statements

import org.example.DTO.ClientDTO;
import org.example.Models.Client;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.TransportCompanyDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ClientDAOTest {

    @Test
    public void testisThereObligationsThatAreNotPaidForASpecificCompany(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        Client client = ClientsDAO.getClientById(6);
        TransportCompany tc = TransportCompanyDAO.getCompanyById(1);
        ClientsDAO.isThereObligationsThatAreNotPaidForASpecificCompany(client, tc);
        //We see the result printed in the console

    }
    @Test
    public void testIsThereObligationsThatAreNotPaid(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        Client client = ClientsDAO.getClientById(6);
        ClientsDAO.IsThereObligationsThatAreNotPaid(client);

        //We see the result printed in the console
    }
    @Test
    public void testPayAllObligations(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        Client client = ClientsDAO.getClientById(6);
        TransportCompany tc = TransportCompanyDAO.getCompanyById(1);
        ClientsDAO.PayAllObligations(client, tc);

        //we can just check the database for the change
    }


    @Test
    public void testPayObligation(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var obligationId = 2;
        Client client = ClientsDAO.getClientById(6);
        TransportCompany tc = TransportCompanyDAO.getCompanyById(1);

        ClientsDAO.payObligation(obligationId, client, tc);


        //we can just check the database for the change
       // assertEquals();

    }


    @Test
    public void testUpdateClient(){
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var client = ClientsDAO.getClientById(6);
        client.setName("Iskrencho");
        ClientsDAO.updateClient(client);


        assertEquals(client.getName(), "Iskrencho");

    }

    @Test
    public void testdeleteClientById() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        ClientsDAO.deleteClientById(5);

        assertNull(ClientsDAO.getClientById(5));
        assertEquals(ClientsDAO.getClients().stream().count(), ((long)2));
    }

    @Test
    public void testDeleteClient() {
        var session = SessionFactoryUtil.getSessionFactory().openSession();
        var client = ClientsDAO.getClientById(7);
        ClientsDAO.deleteClient(client);

        assertNull(ClientsDAO.getClientById(7));
        //assertEquals(ClientsDAO.getClients().stream().count(), ((long) 7));
    }


    @Test
    public void testGetClientsDTO() {
        // Arrange
        var session = SessionFactoryUtil.getSessionFactory().openSession();

        List<ClientDTO> clients = ClientsDAO.getClientsDTO(1);
        for(ClientDTO clientDTO : clients){
            System.out.println(clientDTO);
        }
        assertTrue(clients.size() > 0);
        assertEquals(4, clients.size());
    }


    @Test
    public void testGetClients() {
        // Arrange
        var session = SessionFactoryUtil.getSessionFactory().openSession();

        List<Client> clients = ClientsDAO.getClients();

        assertTrue(clients.size() > 0);
        assertEquals(7, clients.size());


    }

@Test
public void testGetClientById() {
    var session = SessionFactoryUtil.getSessionFactory().openSession();
   var clientGet =  assertDoesNotThrow(() -> ClientsDAO.getClientById(1));
    Client client = new Client("sssasdsas", 22222);
client.setId(1);
    assertEquals(clientGet.getId(), client.getId());
    assertEquals(clientGet.getName(), client.getName());
    assertEquals(clientGet.getFinances(), client.getFinances());


}

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
