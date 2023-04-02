package indi.ly.crush.mail.core.api;

import indi.ly.crush.mail.core.model.Annex;
import indi.ly.crush.mail.core.model.Mail;
import indi.ly.crush.mail.core.model.TemplateMail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import java.util.Objects;

/**
 * <h2>邮件基础设施</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class AbstractMailService
		implements IMailService {
	private final SpringTemplateEngine templateEngine;
	
	protected AbstractMailService(SpringTemplateEngine templateEngine) {
		this.templateEngine = Objects.requireNonNull(templateEngine, "SpringTemplateEngine is null.");
	}
	
	protected abstract JavaMailSender getJavaMailSender();
	
	@Override
	public void sendMail(Mail mail) {
		/*
			This instance cannot exist as an attribute,
			because it would cause confusion between the instances used by 'MailServiceImpl' and 'MultiMailServiceImpl'.
		 */
		JavaMailSender javaMailSender = this.getJavaMailSender();
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
			setFrom(mimeMessageHelper, mail.getFrom(), ((JavaMailSenderImpl) javaMailSender).getUsername());
			this.setMimeMessageHelper(mail, mimeMessageHelper);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	protected void setMimeMessageHelper(Mail mail, MimeMessageHelper mimeMessageHelper) throws MessagingException {
		setTo(mimeMessageHelper, mail.getTo());
		setReplyTo(mimeMessageHelper, mail.getReplyTo());
		setCc(mimeMessageHelper, mail.getCc());
		setBcc(mimeMessageHelper, mail.getBcc());
		setSentDate(mimeMessageHelper, mail.getSentDate());
		setSubject(mimeMessageHelper, mail.getSubject());
		setText(mimeMessageHelper, this.templateEngine, mail);
		setAnnex(mimeMessageHelper, mail.getAnnex());
	}
	
	protected static void setFrom(MimeMessageHelper mimeMessageHelper, String from, String from2)
			throws MessagingException {
		if (!StringUtils.hasText(from)) {
			from = from2;
		}
		mimeMessageHelper.setFrom(from);
	}
	
	protected static void setReplyTo(MimeMessageHelper mimeMessageHelper, String replyTo)
			throws MessagingException {
		if (StringUtils.hasText(replyTo)) {
			mimeMessageHelper.setReplyTo(replyTo);
		}
	}
	
	protected static void setTo(MimeMessageHelper mimeMessageHelper, String[] to)
			throws MessagingException {
		if (to == null || to.length < 1) {
			throw new RuntimeException("Please set the email recipient.");
		}
		mimeMessageHelper.setTo(to);
	}
	
	protected static void setCc(MimeMessageHelper mimeMessageHelper, String[] cc)
			throws MessagingException {
		if (cc != null && cc.length > 0) {
			mimeMessageHelper.setCc(cc);
		}
	}
	
	protected static void setBcc(MimeMessageHelper mimeMessageHelper, String[] bcc)
			throws MessagingException {
		if (bcc != null && bcc.length > 0) {
			mimeMessageHelper.setBcc(bcc);
		}
	}
	
	protected static void setSentDate(MimeMessageHelper mimeMessageHelper, LocalDate sentDate)
			throws MessagingException {
		if (sentDate != null) {
			ZoneId zoneId = ZoneId.systemDefault();
			Date date = Date.from(sentDate.atStartOfDay().atZone(zoneId).toInstant());
			mimeMessageHelper.setSentDate(date);
		}
	}
	
	protected static void setSubject(MimeMessageHelper mimeMessageHelper, String subject)
			throws MessagingException {
		if (!StringUtils.hasText(subject)) {
			throw new RuntimeException("Please set the email subject.");
		}
		mimeMessageHelper.setSubject(subject);
	}
	
	protected static void setText(MimeMessageHelper mimeMessageHelper, SpringTemplateEngine templateEngine, Mail mail)
			throws MessagingException {
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
	
	protected static void setAnnex(MimeMessageHelper mimeMessageHelper, Annex annex)
			throws MessagingException {
		if (annex != null) {
			List<File> annexList = annex.getAnnexList();
			for (File annexFile : annexList) {
				//	附件名称. 必须是服务器本地文件名, 不能是远程文件链接.
				mimeMessageHelper.addAttachment(annexFile.getName(), annexFile);
			}
		}
	}
}
