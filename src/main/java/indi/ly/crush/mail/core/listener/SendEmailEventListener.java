package indi.ly.crush.mail.core.listener;

import indi.ly.crush.mail.core.api.IMailService;
import indi.ly.crush.mail.core.config.AsyncConfig;
import indi.ly.crush.mail.core.event.SendEmailEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <h2>发送邮件事件监听器</h2>
 *
 * @author 云上的云
 * @see SendEmailEvent
 * @see AsyncConfig
 * @see SmartApplicationListener
 * @since 1.0
 */
@Component
public class SendEmailEventListener {
	final Log logger = LogFactory.getLog(this.getClass());
	final IMailService iMultiMailServiceProxyImpl;
	
	public SendEmailEventListener(
			 @Qualifier(value = "mailServiceImpl") IMailService iMailServiceProxyImpl
//			@Qualifier(value = "multiMailServiceImpl") IMailService iMultiMailServiceProxyImpl
	) {
		this.iMultiMailServiceProxyImpl = iMailServiceProxyImpl;
	}
	
	@Async
	@Retryable(retryFor = RuntimeException.class, maxAttempts = 4, backoff = @Backoff(delay = 1000))
	@EventListener
	public void handler(@NonNull SendEmailEvent event) {
		logger.info(Thread.currentThread());
		this.iMultiMailServiceProxyImpl.sendMail(event.getMail());
	}
	
	@Recover
	public void handlerException(RuntimeException e) {
		logger.info("Sending an email failed because " + e.getMessage(), e);
	}
}
