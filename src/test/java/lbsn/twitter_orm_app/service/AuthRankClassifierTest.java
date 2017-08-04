package lbsn.twitter_orm_app.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.domain.TweetUserEntity;
import lbsn.twitter_orm_app.repository.TweetUserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthRankClassifierTest {
	@Autowired
	private AuthRankClassifier classifier;
	@Autowired
	private TweetUserDao tweetUserDao;
	
	@Test
	public void testCreateInstances() throws Exception{
		TweetUserEntity user = this.tweetUserDao.findAll().get(0);
		this.classifier.makeInstance(user.getProfile());
		assertNotNull("Instances is null", this.classifier.getInstances());		
	}
	
	@Test
	public void testClassify() throws Exception{
		TweetUserEntity user = this.tweetUserDao.findAll().get(0);
		this.classifier.makeInstance(user.getProfile());
		boolean actual = this.classifier.classify();
		assertNotNull("Predication is null", actual);
	}
}
