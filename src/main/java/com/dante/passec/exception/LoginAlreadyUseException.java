package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = BAD_REQUEST, reason = "Login already use") //400
public class LoginAlreadyUseException extends RuntimeException {
}
