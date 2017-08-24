package lbsn.twitter_orm_app.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lbsn.twitter_orm_app.domain.AjaxResponse;
import lbsn.twitter_orm_app.domain.ClusterEntry;
import lbsn.twitter_orm_app.domain.SearchCriteria;
import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.domain.TweetUserEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.repository.TweetUserDao;
import lbsn.twitter_orm_app.service.ClusteringService;
import lbsn.twitter_orm_app.service.TweetStream;
import twitter4j.User;

@RestController
public class ApiController {
	@Autowired
	private TweetStream tweetStream;
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private TweetUserDao tweetUserDao;
	@Autowired
	private ClusteringService clusteringService;
	
	@PostMapping("/api/search")	
	public ResponseEntity<String> submit(@Valid @RequestBody SearchCriteria search) throws Exception{
		System.out.println("-------------------/SEARCH " + search.getKeyword());
		tweetStream.setKeyword(search.getKeyword());
//		tweetStream.startStreaming();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@PostMapping(value="/api/update", produces="application/json;charset='ISO-8859-1'")
	public AjaxResponse update(@Valid @RequestBody SearchCriteria search){
		System.out.println("-------------------/UPDATE " + search.getKeyword());
		// Object to store ajax response
		AjaxResponse result = new AjaxResponse();

		// Get list of tweets form db
		List<TweetEntity> tweets = this.tweetDao.findByKeyword(search.getKeyword());
		
		// Get list of authors ids
		List<Long> authorsIds = new ArrayList<Long>();
		// Get list of authors
		List<TweetUserEntity> authors = new ArrayList<TweetUserEntity>();
		for(TweetEntity t : tweets){
			if(!authorsIds.contains(t.getUserId())){
				authorsIds.add(t.getUserId());
				authors.add(tweetUserDao.findOne(t.getUserId()));
			}			
		}
		
		result.setKeyword(search.getKeyword());
		result.setTweets(tweets);
		result.setUsers(authors);
		return result;
	}
	
	@PostMapping(value="/api/cluster")
	public List<ClusterEntry> cluster(@Valid @RequestBody SearchCriteria search) throws Exception{
		System.out.println("-------------------/CLUSTER " + search.getKeyword());
		List<ClusterEntry> result = new ArrayList<ClusterEntry>();
		Map<String, Integer> clusters = this.clusteringService.cluster(
				this.clusteringService.getTweetList(search.getKeyword())
				);
		for(Entry e : clusters.entrySet()){
			ClusterEntry entry = new ClusterEntry();
			entry.setId((String) e.getKey());
			entry.setCluster((Integer) e.getValue());
			result.add(entry);
		}
		return result;
	}
}
