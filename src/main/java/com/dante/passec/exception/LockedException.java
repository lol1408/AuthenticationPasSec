package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.LOCKED;


@ResponseStatus(value = LOCKED, reason = "user already exist")
public class LockedException extends RuntimeException{
}
