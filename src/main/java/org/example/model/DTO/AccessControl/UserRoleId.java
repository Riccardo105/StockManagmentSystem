package org.example.model.DTO.AccessControl;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;



/**
 * this embeddable class is used by hibernate to construct and manage
 * the composite primary key in the userRole table
 */
@Embeddable
public class UserRoleId implements Serializable {
    private int userId;
    private int roleId;

    public UserRoleId() {}

    public UserRoleId(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
