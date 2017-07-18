package lbsn.twitter_orm_app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("lbsn.twitter_orm_app.repository")
public class App {
	
	public static void main(String[] args) throws Exception{
		SpringApplication app = new SpringApplication(App.class);
		app.run(args);
	}
}
