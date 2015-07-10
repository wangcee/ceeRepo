package cee.demo.spring.event.sync;

import cee.demo.spring.event.keyword.KeywordAsyncService;
import cee.demo.spring.event.keyword.KeywordService;
import cee.demo.spring.event.keyword.KeywordSyncService;
import cee.demo.spring.utils.SpringBeanFactory;

/**
 * Hello world!
 * 
 */
public class App {
	
	public static void main(String[] args) {
		
		//old
//		KeywordService keywordService = SpringBeanFactory.getBean("keywordService");
//		keywordService.unrank(12345l);
		
		//Sync
//		KeywordSyncService keywordSyncService = SpringBeanFactory.getBean("keywordSyncService");
//		keywordSyncService.unrank(998877l);
		
		//Async
		KeywordAsyncService keywordAsyncService = SpringBeanFactory.getBean("keywordAsyncService");
		keywordAsyncService.createKeyword("Nike Shoes");
		keywordAsyncService.createKeyword("Apple iPad");
	}
	
}
