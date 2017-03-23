package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = CONFLICT, reason = "user already exist")
public class UserAlreadyExistException extends RuntimeException{
}
