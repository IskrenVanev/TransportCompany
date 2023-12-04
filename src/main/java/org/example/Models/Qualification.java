package org.example.Models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Qualification")
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private DriverEmployee driver;

    @Override
    public String toString() {
        return "Qualification{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Qualification() {
    }

    public Qualification(String name, DriverEmployee driver) {
        this.name = name;
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Qualification that = (Qualification) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

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
