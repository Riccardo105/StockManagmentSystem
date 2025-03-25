package org.example.model.DTO.AccessControl;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.AccessControl.RoleDTO;
import javax.persistence.*;


/**
 * This is a join table
 * role and permission are mapped to the corresponding value in the UserRoleId.
 * This allows Hibernate to correctly compose the composite key
 */
@Entity
@Table(name = "userRole")
public class UserRoleDTO {

    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private UserDTO user;

    @ManyToOne(fetch = FetchType.LAZY)
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
