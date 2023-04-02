package indi.ly.crush.mail.core.api.impl;

import indi.ly.crush.mail.core.api.AbstractMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * <h2>邮件服务接口实现</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Service
public class MailServiceImpl
		extends AbstractMailService {
	private final JavaMailSender javaMailSender;
	
	public MailServiceImpl(
			SpringTemplateEngine templateEngine,
			@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender javaMailSender
	) {
		super(templateEngine);
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	protected JavaMailSender getJavaMailSender() {
		return this.javaMailSender;
	}
}
