package org.example.model.DTO.AccessControl;

import javax.persistence.*;

/**
 * This is a join table
 * role and permission are mapped to the corresponding value in the RolePermissionId.
 * This allows Hibernate to correctly compose the composite key.
 */
@Entity
@Table(name = "rolePermissions")
public class RolePermissionsDTO {

    @EmbeddedId
    private RolePermissionsId rolePermissionsId;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roleId")
    private RoleDTO role;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn( name = "permissionId")
    private PermissionDTO permission;

    public RolePermissionsDTO() {}

    public RolePermissionsDTO(RoleDTO role, PermissionDTO permission) {
        this.role = role;
        this.permission = permission;
        this.rolePermissionsId = new RolePermissionsId(role.getId(), permission.getId());
    }

    public RolePermissionsId getRolePermissionsId() {
        return rolePermissionsId;
    }

    public RoleDTO getRole() {
        return role;
    }

    public PermissionDTO getPermission() {
        return permission;
    }


}
