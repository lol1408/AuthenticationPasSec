package com.dante.passec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "email is busy")
public class EmailIsBusyException extends RuntimeException{
}
