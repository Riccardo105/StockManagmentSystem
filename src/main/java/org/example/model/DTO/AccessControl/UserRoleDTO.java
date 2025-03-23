package org.example.model.DTO.AccessControl;

import javax.persistence.*;

@Entity
@Table(name = "userRole")
public class UserRoleDTO {

    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private UserDTO user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roleId")
    private RoleDTO role;

    public UserRoleDTO() {}

    public UserRoleDTO(UserDTO user, RoleDTO role) {
        this.user = user;
        this.role = role;
        this.userRoleId = new UserRoleId(user.getId(), role.getId());
    }

    public UserRoleId getUserRoleId() {
        return userRoleId;
    }

    public UserDTO getUser() {
        return user;
    }

    public RoleDTO getRole() {
        return role;
    }
}
