package com.dante.passec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resource is not founded")
public class ResourceIsNotFound extends RuntimeException{
}
