package lbsn.twitter_orm_app.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.domain.TweetEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetDaoTest {
	@Autowired
	TweetDao tweetDao;
	
	@Test
	public void testfindByKeyword(){
		String keyword = "london";
		List<TweetEntity> list = this.tweetDao.findByKeyword(keyword);
		assertNotNull("List is null", list);
		assertFalse("List is empty", list.isEmpty());
		
		// Check that only tweets with keyword "london" are retrieved
		String check = "london";
		for(TweetEntity tweet : list){
			if(tweet.getKeyword() != check){
				check = tweet.getKeyword();
			}
		}
		assertEquals(keyword, check);
	}
}
