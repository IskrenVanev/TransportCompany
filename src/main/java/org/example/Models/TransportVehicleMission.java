package org.example.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TransportVehicleMission")
public class TransportVehicleMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String departureStartingPoint;
    private String departureArrivalPoint;
    private LocalDate dateOfDeparture;
    private LocalDate dateOfArrival;

    private double PriceForMission;

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportVehicle vehicle;
    @ManyToOne(fetch = FetchType.LAZY)
    private TransportCompany company;

    @Override
    public String toString() {
        return "TransportVehicleMission{" +
                "id=" + id +
                ", departureStartingPoint='" + departureStartingPoint + '\'' +
                ", departureArrivalPoint='" + departureArrivalPoint + '\'' +
                ", dateOfDeparture=" + dateOfDeparture +
                ", dateOfArrival=" + dateOfArrival +
                ", PriceForMission=" + PriceForMission +
                ", vehicle=" + vehicle +
                ", company=" + company +
                '}';
    }
}
