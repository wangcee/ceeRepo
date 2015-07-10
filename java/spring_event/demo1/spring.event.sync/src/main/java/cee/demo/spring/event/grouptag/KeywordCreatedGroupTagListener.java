/**
 * 
 */
package cee.demo.spring.event.grouptag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cee.demo.spring.event.keyword.KeywordCreatedEvent;

/**
 * cee.demo.spring.event.grouptag.KeywordCreatedGroupTagListener.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Component
public class KeywordCreatedGroupTagListener implements ApplicationListener<KeywordCreatedEvent> {

	static final Logger logger = LoggerFactory.getLogger(KeywordCreatedGroupTagListener.class);
	
	public void onApplicationEvent(KeywordCreatedEvent event) {
		logger.info("Group Tag get the Keyword {}", event.getKeywordEntity().getName());
	}

}
