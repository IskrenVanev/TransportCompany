package org.example.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

//TODO: 5. Възможност за записване на данни за превозите (дестинация, товар, цена и др.)


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
    @OneToOne(mappedBy = "mission", fetch = FetchType.EAGER)
    private TransportContent content;

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
