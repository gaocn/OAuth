package govind.config;

import govind.propeties.SecurityCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityCoreProperties.class)
public class SecurityCoreConfig {
}
