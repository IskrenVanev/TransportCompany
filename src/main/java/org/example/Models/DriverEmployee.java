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

    public String getName() {
        return name;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }
}
