package org.example.Models;

import jakarta.persistence.*;
import org.example.Models.Enums.ContentType;

@Entity
@Table(name = "TransportContent")
public class TransportContent {//either people or stock
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private ContentType content;//PEOPLE, STOCK
    @OneToOne(fetch = FetchType.LAZY)
    private TransportVehicle transportVehicle;
    private Double weight;

    public long getId() {
        return id;
    }

    public TransportContent() {
    }

    public TransportContent(long id, ContentType content, TransportVehicle transportVehicle, Double weight) {
        this.id = id;
        this.content = content;
        this.transportVehicle = transportVehicle;
        if (content==ContentType.STOCK) {
            this.weight = weight; // Replace with your calculation logic
        } else {
            // Set a default weight or handle other cases
            this.weight = null;
        }
    }

    @Override
    public String toString() {
        return "TransportContent{" +
                "id=" + id +
                ", content=" + content +
                ", weight=" + weight +
                '}';
    }

    public ContentType getContent() {
        return content;
    }

    public TransportVehicle getTransportVehicle() {
        return transportVehicle;
    }

    public double getWeight() {
        return weight;
    }
}
