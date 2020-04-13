package com.paycraft.cmsauthentication.exception;

import lombok.Getter;

@Getter
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;

	public DaoException(String message) {
		super(message);
		this.message = message;
	}

}
