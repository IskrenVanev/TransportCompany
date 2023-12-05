package org.example.DTO;

import org.example.Models.Enums.VehicleType;

public class TransportVehicleDTO
{
    private long id;
    private VehicleType name;


    public TransportVehicleDTO(long id, VehicleType typeName) {
        this.id = id;
        this.name = typeName;
    }

    @Override
    public String toString() {
        return "TransportVehicleDTO{" +
                "id=" + id +
                ", TypeName='" + name + '\'' +
                '}';
    }
}
