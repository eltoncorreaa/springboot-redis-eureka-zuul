package com.elton.app.exception;

public class ManagedRedisException extends RuntimeException{

	private static final long serialVersionUID = -7106307367058573413L;

	public ManagedRedisException(final String message, final Exception cause){
		super(message, cause.getCause() != null ? cause.getCause() : cause);
	}
}
