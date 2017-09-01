package lbsn.twitter_orm_app.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lbsn.twitter_orm_app.service.clustering.ClusteringService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClusteringServiceTest {
	@Autowired
	ClusteringService service;
	
	@Test
	@Ignore
	public void testGetTweetList(){
		String keyword = "london";
		Map<String, String> list = service.getTweetList(keyword);
				
		assertNotNull("List is null", list);
		assertFalse("List is empty", list.isEmpty());
	}
	
	@Test
	@Ignore
	public void testCluster() throws Exception{
		String keyword = "london";
		Map<String, String> list = service.getTweetList(keyword);
		Map<String, Integer> clusters = service.cluster(list);
		
		assertNotNull("Clusters is null", clusters);

		// Check that tweet id order is the same in both maps
		Object [] expected = list.keySet().toArray();
		Object [] actual = clusters.keySet().toArray();
		assertArrayEquals(expected, actual);
	}
}
