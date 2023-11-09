package org.example.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String Name;
    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;

    public Client(String name, TransportCompany company) {
        this.Name = name;
        this.company = company;
    }

    public Client() {
    }

    public void setName(String name) {
        Name = name;
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
