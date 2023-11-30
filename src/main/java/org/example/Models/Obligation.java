package org.example.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "obligations")
public class Obligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(name = "amount")
    private Double amount;



    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;


    //@ManyToOne(fetch = FetchType.EAGER)
    //private TransportCompany company;

    public void setClient(Client client) {
        this.client = client;
    }

    public Obligation() {
    }

    public Obligation(Client client, Double amount, TransportCompany company) {

      //  this.company =company;
        this.client = client;
        this.amount = amount;
    }

   // public void setCompany(TransportCompany company) {
   //     this.company = company;
   // }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }
}
