package com.dante.passec.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

@ResponseStatus(value = NOT_FOUND, reason = "user is not real")
public class NotFoundException extends RuntimeException {
}
