package lbsn.twitter_orm_app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.TweetStream;

@EnableAsync
@EnableMongoRepositories
@SpringBootApplication
public class App {
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(30);
		return taskExecutor;
	}
	
	public static void main(String[] args) throws Exception{
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
	
	}
	
}
