/**
 * 
 */
package cee.demo.spring.event.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * cee.demo.spring.event.email.EmailService.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Service
public class EmailService {
	
	static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public void sendUnrankAlert(Long keywordId) {
		logger.info("Email \"unrank keyword {}\"", keywordId);
	}
}
