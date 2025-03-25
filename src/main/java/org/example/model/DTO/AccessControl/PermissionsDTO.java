package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "operationId")
    private OperationDTO operation;

    @ManyToOne()
    @JoinColumn(name = "resourceId")
    private ResourceDTO resource;

    public PermissionsDTO() {}

    public PermissionsDTO(OperationDTO operation, ResourceDTO resource) {
        this.operation = operation;
        this.resource = resource;
    }

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
