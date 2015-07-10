/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cee.demo.spring.event.email.EmailService;
import cee.demo.spring.event.grouptag.GroupTagService;

/**
 * cee.demo.spring.event.keyword.KeywordService.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
@Service
public class KeywordService {
	
	@Autowired
	KeywordDAO keywordDAO;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	GroupTagService groupTagService;
	
	public void unrank(Long keywordId) {
		
		KeywordEntity keyword = keywordDAO.find(keywordId);
		keyword.unrank();
		keywordDAO.update(keyword);
		
		groupTagService.removeKeyword(keywordId);
		emailService.sendUnrankAlert(keywordId);
	}
	
}






