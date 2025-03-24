package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "resources")
public class ResourceDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public ResourceDTO() {}

    public ResourceDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResourceDTO{" + "name='" + name + '\'' + '}';
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
