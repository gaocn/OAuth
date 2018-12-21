package govind.validate;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidateCode implements Serializable {
	private String code;
	private LocalDateTime expireTime;

	public ValidateCode(String code, long expireIn) {
		this(code, LocalDateTime.now().plusSeconds(expireIn));
	}
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expireTime);
	}
}
