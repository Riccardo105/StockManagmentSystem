package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class PermissionsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,  // Save operation will cascade
            CascadeType.MERGE     // Update operation will cascade
    })
    @JoinColumn(name = "operationId")
    private OperationDTO operation;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,  // Save operation will cascade
            CascadeType.MERGE     // Update operation will cascade
    })
    @JoinColumn(name = "resourceId")
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
