package org.example.model.DTO.AccessControl;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceDTO resourceDTO = (ResourceDTO) o;
        return Objects.equals(name, resourceDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
