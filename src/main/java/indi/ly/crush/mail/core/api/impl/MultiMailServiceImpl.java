package indi.ly.crush.mail.core.api.impl;

import indi.ly.crush.mail.core.api.AbstractMailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h2>多邮件服务接口实现</h2>
 *
 * @author 云上的云
 * @see <a href="https://segmentfault.com/a/1190000022945162">SpringBoot多邮件源发送邮件</a>
 * @since 1.0
 */
@Service
public class MultiMailServiceImpl
		extends AbstractMailService {
	private final List<JavaMailSender> javaMailSenderList;
	private final ThreadLocalRandom random;
	
	public MultiMailServiceImpl(
			SpringTemplateEngine templateEngine,
			@Qualifier(value = "javaMailSenders") List<JavaMailSender> javaMailSenderList
	) {
		super(templateEngine);
		this.javaMailSenderList = javaMailSenderList;
		this.random = ThreadLocalRandom.current();
	}
	
	@Override
	protected JavaMailSender getJavaMailSender() {
		int index = this.random.nextInt(this.javaMailSenderList.size());
		return this.javaMailSenderList.get(index);
	}
}
