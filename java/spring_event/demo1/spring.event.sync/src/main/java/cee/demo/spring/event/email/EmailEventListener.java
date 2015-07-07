/**
 * 
 */
package cee.demo.spring.event.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cee.demo.spring.event.keyword.KeywordUnrankEvent;

/**
 * cee.demo.spring.event.email.EmailEventListener.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Component
public class EmailEventListener implements ApplicationListener<ApplicationEvent> {
	
	static final Logger logger = LoggerFactory.getLogger(EmailEventListener.class);

	public void onApplicationEvent(final ApplicationEvent event) {
		if (event instanceof KeywordUnrankEvent) {
			logger.info("Email Listener get Keyword Unrank Event : {}", event.getSource());
		}
	}

}
