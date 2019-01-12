package me.vyara.transporthotspot.exceptions;

public class StopNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8243205081890741533L;

	public StopNotFoundException(Long id) {
		super("Could not find stop " + id);
	}
}