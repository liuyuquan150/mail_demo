package indi.ly.crush.mail.core.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <h2>多种邮件属性</h2>
 * <p>
 *     用于配置多个电子邮件.
 * </p>
 *
 * @author 云上的云
 * @since 1.0
 */
@ConfigurationProperties(prefix = "mail")
public class MultiMailProperties {
	private List<MailProperties> configs;
	
	public List<MailProperties> getConfigs() {
		return configs;
	}
	
	public void setConfigs(List<MailProperties> configs) {
		this.configs = configs;
	}
}
