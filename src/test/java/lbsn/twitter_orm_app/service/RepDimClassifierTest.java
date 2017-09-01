package lbsn.twitter_orm_app.service;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.service.classification.RepDimClassifier;
import twitter4j.Status;
import weka.core.Instances;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepDimClassifierTest {
	@Autowired
	private RepDimClassifier classifier;
	private BlockingQueue<Status> queue = new ArrayBlockingQueue<>(20);
	
	String text = "RT @IainDale: @mcashmanCBE I know you think all Brexit supporters are ignorant, but answer me this. If the EU has a FTA with Cana…";
	
	@Before
	public void setUp() throws FileNotFoundException, Exception{
//		this.classifier = new RepDimClassifier();
		for(int i = 0; i < 10; i++){
			Status status = new StatusExample(text);
			this.queue.offer(status);
		}
	}
	
	@Test
	public void testCreateInstances() throws Exception{
		Instances dataset = this.classifier.makeDataset();
		assertNotNull("Dataset is null", dataset);		
	}
	
	@Test
	public void testClassify() throws Exception{
		Instances dataset = this.classifier.makeDataset();
		dataset = this.classifier.addInstance(dataset, this.text);
		String dimension = this.classifier.classify(dataset);
		assertNotNull("Dimension is null", dimension);
		assertTrue("Dimension is empty", dimension.length() != 0);
	}
	
	@Test
	public void testQueue(){
		
	}
		
}
