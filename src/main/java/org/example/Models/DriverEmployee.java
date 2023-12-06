package org.example.Models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "DriverEmployee")
public class DriverEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private TransportCompany company;

    private double salary;
    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private Set<Qualification> qualifications = new HashSet<>();
    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private List<TransportVehicleMission> missions= new ArrayList<>();
    public long getId() {
        return id;
    }

    public DriverEmployee() {
    }
    public DriverEmployee(String name, TransportCompany company, Set<Qualification> qualifications, double salary) {
        this.name = name;
        this.company = company;
        this.salary = salary;
        this.qualifications = qualifications;

    }
    public DriverEmployee(String name, TransportCompany company, double salary) {
        this.name = name;
        this.company = company;
        this.salary =salary;

    }

    public DriverEmployee(String name, TransportCompany company, Set<Qualification> qualifications) {
        this.name = name;
        this.company = company;
        this.qualifications = qualifications;
    }
    public DriverEmployee(String name, TransportCompany company) {
        this.name = name;
        this.company = company;

    }
    @Override
    public String toString() {
        return "DriverEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public List<TransportVehicleMission> getMissions() {
        return missions;
    }

    public TransportCompany getCompany() {
        return company;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public void setCompany(TransportCompany company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverEmployee that = (DriverEmployee) o;
        return id == that.id && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name) && Objects.equals(company, that.company) && Objects.equals(qualifications, that.qualifications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company, salary, qualifications);
    }

// public void setName(String name) {
 //       this.name = name;
 //   }
}
