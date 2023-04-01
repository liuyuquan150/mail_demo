package indi.ly.crush.mail.core.api.impl;

import indi.ly.crush.mail.core.api.IMailService;
import indi.ly.crush.mail.core.model.Annex;
import indi.ly.crush.mail.core.model.Mail;
import indi.ly.crush.mail.core.model.TemplateMail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * <h2>邮件服务接口实现</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@Service
public class MailServiceImpl
		implements IMailService {
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	@Value(value = "${spring.mail.username}")
	private String from;
	
	public MailServiceImpl(
			@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") JavaMailSender javaMailSender,
			SpringTemplateEngine templateEngine
	) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	@Override
	public void sendMail(Mail mail) {
		MimeMessage message = this.javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
			this.setMimeMessageHelper(mail, mimeMessageHelper);
			this.javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private void setMimeMessageHelper(Mail mail, MimeMessageHelper mimeMessageHelper) throws MessagingException {
		setFrom(mimeMessageHelper, mail.getFrom(), this.from);
		setTo(mimeMessageHelper, mail.getTo());
		setReplyTo(mimeMessageHelper, mail.getReplyTo());
		setCc(mimeMessageHelper, mail.getCc());
		setBcc(mimeMessageHelper, mail.getBcc());
		setSentDate(mimeMessageHelper, mail.getSentDate());
		setSubject(mimeMessageHelper, mail.getSubject());
		setText(mimeMessageHelper, this.templateEngine, mail);
		setAnnex(mimeMessageHelper, mail.getAnnex());
	}
	
	private static void setFrom(MimeMessageHelper mimeMessageHelper, String from, String from2) throws MessagingException {
		if (!StringUtils.hasText(from)) {
			from = from2;
		}
		mimeMessageHelper.setFrom(from);
	}
	
	private static void setReplyTo(MimeMessageHelper mimeMessageHelper, String replyTo) throws MessagingException {
		if (StringUtils.hasText(replyTo)) {
			mimeMessageHelper.setReplyTo(replyTo);
		}
	}
	
	private static void setTo(MimeMessageHelper mimeMessageHelper, String[] to) throws MessagingException {
		if (to == null || to.length < 1) {
			throw new RuntimeException("Please set the email recipient.");
		}
		mimeMessageHelper.setTo(to);
	}
	
	private static void setCc(MimeMessageHelper mimeMessageHelper, String[] cc) throws MessagingException {
		if (cc != null && cc.length > 0) {
			mimeMessageHelper.setCc(cc);
		}
	}
	
	private static void setBcc(MimeMessageHelper mimeMessageHelper, String[] bcc) throws MessagingException {
		if (bcc != null && bcc.length > 0) {
			mimeMessageHelper.setBcc(bcc);
		}
	}
	
	private void setSentDate(MimeMessageHelper mimeMessageHelper, LocalDate sentDate) throws MessagingException {
		if (sentDate != null) {
			ZoneId zoneId = ZoneId.systemDefault();
			Date date = Date.from(sentDate.atStartOfDay().atZone(zoneId).toInstant());
			mimeMessageHelper.setSentDate(date);
		}
	}
	
	private static void setSubject(MimeMessageHelper mimeMessageHelper, String subject) throws MessagingException {
		if (!StringUtils.hasText(subject)) {
			throw new RuntimeException("Please set the email subject.");
		}
		mimeMessageHelper.setSubject(subject);
	}
	
	private static void setText(MimeMessageHelper mimeMessageHelper, SpringTemplateEngine templateEngine, Mail mail) throws MessagingException {
		String text = mail.getText();
		boolean isHtml = mail instanceof TemplateMail;
		if (mail instanceof TemplateMail templateMail) {
			// 模板合成 Html 内容
			text = templateEngine.process(templateMail.getPath(), templateMail.getContext());
		}
		if (!StringUtils.hasText(text)) {
			throw new RuntimeException("Please set the email content.");
		}
		mimeMessageHelper.setText(text, isHtml);
	}
	
	private static void setAnnex(MimeMessageHelper mimeMessageHelper, Annex annex) throws MessagingException {
		if (annex != null) {
			List<File> annexList = annex.getAnnexList();
			for (File annexFile : annexList) {
				//	附件名称. 必须是服务器本地文件名, 不能是远程文件链接.
				mimeMessageHelper.addAttachment(annexFile.getName(), annexFile);
			}
		}
	}
}
