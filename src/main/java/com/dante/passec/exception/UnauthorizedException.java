package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(value = UNAUTHORIZED, reason = "The session is not relevant")
public class UnauthorizedException extends RuntimeException {

}
