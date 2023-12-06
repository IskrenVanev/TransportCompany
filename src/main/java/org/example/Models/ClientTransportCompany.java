package org.example.Models;

import jakarta.persistence.*;
@Entity
@Table(name = "ClientTransportCompany")
public class ClientTransportCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private TransportCompany company;

}
