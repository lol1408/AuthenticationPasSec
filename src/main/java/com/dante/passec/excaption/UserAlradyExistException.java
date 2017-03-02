package com.dante.passec.excaption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = CONFLICT, reason = "user already exist")
public class UserAlradyExistException extends RuntimeException{
}
