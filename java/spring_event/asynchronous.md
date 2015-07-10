Spring 事件机制 - Asynchronous
----------

Spring 3.X新增注解 ***@Async***，可以标记方法异步运行。 异步方法的返回值可以使 *void* 或者 *java.util.concurrent.Future*

##### Spring 配置文件

配置文件需要添加 

    <!-- to support async method -->
    <task:annotation-driven executor="taskExecutor" scheduler="taskScheduler"/>
    <task:executor id="taskExecutor" pool-size="10"/>
    <task:scheduler id="taskScheduler" pool-size="10"/>


##### 异步事件Listener

```Java
@Component
public class KeywordCreatedEmailListener implements ApplicationListener<KeywordCreatedEvent> {

	static final Logger logger = LoggerFactory.getLogger(KeywordCreatedEmailListener.class);

	//这里的@Async注解让方法可以异步执行
	@Async
	public void onApplicationEvent(KeywordCreatedEvent event) {
		logger.info("Start to send email ... ");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("A New Keyword {} Created Email sent out !", event.getKeywordEntity().getName());
	}
	
}
```
##### Service类以及Event类都跟同步事件没有区别

```Java
@Service
public class KeywordAsyncService implements ApplicationEventPublisherAware {
	
	static final Logger logger = LoggerFactory.getLogger(KeywordAsyncService.class);

	@Autowired
	KeywordDAO keywordDAO;

	private ApplicationEventPublisher applicationEventPublisher;
	
	public void createKeyword(String KeywordText) {
		KeywordEntity keyword = new KeywordEntity();
		keyword.setName(KeywordText);
		keywordDAO.create(keyword);
		
		logger.info("Keyword created by DAO");
		
		KeywordCreatedEvent event = new KeywordCreatedEvent(this, keyword);
		applicationEventPublisher.publishEvent(event);
		
		logger.info("Keyword created event sent");
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
}
```