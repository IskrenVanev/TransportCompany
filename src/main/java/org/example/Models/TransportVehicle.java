package org.example.Models;

import jakarta.persistence.*;
import org.example.Models.Enums.VehicleType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TransportVehicle")
public class TransportVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;
    @OneToOne(mappedBy = "transportVehicle", fetch = FetchType.LAZY)
    private TransportContent transportContent;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<TransportVehicleMission> missions= new ArrayList<>();
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TransportVehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType +
                ", company=" + company +
                ", transportContent=" + transportContent +
                '}';
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public TransportContent getTransportContent() {
        return transportContent;
    }
}
