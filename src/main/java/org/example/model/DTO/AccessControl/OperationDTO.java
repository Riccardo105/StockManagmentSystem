package org.example.model.DTO.AccessControl;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "operations")
public class OperationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public OperationDTO() {}

    public OperationDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OperationDTO{" + "name='" + name + '\'' + '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String operation) {
        this.name = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationDTO operationDTO = (OperationDTO) o;
        return Objects.equals(name, operationDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
