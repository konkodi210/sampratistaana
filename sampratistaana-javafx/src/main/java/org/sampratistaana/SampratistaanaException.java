package org.sampratistaana;

public class SampratistaanaException extends RuntimeException {

	public SampratistaanaException(String message, Throwable cause) {
		super(Messages.getMessage(message), cause);
	}

	public SampratistaanaException(String message,Object... args) {
		super(Messages.getMessage(message,args));
	}

	public SampratistaanaException(Throwable cause) {
		super(cause);
	}
}
