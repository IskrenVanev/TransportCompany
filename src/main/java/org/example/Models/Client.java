package org.example.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO:6. Начин за записване на това, дали клиентът си е платил задълженията.
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Name;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<Obligation> obligations;


    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;


    private double finances;

    public void setObligations(List<Obligation> obligations) {
        this.obligations = obligations;
    }

    public void addObligation(Obligation obligation) {
        obligations.add(obligation);
        obligation.setClient(this);
    }


    public Client(String name, TransportCompany company, double finances) {
        this.finances = finances;
        this.obligations = new ArrayList<>();
        this.Name = name;
        this.company = company;
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

    public TransportCompany getCompany() {
        return company;
    }

    public long getId() {
        return id;
    }
}
