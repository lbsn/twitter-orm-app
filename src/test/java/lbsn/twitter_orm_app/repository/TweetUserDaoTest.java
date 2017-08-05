package lbsn.twitter_orm_app.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.domain.TweetUserEntity;
import lbsn.twitter_orm_app.service.TweetSearch;
import twitter4j.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class TweetUserDaoTest {
	@Autowired
	TweetSearch tsearch;
	@Autowired
	TweetUserDao tUserDao;
	
	@Test
	@Ignore
	public void testGetUser() {
//		TweetUserEntity user = new TweetUserEntity(tsearch.searchProfile("2289370b"));
//		tUserDao.save(user);
//		assertNotNull(user);
	}
	
	@Test
	public void testFindOne(){
		TweetUserEntity user = tUserDao.findOne(Long.parseLong("875757581498798081"));
		assertNotNull(user);
	}
	
	@Test
	public void testFindAll(){
		List<TweetUserEntity> users = tUserDao.findAll();
		assertTrue(users.size() != 0);
	}
	
	@Test
	@Ignore
	public void testGetProfile(){
//		TweetUserEntity user = tUserDao.findOne(Long.parseLong("875757581498798081"));
//		User profile = user.getProfile();
//		assertNotNull("Profile is null", profile);
//		assertTrue("Screen name isn't correct", profile.getScreenName().equals("2289370b"));
//		Map<String,Object> extraData = profile.getExtraData();
//		assertFalse("Extra data is empty", extraData.isEmpty());
//		assertNotNull("Entities is null", extraData.get("entities"));
	}

}
