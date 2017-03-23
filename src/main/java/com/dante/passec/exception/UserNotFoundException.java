package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = UNAUTHORIZED, reason = "user is not real")
public class UserNotFoundException extends RuntimeException {
}
