package indi.ly.crush.mail.core.model;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>模板邮件</h2
 *
 * @author 云上的云
 * @since 1.0
 */
public class TemplateMail
		extends Mail {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	/**
	 * <p>
	 *     模板文件路径.
	 * </p>
	 */
	private String path;
	/**
	 * <p>
	 *     模板变量名称.
	 * </p>
	 */
	private String name;
	/**
	 * <p>
	 *    模板参数.
	 * </p>
	 */
	private Map<String, String> parameters;
	private Context context;
	
	public TemplateMail(
			String to, String subject, Annex annex,
			String path, String name, Map<String, String> templateParameters
	) {
		this(null, null, new String[]{to}, null, null, null, subject, annex, path, name, templateParameters);
	}
	
	public TemplateMail(
			String from, String replyTo, String to, String[] cc, String[] bcc,
			LocalDate sentDate, String subject, Annex annex,
			String path, String name, Map<String, String> templateParameters
	) {
		this(from, replyTo, new String[]{to}, cc, bcc, sentDate, subject, annex, path, name, templateParameters);
	}
	
	public TemplateMail(
			String form, String replyTo, String[] to, String[] cc, String[] bcc,
			LocalDate sentDate, String subject, Annex annex,
			String path, String name, Map<String, String> templateParameters
	) {
		super(form, replyTo, to, cc, bcc, sentDate, subject, null, annex);
		this.path = Objects.requireNonNull(path);
		Assert.state(StringUtils.hasLength(name), "Please provide a valid name.");
		this.name = name;
		this.parameters = Objects.requireNonNull(templateParameters);
		this.context = new Context();
		this.context.setVariable(this.name, this.parameters);
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}
	
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public String toString() {
		return "TemplateMail{" + "path='" + path + '\'' + ", name='" + name + '\'' + ", parameters=" + parameters
				+ ", context=" + context + '}';
	}
}
