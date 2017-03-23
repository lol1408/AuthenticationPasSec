package com.dante.passec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Calendar;
import java.util.Date;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static javax.persistence.TemporalType.*;

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
    Integer token;

    @Temporal(TIMESTAMP)
    @Column(nullable = false)
    Date date;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    UserRest user;

    @Column
    Boolean including;

    public Session(UserRest user) {
        this.user = user;
        this.including = true;
/*------Генерируем дату на 2 часа позже от текущей---------------------------*/
        Calendar rightNow = Calendar.getInstance();
        Date currentDate = new Date();
        rightNow.setTime(currentDate);
        rightNow.add(Calendar.HOUR, 2);
        Date newDate = rightNow.getTime();
/*---------------------------------------------------------------------------*/
        this.date = newDate;
        generateToken();
    }

    public Session() {
    }

    public Boolean isIncluding() {
        return including;
    }

    public void setIncluding(Boolean including) {
        this.including = including;
    }

    public void setToken(){
        this.token = token;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    private void generateToken(){this.token = this.hashCode();}

    public Integer getToken(){return this.token;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public UserRest getUser() {return user;}

    public void setUser(UserRest user) {this.user = user;}

    public String toString() {
        return "Session{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (id != null ? !id.equals(session.id) : session.id != null) return false;
        if (!token.equals(session.token)) return false;
        if (!date.equals(session.date)) return false;
        return user.equals(session.user);
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + date.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

}
