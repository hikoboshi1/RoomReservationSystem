package jp.co.dcf.rrs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Bean
	public MessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    // Set a property file on the class path.
	    messageSource.setBasename("messages");
	    // Set UTF-8 as a default encoding code
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
}