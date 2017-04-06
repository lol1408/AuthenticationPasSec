package com.dante.passec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

/**
 * @model for Users' resources. It save id->long, url->String, password-> String, user->UserRest
 * @author Makarenko Sergey
 * @version 1.0
 */
@Entity
@Table(name = "resource_data")
public class ResourceData
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(name = "url")
    String url;

    @Column(name = "password")
    String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    UserRest user;

    public ResourceData() {
    }

    public ResourceData(String login, String password, UserRest user) {
        this.url = login;
        this.password = password;
        this.user = user;
    }
    public ResourceData(ResourceData resource) {
        this.id = resource.id;
        this.url = resource.url;
        this.password = resource.password;
        this.user = resource.user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceData that = (ResourceData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResourceData{" +
                ", url='" + url + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
