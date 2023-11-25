package org.example.DTO;

import org.example.Models.Enums.ContentType;

public class TransportContentDTO {
    private long id;
    private ContentType content;//PEOPLE, STOCK
    private Double weight;

    public TransportContentDTO(ContentType content, Double weight) {
        this.content = content;
        if (content==ContentType.STOCK) {
            this.weight = weight; // Replace with your calculation logic
        } else {
            // Set a default weight or handle other cases
            this.weight = null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ContentType getContent() {
        return content;
    }

    public void setContent(ContentType content) {
        this.content = content;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
