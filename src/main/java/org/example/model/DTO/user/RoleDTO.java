package org.example.model.DTO.user;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String parentRole;

    public RoleDTO(String name){
        this.name = name;
    }

    public RoleDTO(String name, String parentRole) {
        this.name = name;
        this.parentRole = parentRole;
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

    public String getParentRole() {
        return parentRole;
    }

    public void setParentRole(String parentRole) {
        this.parentRole = parentRole;
    }
}
