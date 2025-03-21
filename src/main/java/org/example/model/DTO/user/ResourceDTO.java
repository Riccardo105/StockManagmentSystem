package org.example.model.DTO.user;

import javax.persistence.*;

@Entity
@Table(name = "resources")
public class ResourceDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    public ResourceDTO(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
