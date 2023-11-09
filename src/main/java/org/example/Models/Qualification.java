package org.example.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Qualification")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private DriverEmployee driver;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DriverEmployee getDriver() {
        return driver;
    }
}
