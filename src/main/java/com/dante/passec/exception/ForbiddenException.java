package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@ResponseStatus(value = FORBIDDEN, reason = "user already exist")
public class ForbiddenException extends RuntimeException {
}
