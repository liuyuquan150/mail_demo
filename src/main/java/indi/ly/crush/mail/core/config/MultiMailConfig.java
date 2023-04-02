package indi.ly.crush.mail.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <h2>多种邮件配置</h2>
 *
 * @author 云上的云
 * @see org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultiMailProperties.class)
public class MultiMailConfig {
	
	@Bean(name = "javaMailSenders")
	List<JavaMailSender> createJavaMailSenderListBean(
			@Autowired(required = false) JavaMailSender javaMailSender, MultiMailProperties properties
	) {
		List<MailProperties> configs = properties.getConfigs();
		List<JavaMailSender> javaMailSenders = Optional
													.ofNullable(configs)
													.orElseGet(() -> new ArrayList<>(0))
													.stream()
													.map(mailProperties -> applyProperties(mailProperties, new JavaMailSenderImpl()))
													.collect(Collectors.toCollection(LinkedList :: new));
		
		if (javaMailSender != null) {
			javaMailSenders.add(0, javaMailSender);
		}
		
		return javaMailSenders;
	}
	
	private JavaMailSender applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
		if (!properties.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(properties.getProperties()));
		}
		return sender;
	}
	
	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}
}
