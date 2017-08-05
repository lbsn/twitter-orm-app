package lbsn.twitter_orm_app.service;

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
		Map<String, String> tweetMap = new LinkedHashMap<String,String>();
		List<TweetEntity> tweets = this.tweetDao.findByKeyword(keyword);
		
		for(TweetEntity t : tweets){
			tweetMap.put(t.getId(), t.getText());
		}
		return tweetMap;
	}
	
	public Map<String, Integer> cluster(Map<String, String> tweetMap) throws Exception{
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		
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
