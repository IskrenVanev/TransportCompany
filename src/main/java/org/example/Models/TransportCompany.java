package org.example.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TransportCompany")
public class TransportCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<DriverEmployee> driverEmployees = new HashSet<>();
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<TransportVehicle> vehicles = new ArrayList<>();




    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<DriverEmployee> getDriverEmployees() {
        return driverEmployees;
    }

    public List<TransportVehicle> getVehicles() {
        return vehicles;
    }
}
