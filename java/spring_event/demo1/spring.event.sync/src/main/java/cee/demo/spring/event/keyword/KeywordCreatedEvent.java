/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * cee.demo.spring.event.keyword.KeywordCreatedEvent.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
public class KeywordCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6382724724966241822L;
	
	private KeywordEntity keywordEntity;
	
	public KeywordCreatedEvent(Object source) {
		super(source);
	}

	public KeywordCreatedEvent(Object source, KeywordEntity keywordEntity) {
		super(source);
		this.keywordEntity = keywordEntity;
	}

	public KeywordEntity getKeywordEntity() {
		return keywordEntity;
	}

	public void setKeywordEntity(KeywordEntity keywordEntity) {
		this.keywordEntity = keywordEntity;
	}
	
}
