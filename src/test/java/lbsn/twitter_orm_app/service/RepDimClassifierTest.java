package lbsn.twitter_orm_app.service;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class RepDimClassifierTest {
	@Autowired
	private RepDimClassifier classifier;
	private String tweetToTest = "Berlin street artist group cleverly undo swastika graffiti https://t.co/JEnPhzdn3s";
	
	@Test
	public void testClassify() throws Exception{
		this.classifier.makeInstance(tweetToTest);
		String dimension = this.classifier.classify();
		assertNotNull("Dimension is null", dimension);
		assertTrue("Dimension is empty", dimension.length() != 0);
	}
}
