package com.app.err;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public
class UserFoundException extends RuntimeException {

	public UserFoundException(String userId) {
		super("found user '" + userId + "'.");
	}
}
