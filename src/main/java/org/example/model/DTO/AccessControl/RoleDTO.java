package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentRoleId")
    private RoleDTO parentRole;

    public RoleDTO() {}

    public RoleDTO(String name){
        this.name = name;
    }

    public RoleDTO(String name, RoleDTO parentRole) {
        this.name = name;
        this.parentRole = parentRole;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParentRole(RoleDTO parentRole) {
        this.parentRole = parentRole;
    }

    public RoleDTO getParentRole() {
        return parentRole;
    }
}
