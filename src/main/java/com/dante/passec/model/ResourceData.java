package com.dante.passec.model;

import javax.persistence.*;

/**
 * @model for Users' resources. It save id->long, login->String, password-> String, user->UserRest
 * @author Makarenko Sergey
 * @version 1.0
 */
@Entity
@Table(name = "resource_data")
public class ResourceData
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "login", nullable = false)
    String login;

    @Column(name = "password", nullable = false)
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    UserRest user;

    public ResourceData() {
    }

    public ResourceData(String login, String password, UserRest user) {
        this.login = login;
        this.password = password;
        this.user = user;
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

    public UserRest getUser() {
        return user;
    }

    public void setUser(UserRest user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResourceData{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
