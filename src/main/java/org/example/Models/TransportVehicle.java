package org.example.Models;

import jakarta.persistence.*;
import org.example.Models.Enums.VehicleType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TransportVehicle")
public class TransportVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @ManyToOne(fetch = FetchType.EAGER)
    private TransportCompany company;
    //@OneToOne(mappedBy = "transportVehicle", fetch = FetchType.EAGER)
   // private TransportContent transportContent;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
    private List<TransportVehicleMission> missions= new ArrayList<>();

    public TransportVehicle() {
    }

    public TransportVehicle(VehicleType vehicleType, TransportCompany company,  List<TransportVehicleMission> missions) {
        this.vehicleType = vehicleType;
        this.company = company;

        this.missions = missions;
    }

    public long getId() {
        return id;
    }

    public List<TransportVehicleMission> getMissions() {
        return missions;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

   // public void setTransportContent(TransportContent transportContent) {
      //  this.transportContent = transportContent;
 //   }

    public void setMissions(List<TransportVehicleMission> missions) {
        this.missions = missions;
    }

    @Override
    public String toString() {
        return "TransportVehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType +
                ", company=" + company  +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportVehicle vehicle = (TransportVehicle) o;
        return id == vehicle.id && vehicleType == vehicle.vehicleType && Objects.equals(company, vehicle.company) && Objects.equals(missions, vehicle.missions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleType, company, missions);
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public TransportCompany getCompany() {
        return company;
    }

   // public TransportContent getTransportContent() {
   //     return transportContent;
   // }
}
