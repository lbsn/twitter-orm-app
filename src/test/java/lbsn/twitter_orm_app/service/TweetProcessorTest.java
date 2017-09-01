package lbsn.twitter_orm_app.service;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import twitter4j.Status;

@RunWith(SpringRunner.class)
public class TweetProcessorTest {
	@Mock(name="taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;
	
	private BlockingQueue<Status> queue = new ArrayBlockingQueue<>(20);
	
	@Before
	public void setUp(){
		String text = "RT @IainDale: @mcashmanCBE I know you think all Brexit supporters are ignorant, but answer me this. If the EU has a FTA with Cana…";
		
		// Add some tweets to the queue
		for(int i = 0; i < 20; i++){
			Status status = new StatusExample(text);
			this.queue.offer(status);
		}
		this.taskExecutor.setCorePoolSize(5);
		this.taskExecutor.setMaxPoolSize(10);
		this.taskExecutor.setQueueCapacity(30);
		this.taskExecutor.setWaitForTasksToCompleteOnShutdown(true);		
	}
	
	@Test
	public void testMultiThread(){
		for (int i = 0; i < this.taskExecutor.getMaxPoolSize(); i++) {
			this.taskExecutor.execute(
					new TweetProcessor(this.queue)
					);
		}
		
		System.out.println(this.taskExecutor.getCorePoolSize());
	}
	
	
}
