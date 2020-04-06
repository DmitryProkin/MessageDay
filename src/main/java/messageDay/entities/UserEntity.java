package messageDay.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "public", catalog = "messageDay")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "login", nullable = false, length = 255)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @Basic
    @Column(name = "firstname", nullable = false, length = 255)
    private String firstname;
    @Basic
    @Column(name = "lastname", nullable = false, length = 255)
    private String lastname;

    @Basic
    @Column(name = "active", nullable = true)
    private Integer active;

    @Basic
    @Column(name = "role_id", nullable = true)
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = true, insertable = false, updatable = false)
    private RoleEntity role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity rol) {
        this.role = role;
    }

    public String getFIO(){
        return firstname + " " + lastname;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(active, that.active) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, firstname, lastname, active, roleId);
    }
}
