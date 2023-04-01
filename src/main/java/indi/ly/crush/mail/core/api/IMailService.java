package indi.ly.crush.mail.core.api;

import indi.ly.crush.mail.core.model.Mail;

/**
 * <h2>邮件服务接口</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface IMailService {
	/**
	 * <p>
	 *     发送邮件.
	 * </p>
	 *
	 * @param mail 要发送的邮件.
	 */
	void sendMail(Mail mail);
}