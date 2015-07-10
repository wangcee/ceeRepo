/**
 * 
 */
package cee.demo.spring.event.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cee.demo.spring.event.keyword.KeywordCreatedEvent;

/**
 * cee.demo.spring.event.email.KeywordCreatedEmailListener.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Component
public class KeywordCreatedEmailListener implements ApplicationListener<KeywordCreatedEvent> {

	static final Logger logger = LoggerFactory.getLogger(KeywordCreatedEmailListener.class);

	@Async
	public void onApplicationEvent(KeywordCreatedEvent event) {
		logger.info("Start to send email ... ");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("A New Keyword {} Created Email sent out !", event.getKeywordEntity().getName());
	}
	
}
