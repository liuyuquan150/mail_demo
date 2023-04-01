package indi.ly.crush.mail.core.api;

import indi.ly.crush.mail.core.event.SendEmailEvent;
import indi.ly.crush.mail.core.model.Annex;
import indi.ly.crush.mail.core.model.Mail;
import indi.ly.crush.mail.core.model.TemplateMail;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 云上的云
 * @see IMailService
 * @since 1.0
 */
@Log4j2
@DisplayName(value = "indi.ly.crush.mail.core.api.IMailServiceTest 测试单元")
@SpringBootTest
class IMailServiceTest {
	static String to;
	static String subject;
	static String path;
	static String name;
	static Map<String, String> templateParameters;
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@BeforeAll
	static void initAll() {
		to = "xxx@163.com";
		subject = "测试邮件";
		path = "email/emailTemplate";
		name = "emailParam";
		templateParameters = Map.of("companyName", "才华有限", "address", "翻斗大街翻斗花园二号楼1001室", "phone", "12138");
	}
	
	/**
	 * @see IMailService#sendMail(Mail)
	 */
	@Test
	@SneakyThrows(value = {IOException.class, InterruptedException.class})
	void sendMail() {
		/* The acquisition of resources must be guaranteed to be successful on the production environment as well */
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource resource = resourcePatternResolver.getResource("static/img/167316625.jpg");
		Resource resource2 = resourcePatternResolver.getResource("static/去海边吧夏日小清新模板.pptx");

		Annex annex = new Annex(List.of(resource.getFile(), resource2.getFile()));
		TemplateMail templateMail = new TemplateMail(to, subject, annex, path,  name, templateParameters);
		this.applicationEventPublisher.publishEvent(new SendEmailEvent(this, templateMail));
		// It's important to make the main thread wait for the asynchronous thread
		Thread.sleep(10000);
	}
}