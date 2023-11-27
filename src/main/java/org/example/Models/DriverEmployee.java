package org.example.Models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DriverEmployee")
public class DriverEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private Set<Qualification> qualifications = new HashSet<>();

    public long getId() {
        return id;
    }

    public DriverEmployee() {
    }


    public DriverEmployee(String name, TransportCompany company, Set<Qualification> qualifications) {
        this.name = name;
        this.company = company;
        this.qualifications = qualifications;
    }

    @Override
    public String toString() {
        return "DriverEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }
}
