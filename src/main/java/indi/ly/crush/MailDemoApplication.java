package indi.ly.crush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <h2>主启动类</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
@EnableAsync
@EnableRetry
@SpringBootApplication(scanBasePackages = "indi.ly.crush")
public class MailDemoApplication {
	public static void main(String[] args) {
		SpringApplication mailDemoApplication = new SpringApplication(MailDemoApplication.class);
		mailDemoApplication.run(args);
	}
}
