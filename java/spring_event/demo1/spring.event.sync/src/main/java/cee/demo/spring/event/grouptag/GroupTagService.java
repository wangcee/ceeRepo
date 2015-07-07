/**
 * 
 */
package cee.demo.spring.event.grouptag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * cee.demo.spring.event.grouptag.GroupTagService.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Service
public class GroupTagService {
	
	static final Logger logger = LoggerFactory.getLogger(GroupTagService.class);

	public void removeKeyword(Long keywordId) {
		logger.info("remvoe keyword {} from tags", keywordId);
	}
	
}
