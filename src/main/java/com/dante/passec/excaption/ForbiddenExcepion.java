package com.dante.passec.excaption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Created by sergey on 04.03.17.
 */
@ResponseStatus(value = FORBIDDEN, reason = "user already exist")
public class ForbiddenExcepion extends RuntimeException {
}
