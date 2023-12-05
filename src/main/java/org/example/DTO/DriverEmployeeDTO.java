package org.example.DTO;

public class DriverEmployeeDTO {
    private long id;
    private String name;

    public DriverEmployeeDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DriverEmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
