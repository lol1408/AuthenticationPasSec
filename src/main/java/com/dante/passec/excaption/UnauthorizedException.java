package com.dante.passec.excaption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * Created by sergey on 20.02.17.
 */
@ResponseStatus(value = UNAUTHORIZED, reason = "The session is not relevant")
public class UnauthorizedException extends RuntimeException {

}
