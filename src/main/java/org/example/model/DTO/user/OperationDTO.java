package org.example.model.DTO.user;

public class OperationDTO {
    private int id;
    private String name;

    public OperationDTO(String name) {
        this.name = name;
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
