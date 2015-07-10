/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * cee.demo.spring.event.keyword.KeywordAsyncService.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Service
public class KeywordAsyncService implements ApplicationEventPublisherAware {
	
	static final Logger logger = LoggerFactory.getLogger(KeywordAsyncService.class);

	@Autowired
	KeywordDAO keywordDAO;

	private ApplicationEventPublisher applicationEventPublisher;
	
	public void createKeyword(String KeywordText) {
		KeywordEntity keyword = new KeywordEntity();
		keyword.setName(KeywordText);
		keywordDAO.create(keyword);
		
		logger.info("Keyword created by DAO");
		
		KeywordCreatedEvent event = new KeywordCreatedEvent(this, keyword);
		applicationEventPublisher.publishEvent(event);
		
		logger.info("Keyword created event sent");
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
}
