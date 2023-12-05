package org.example.Models;

import jakarta.persistence.*;
import org.example.Models.Enums.ContentType;

import java.time.LocalDate;
import java.util.Objects;


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

    private Double weight;


    public TransportVehicleMission(String departureStartingPoint,
                                   String departureArrivalPoint, LocalDate dateOfDeparture,
                                   LocalDate dateOfArrival, double priceForMission, TransportVehicle vehicle, ContentType content)
    {
        this.departureStartingPoint = departureStartingPoint;
        this.departureArrivalPoint = departureArrivalPoint;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        PriceForMission = priceForMission;
        this.vehicle = vehicle;
       // this.company = company;
        this.content = content;
        if (content==ContentType.STOCK) {
            this.weight = weight; // Replace with your calculation logic
        } else {
            // Set a default weight or handle other cases
            this.weight = null;
        }
    }

    public TransportVehicleMission() {
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private TransportVehicle vehicle;
  //  @ManyToOne(fetch = FetchType.EAGER)
  //  private TransportCompany company;
   // @OneToOne(mappedBy = "mission", fetch = FetchType.EAGER)
    private ContentType content;

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

    public Double getWeight() {
        return weight;
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

    public ContentType getContent() {
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

    public void setContent(ContentType content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportVehicleMission that = (TransportVehicleMission) o;
        return id == that.id && Double.compare(that.PriceForMission, PriceForMission) == 0 && Objects.equals(departureStartingPoint, that.departureStartingPoint) && Objects.equals(departureArrivalPoint, that.departureArrivalPoint) && Objects.equals(dateOfDeparture, that.dateOfDeparture) && Objects.equals(dateOfArrival, that.dateOfArrival) && Objects.equals(vehicle, that.vehicle) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureStartingPoint, departureArrivalPoint, dateOfDeparture, dateOfArrival, PriceForMission, vehicle, content);
    }
}
