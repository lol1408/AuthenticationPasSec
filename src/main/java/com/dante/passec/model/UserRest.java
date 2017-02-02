package com.dante.passec.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Model for entity user_rest
 * @author Dante de Braso
 * @version 1.0
 * Created by We on 21.12.2016.
 */
@Entity
@Table(name = "user_rest")
public class UserRest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "login", nullable = false, length = 30, unique = true)
    @Size(min = 6)
    String login;

    @Column(name = "password", nullable = false, length = 30)
    @Size(min = 6)
    String password;

    @OneToMany(mappedBy = "user")
    private Set<ResourceData> resources = new HashSet<>(0);

    public Set<ResourceData> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceData> resources) {
        this.resources = resources;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Override
    public String toString() {
        return "UserRest{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRest userRest = (UserRest) o;

        if (id != null ? !id.equals(userRest.id) : userRest.id != null) return false;
        if (login != null ? !login.equals(userRest.login) : userRest.login != null) return false;
        if (password != null ? !password.equals(userRest.password) : userRest.password != null) return false;
        return resources != null ? resources.equals(userRest.resources) : userRest.resources == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        return result;
    }

    public UserRest() {
    }

    public UserRest(String login, String password, Set<ResourceData> resources) {
        this.login = login;
        this.password = password;
        this.resources = resources;
    }
}

