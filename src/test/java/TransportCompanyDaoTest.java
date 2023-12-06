import org.example.Models.Client;
import org.example.Models.Obligation;
import org.example.Models.TransportCompany;
import org.example.configuration.SessionFactoryUtil;
import org.example.dao.ClientsDAO;
import org.example.dao.TransportCompanyDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransportCompanyDaoTest {//(Obligation obligation , Client client)

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

        assertEquals(2, session.get(TransportCompany.class, 2L).getId());
        assertEquals("Company2", session.get(TransportCompany.class, 2L).getName());


    }
}
