package govind.exception;

import lombok.Getter;

@Getter
public class UserNotExistException extends RuntimeException {
	private String id;

	public UserNotExistException(String id) {
		super("User Not Exist");
		this.id = id;
	}
}
