package org.example.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "obligations")
public class Obligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "amount")
    private Double amount;

    public void setClient(Client client) {
        this.client = client;
    }

    public Obligation() {
    }

    public Obligation(Client client, Double amount) {
        this.client = client;
        this.amount = amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
