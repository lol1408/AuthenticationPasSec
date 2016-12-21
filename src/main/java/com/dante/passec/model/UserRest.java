package com.dante.passec.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Model for entity user_rest
 * @author Dante de Braso
 * @version 1.0
 * Created by We on 21.12.2016.
 */
@Entity(name = "user_rest")
public class UserRest {

    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "login", nullable = false, length = 30)
    @Size(min = 6)
    String login;

    @Column(name = "password", nullable = false, length = 30)
    @Size(min = 6)
    String password;

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

        if (!id.equals(userRest.id)) return false;
        if (!login.equals(userRest.login)) return false;
        return password.equals(userRest.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
