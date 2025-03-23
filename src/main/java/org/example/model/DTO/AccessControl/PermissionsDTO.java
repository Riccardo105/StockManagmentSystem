package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private OperationDTO operation;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private ResourceDTO resource;

    // Constructor
    public PermissionsDTO() {}

    public PermissionsDTO(OperationDTO operation, ResourceDTO resource) {
        this.operation = operation;
        this.resource = resource;
    }

    // Getters
    public int getId() {
        return id;
    }

    public OperationDTO getOperation() {
        return operation;
    }

    public ResourceDTO getResource() {
        return resource;
    }
}
