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

    @ManyToOne(fetch = FetchType.LAZY)
    private TransportVehicle vehicle;
}
