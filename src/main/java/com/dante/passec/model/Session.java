package com.dante.passec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.sql.Date;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

/**
 *  Model for session
 *  @author Dante de Braso
 *  @version 1.0
 */
@Entity
@Table(name = "session")
public class Session {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column(nullable = false)
    String token;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    UserRest user;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public UserRest getUser() {return user;}

    public void setUser(UserRest user) {this.user = user;}

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (id != null ? !id.equals(session.id) : session.id != null) return false;
        if (!token.equals(session.token)) return false;
        if (!date.equals(session.date)) return false;
        return user.equals(session.user);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + token.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
