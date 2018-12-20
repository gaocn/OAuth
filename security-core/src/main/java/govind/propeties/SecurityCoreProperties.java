package govind.propeties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "govind.security")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCoreProperties {
	private BrowserSecurityProperties browser = new BrowserSecurityProperties();
	private ValidateCodeProperties sms = new ValidateCodeProperties();

}
