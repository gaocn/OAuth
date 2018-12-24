package govind.propeties;

import govind.enumeration.LoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrowserSecurityProperties {
	private String loginPage  = "/login.html";
	private String loginType = LoginType.REDIRECT;
	private ImageValidateCodeProperties image =  new ImageValidateCodeProperties();

	/**
	 * 默认remember-me的时间为1小时
	 */
	private int rememberMeSeconds = 3600;
}
