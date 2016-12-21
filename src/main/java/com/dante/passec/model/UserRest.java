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
public class User {

    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "login", nullable = false, length = 30)
    @Size(min = 6)
    String login;

    @Column(name = "password", nullable = false, length = 30)

}
