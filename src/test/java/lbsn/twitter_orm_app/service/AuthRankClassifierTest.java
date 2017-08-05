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
import weka.core.Instances;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthRankClassifierTest {
	@Autowired
	private AuthRankClassifier classifier;
	@Autowired
	private TweetUserDao tweetUserDao;
	
	@Test
	public void testCreateInstances() throws Exception{
		Instances dataset = this.classifier.makeDataset();
		assertNotNull("Dataset is null", dataset);		
	}
	
	@Test
	public void testClassify() throws Exception{
		TweetUserEntity user = this.tweetUserDao.findAll().get(0);
		Instances dataset = this.classifier.makeDataset();
		dataset = this.classifier.addInstance(dataset, user.getProfile());
		boolean actual = this.classifier.classify(dataset);
		assertNotNull("Predication is null", actual);
	}
}
