package govind.config;

import govind.propeties.SecurityCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: govind
 * 让配置类SecurityCoreProperties生效
 */
@Configuration
@EnableConfigurationProperties(SecurityCoreProperties.class)
public class SecurityCorePropertiesConfig {
}
