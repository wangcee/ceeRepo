/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.springframework.context.ApplicationEvent;

/**
 * cee.demo.spring.event.keyword.KeywordUnrankEvent.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
public class KeywordUnrankEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6622777026840909769L;

	private Long keywordId;
	
	public KeywordUnrankEvent(Object source) {
		super(source);
	}

	public KeywordUnrankEvent(Object source, Long keywordId) {
		super(source);
		this.keywordId = keywordId;
	}

	public Long getKeywordId() {
		return keywordId;
	}
	
}
