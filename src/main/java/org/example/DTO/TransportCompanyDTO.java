package org.example.DTO;

public class TransportCompanyDTO {
    private long id;
    private String name;

    public TransportCompanyDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TransportCompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
