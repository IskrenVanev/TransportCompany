package org.example.DTO;

import java.util.List;

public class ClientDTO {
    private long id;
    private String name;
    private double finances;
  // private String transportCompanyName;

    public ClientDTO(long id, String name, double finances) {
        this.id = id;
        this.name = name;
        this.finances = finances;
    }
    public ClientDTO(String name, double finances) {
        this.name = name;
        this.finances = finances;
        //this.transportCompanyName = transportCompanyName;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                ", finances=" + finances + '\'' +
                '}';
    }
}
