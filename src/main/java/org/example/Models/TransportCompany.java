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
    private long income;
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private Set<DriverEmployee> driverEmployees = new HashSet<>();
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<TransportVehicle> vehicles = new ArrayList<>();
  //  @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
  //  private List<TransportVehicleMission> missions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ClientTransportCompany",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private List<Client> clients = new ArrayList<>();


    //@OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    //private List<Obligation> obligations = new ArrayList<>();


    public TransportCompany() {
    }

    public TransportCompany(String name) {
       // this.obligations = new ArrayList<>();
        this.income = 0;
        this.name = name;

    }
  //  public void addObligation(Obligation obligation) {
   //     obligations.add(obligation);

  //  }

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


 //   public List<TransportVehicleMission> getMissions() {
 //       return missions;
  //  }

    public List<Client> getClients() {
        return clients;
    }

    public void setDriverEmployees(Set<DriverEmployee> driverEmployees) {
        this.driverEmployees = driverEmployees;
    }

    public void setVehicles(List<TransportVehicle> vehicles) {
        this.vehicles = vehicles;
    }

  //  public void setMissions(List<TransportVehicleMission> missions) {
   //     this.missions = missions;
 //   }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setIncome(long income) {
        this.income = income;
    }

    public long getIncome() {
        return income;
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
