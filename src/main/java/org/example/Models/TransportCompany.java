package org.example.Models;

import jakarta.persistence.*;

import java.util.*;

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
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<TransportVehicleMission> missions = new ArrayList<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Client> clients = new ArrayList<>();


    public TransportCompany() {
    }

    public TransportCompany(String name) {

        this.name = name;

    }

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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportCompany that = (TransportCompany) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name +"}" +'\'';
    }
}
