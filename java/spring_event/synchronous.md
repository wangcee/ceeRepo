Spring 事件机制 - Synchronous
----------

##### 领域驱动设计 Domain Driven Design
- DDD是一套综合软件系统分析和设计的面向对象建模方法
- 领域模型准确反映了业务语言，而传统Spring+Hibernate等编程模型只关心数据
- DDD让你首先考虑的是业务语言，而不是数据
- 充血模型
- DDD落地实现离不开in-memory缓存、 CQRS、 DCI、 EDA或Event Source几大大相关领域

##### OCP原则 (Open-Closed Principle)
*Software entities should be open for extension,but closed for modification.*<br/>
一个软件应该对扩展开放，对修改关闭

- 对扩展开放 Open for extension
- 对更改封闭 Closed for modification

##### SRP原则 (Single Responsibility Principle)
一个类或模块应该有一个且只有一个理由去改变。

> Talk is cheap. Show me the code.  
> 				 ——Linus Torvalds

##以前我们是这么做的

**Case**：客户要unrank一个keyword

```Java
//创建一个KeywordService类，处理unrank功能
@Service
public class KeywordService {
	
	@Autowired
	KeywordDAO keywordDAO;
	
	public void unrank(Long keywordId) {
		
		KeywordEntity keyword = keywordDAO.find(keywordId);
		keyword.unrank();
		keywordDAO.update(keyword);
	}
}
```

然后，客户说unrank keyword时要发邮件，并要从tags里删除关系，那我们就再把GroupTagService和EmailService引入进KeywordService，并调用相关方法

```Java
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
		emailService.sendUnrankAlert(keyword);
	}
}
```

如果客户再添加新的需求，我们还要回来修改这个类。 或者哪一天我们做什么功能的时候，会忘了修改这个类，而产生bug

##Spring 同步事件机制

####Create Event Class

Event 必须继承 *org.springframework.context.ApplicationEvent*

```Java
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
```

####Publish Event
需要实现接口 *org.springframework.context.ApplicationEventPublisherAware*


```Java
@Service
public class KeywordSyncService implements ApplicationEventPublisherAware {
	
	@Autowired
	KeywordDAO keywordDAO;

	//ApplicationEventPublisherAware interface required
	private ApplicationEventPublisher applicationEventPublisher;
	
	public void unrank(Long keywordId) {
		KeywordEntity keyword = keywordDAO.find(keywordId);
		keyword.unrank();
		keywordDAO.update(keyword);
		
		//Create Event
		KeywordUnrankEvent event = new KeywordUnrankEvent(this, keywordId);
		
		//Publish Event
		applicationEventPublisher.publishEvent(event);
	}
	
	//ApplicationEventPublisherAware interface required
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
```

####Listen Event

Event Listener 需要实现 *org.springframework.context.ApplicationListener* 


```Java
//需要添加@Component注释
@Component
public class EmailEventListener implements ApplicationListener<ApplicationEvent> {
	
	static final Logger logger = LoggerFactory.getLogger(EmailEventListener.class);

	public void onApplicationEvent(final ApplicationEvent event) {
		if (event instanceof KeywordUnrankEvent) {
			logger.info("Email Listener get Keyword Unrank Event : {}", event.getSource());
		}
	}

}
```

```Java
//ApplicationListener支持泛型
@Component
public class UnrankKeywordListener implements ApplicationListener<KeywordUnrankEvent> {

	static final Logger logger = LoggerFactory.getLogger(UnrankKeywordListener.class);
	
	public void onApplicationEvent(KeywordUnrankEvent event) {
		logger.info("Group Tag Unrank Listener : {}", event.getKeywordId());
	}

}
```

执行代码就可以看到结果了。以后如果客户希望在unrank keyword的时候再做什么，我们可以不用修改keywordService类，和其它Listener，只需要再写一个Listener就可以了，适应了OCP原则
<br/>
<br/>
Spirng 同步事件机制，是通过反射找到所有监听事件的Listener并执行，后面再讲讲异步事件机制。