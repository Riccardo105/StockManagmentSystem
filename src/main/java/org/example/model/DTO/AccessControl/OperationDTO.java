package org.example.model.DTO.AccessControl;

import javax.persistence.*;

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

    public String getOperation() {
        return name;
    }

    public void setOperation(String operation) {
        this.name = operation;
    }
}
