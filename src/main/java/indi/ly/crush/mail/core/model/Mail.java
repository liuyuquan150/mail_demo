package indi.ly.crush.mail.core.model;

import org.springframework.mail.MailMessage;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * <h2>邮件</h2>
 * <p>
 *     {@link #from}、{@link #to}、{@link #subject}、{@link #text} 不允许为 null.
 * </p>
 *
 * @author 云上的云
 * @see MailMessage
 * @since 1.0
 */
public class Mail
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     邮件发送人.
	 * </p>
	 */
	private String from;
	/**
	 * <p>
	 *     邮件回复地址.
	 * </p>
	 */
	private String replyTo;
	/**
	 * <p>
	 *     邮件接收人(<em>可以是多个</em>).
	 * </p>
	 */
	private String[] to;
	/**
	 * 邮件接收人(<em>可以是多个</em>)——————抄送.
	 */
	private String[] cc;
	/**
	 * 邮件接收人(<em>可以是多个</em>)——————盲抄送(<em>密送</em>).
	 */
	private String[] bcc;
	/**
	 * <p>
	 *     邮件的发送时间.
	 * </p>
	 */
	private LocalDate sentDate;
	/**
	 * <p>
	 *     邮件主题.
	 * </p>
	 */
	private String subject;
	/**
	 * <p>
	 *     邮件文本内容.
	 * </p>
	 */
	private String text;
	/**
	 * <p>
	 *     邮件附件.
	 * </p>
	 */
	private Annex annex;
	
	public Mail(
			String from, String replyTo, String to, String[] cc, String[] bcc,
			LocalDate sentDate, String subject, String text, Annex annex
	) {
		this(from, replyTo, new String[]{to}, cc, bcc, sentDate, subject, text, annex);
	}
	
	public Mail(
			String from, String replyTo, String[] to, String[] cc, String[] bcc,
			LocalDate sentDate, String subject, String text, Annex annex
	) {
		this.from = from;
		this.replyTo = replyTo;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.sentDate = sentDate;
		this.subject = subject;
		this.text = text;
		this.annex = annex;
	}
	
	public String getFrom() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getReplyTo() {
		return replyTo;
	}
	
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	public String[] getTo() {
		return to;
	}
	
	public void setTo(String[] to) {
		this.to = to;
	}
	
	public String[] getCc() {
		return cc;
	}
	
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	
	public String[] getBcc() {
		return bcc;
	}
	
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	
	public LocalDate getSentDate() {
		return sentDate;
	}
	
	public void setSentDate(LocalDate sentDate) {
		this.sentDate = sentDate;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Annex getAnnex() {
		return annex;
	}
	
	public void setAnnex(Annex annex) {
		this.annex = annex;
	}
	
	@Override
	public String toString() {
		return "Mail{" + "from='" + from + '\'' + ", replyTo='" + replyTo + '\'' + ", to=" + Arrays.toString(to)
				+ ", cc=" + Arrays.toString(cc) + ", bcc=" + Arrays.toString(bcc) + ", sentDate=" + sentDate
				+ ", subject='" + subject + '\'' + ", text='" + text + '\'' + ", annex=" + annex + '}';
	}
}
