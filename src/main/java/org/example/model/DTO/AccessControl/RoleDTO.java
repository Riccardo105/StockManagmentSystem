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
    @JoinColumn(name = "childRole")
    private RoleDTO childRole;

    public RoleDTO() {}

    public RoleDTO(String name){
        this.name = name;
    }

    public RoleDTO(String name, RoleDTO childRole) {
        this.name = name;
        this.childRole = childRole;
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

    public void setChildRole(RoleDTO childRole) {
        this.childRole = childRole;
    }

    public RoleDTO getChildRole() {
        return childRole;
    }
}
