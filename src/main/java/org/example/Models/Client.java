package org.example.Models;

import jakarta.persistence.*;

import java.util.List;

//TODO:6. Начин за записване на това, дали клиентът си е платил задълженията.
@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Obligation> obligations;


    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;

    public void setObligations(List<Obligation> obligations) {
        this.obligations = obligations;
    }

    public void addObligation(Obligation obligation) {
        obligations.add(obligation);
        obligation.setClient(this);
    }


    public Client(String name, TransportCompany company) {
        this.Name = name;
        this.company = company;
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
