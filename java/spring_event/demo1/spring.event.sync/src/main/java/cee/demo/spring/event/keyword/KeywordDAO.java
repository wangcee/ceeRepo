/**
 * 
 */
package cee.demo.spring.event.keyword;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 
 * cee.demo.spring.event.keyword.KeywordDAO.java
 * 
 * @author wangcee
 * 
 * @version $Revision:$ $Author:$
 */
@Repository
public class KeywordDAO {
	
	static final Logger logger = LoggerFactory.getLogger(KeywordDAO.class);

	public KeywordEntity find(Long id) {
		KeywordEntity entity = new KeywordEntity();
		entity.setId(id);
		return entity;
	}
	
	public boolean update(KeywordEntity keyword) {
		logger.info("update DB to unrank {}", keyword.getId());
		return true;
	}
	
	public boolean create(KeywordEntity keyword) {
		keyword.setId(998877l);
		logger.info("insert keyword " + keyword.getId());
		return true;
	}
}
