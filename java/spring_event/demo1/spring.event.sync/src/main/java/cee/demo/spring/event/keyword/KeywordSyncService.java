/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * cee.demo.spring.event.keyword.KeywordSyncService.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Service
public class KeywordSyncService implements ApplicationEventPublisherAware {
	
	@Autowired
	KeywordDAO keywordDAO;

	private ApplicationEventPublisher applicationEventPublisher;
	
	public void unrank(Long keywordId) {
		KeywordEntity keyword = keywordDAO.find(keywordId);
		keyword.unrank();
		keywordDAO.update(keyword);
		
		KeywordUnrankEvent event = new KeywordUnrankEvent(this, keywordId);
		applicationEventPublisher.publishEvent(event);
	}
	
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
