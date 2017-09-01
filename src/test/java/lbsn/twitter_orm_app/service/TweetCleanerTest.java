package lbsn.twitter_orm_app.service;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.service.cleaning.CleanOptions;
import lbsn.twitter_orm_app.service.cleaning.TweetCleaner;
import lbsn.twitter_orm_app.service.cleaning.TweetCleanerConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class TweetCleanerTest {
	
	@Test
	public void testChangeCase(){
		String test = "THIS IS a TesT StrInG";
		String expected = "this is a test string";
		TweetCleanerConfiguration tcc = new TweetCleanerConfiguration.
				Builder().
				changeCase(CleanOptions.CHANGE_CASE.LOWER).
				build();
		TweetCleaner tc = new TweetCleaner(tcc);
		assertEquals(tc.clean(test), expected);
	}
	
	@Test
	public void testStemming(){
		String test = "THIS IS a TesT StrInG";
		TweetCleanerConfiguration tcc = new TweetCleanerConfiguration.
				Builder().
				stemming(CleanOptions.STEMMING.TRUE).
				build();
		TweetCleaner tc = new TweetCleaner(tcc);
		String stemmed = tc.clean(test);
		System.out.println("----------" + stemmed);
		assertNotNull(stemmed);		
	}
}
