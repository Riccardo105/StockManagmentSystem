package org.example.model.DTO.AccessControl;

import javax.persistence.*;
import java.util.Objects;

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
    private PermissionsDTO permission;

    public RolePermissionsDTO() {}

    public RolePermissionsDTO(RoleDTO role, PermissionsDTO permission) {
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

    public PermissionsDTO getPermission() {
        return permission;
    }


}
