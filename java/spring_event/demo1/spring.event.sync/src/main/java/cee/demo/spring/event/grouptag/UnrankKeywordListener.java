/**
 * 
 */
package cee.demo.spring.event.grouptag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cee.demo.spring.event.keyword.KeywordUnrankEvent;

/**
 * 
 * cee.demo.spring.event.grouptag.UnrankKeywordListener.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Component
public class UnrankKeywordListener implements ApplicationListener<KeywordUnrankEvent> {

	static final Logger logger = LoggerFactory.getLogger(UnrankKeywordListener.class);
	
	public void onApplicationEvent(KeywordUnrankEvent event) {
		logger.info("Group Tag Unrank Listener : {}", event.getKeywordId());
	}

}
