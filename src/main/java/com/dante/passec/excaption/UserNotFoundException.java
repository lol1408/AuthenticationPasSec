package com.dante.passec.excaption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * Generating when user is not real
 **/

@ResponseStatus(value = UNAUTHORIZED, reason = "user is not real")
public class UserNotFoundException extends RuntimeException {
}
