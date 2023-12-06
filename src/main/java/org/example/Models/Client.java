package org.example.Models;

import jakarta.persistence.*;
import org.example.configuration.SessionFactoryUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Name;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER )
    private List<Obligation> obligations;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ClientTransportCompany",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<TransportCompany> transportCompanies;



    private double finances;

    public void setObligations(List<Obligation> obligations) {
        this.obligations = obligations;
    }

    public void addObligation(Obligation obligation) {
        obligations.add(obligation);
        obligation.setClient(this);
    }
    public Client(String name,  double finances) {
        this.finances = finances;
        this.Name = name;
        this.obligations = new ArrayList<>();
        this.transportCompanies = new ArrayList<>();

      //  this.transportCompanies.add(company);
    }



    public List<Obligation> getObligations() {
        return obligations;
    }
    public Obligation getObligation(int id) {
        Optional<Obligation> optionalObligation = obligations.stream()
                .filter(o -> o.getId() == id)
                .findFirst();

        return optionalObligation.orElse(null);
    }
    public void setFinances(double finances) {
        this.finances = finances;
    }

    public double getFinances() {
        return finances;
    }

    public Client() {
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                '}';
    }

    //public TransportCompany getCompany() {
 //       return company;
  //  }

    public List<TransportCompany> getTransportCompanies() {
        return transportCompanies;
    }

    public long getId() {
        return id;
    }
}
