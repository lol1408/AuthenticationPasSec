package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(value = NOT_ACCEPTABLE, reason = "user already exist")
public class UserWithEmailAlreadyExistException extends RuntimeException{
}
