package cee.demo.spring.event.sync;

import cee.demo.spring.event.keyword.KeywordService;
import cee.demo.spring.event.keyword.KeywordSyncService;
import cee.demo.spring.utils.SpringBeanFactory;

/**
 * Hello world!
 * 
 */
public class App {
	
	public static void main(String[] args) {
		
		KeywordService keywordService = SpringBeanFactory.getBean("keywordService");
		keywordService.unrank(12345l);
		
//		KeywordSyncService keywordSyncService = SpringBeanFactory.getBean("keywordSyncService");
//		keywordSyncService.unrank(998877l);
	}
	
}
