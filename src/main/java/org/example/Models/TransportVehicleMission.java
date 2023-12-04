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


    public TransportVehicleMission(String departureStartingPoint,
                                   String departureArrivalPoint, LocalDate dateOfDeparture,
                                   LocalDate dateOfArrival, double priceForMission, TransportVehicle vehicle, TransportContent content)
    {
        this.departureStartingPoint = departureStartingPoint;
        this.departureArrivalPoint = departureArrivalPoint;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        PriceForMission = priceForMission;
        this.vehicle = vehicle;
       // this.company = company;
        this.content = content;
    }

    public TransportVehicleMission() {
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private TransportVehicle vehicle;
  //  @ManyToOne(fetch = FetchType.EAGER)
  //  private TransportCompany company;
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
                '}';
    }

    public long getId() {
        return id;
    }

    public String getDepartureStartingPoint() {
        return departureStartingPoint;
    }

    public String getDepartureArrivalPoint() {
        return departureArrivalPoint;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public double getPriceForMission() {
        return PriceForMission;
    }

    public TransportVehicle getVehicle() {
        return vehicle;
    }

    public TransportContent getContent() {
        return content;
    }

    public void setDepartureStartingPoint(String departureStartingPoint) {
        this.departureStartingPoint = departureStartingPoint;
    }

    public void setDepartureArrivalPoint(String departureArrivalPoint) {
        this.departureArrivalPoint = departureArrivalPoint;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public void setPriceForMission(double priceForMission) {
        PriceForMission = priceForMission;
    }

    public void setVehicle(TransportVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setContent(TransportContent content) {
        this.content = content;
    }
}
