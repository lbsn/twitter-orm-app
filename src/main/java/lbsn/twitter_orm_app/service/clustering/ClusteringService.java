package lbsn.twitter_orm_app.service.clustering;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;

@Service
public class ClusteringService {
	@Autowired
	TweetDao tweetDao;
	@Autowired
	KMeansClusterer clusterer;
	
	public Map<String, String> getTweetList(String keyword){
		// A Map to store retrieved tweet as (tweet-id, tweet-text) pairs
		Map<String, String> tweetMap = new LinkedHashMap<String,String>();
		List<TweetEntity> tweets = this.tweetDao.findByKeyword(keyword);
		
		for(TweetEntity t : tweets){
			tweetMap.put(t.getId(), t.getText());
		}
		return tweetMap;
	}
	
	public Map<String, Integer> cluster(Map<String, String> tweetMap) throws Exception{
		// A Map to store the generated clusters as (tweet-id, cluster-id) pairs
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		// Data to be passed to the cluster function is values from tweetMap (tweets texts)
		List<String> dataToLoad = new ArrayList<String>(tweetMap.values());
		this.clusterer.loadData(dataToLoad);
		this.clusterer.buildClusterer();
		int [] assignements = this.clusterer.getAssignments();
		
		int i = 0;
		for(Entry<String, String> entry : tweetMap.entrySet()){
			result.put(entry.getKey(), assignements [i]);
			i++;
		}
		return result;
		
	}
}
