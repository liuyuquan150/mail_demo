package indi.ly.crush.mail.core.event;

import indi.ly.crush.mail.core.model.Mail;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * <h2>发送邮件事件</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public class SendEmailEvent
		extends ApplicationEvent {
	private final Mail mail;
	
	public SendEmailEvent(Object source, @NonNull Mail mail) {
		// source: 事件最初发生的对象, 或者事件与之相关的对象
		super(source);
		this.mail = Objects.requireNonNull(mail, "mail is null.");
	}
	
	public Mail getMail() {
		return mail;
	}
}
