package lbsn.twitter_orm_app.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lbsn.twitter_orm_app.domain.AjaxResponse;
import lbsn.twitter_orm_app.domain.SearchCriteria;
import lbsn.twitter_orm_app.domain.TweetEntity;
import lbsn.twitter_orm_app.repository.TweetDao;
import lbsn.twitter_orm_app.service.ClusteringService;
import lbsn.twitter_orm_app.service.TweetStream;

@RestController
public class ApiController {
	@Autowired
	private TweetStream tweetStream;
	@Autowired
	private TweetDao tweetDao;
	@Autowired
	private ClusteringService clusteringService;
	
	@PostMapping("/api/search")	
	public ResponseEntity<String> submit(@Valid @RequestBody SearchCriteria search) throws Exception{
		System.out.println("-------------------/SEARCH " + search.getKeyword());
		tweetStream.setKeyword(search.getKeyword());
		tweetStream.startStreaming();
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@PostMapping(value="/api/update", produces="application/json;charset='ISO-8859-1'")
	public AjaxResponse update(@Valid @RequestBody SearchCriteria search){
		System.out.println("-------------------/UPDATE " + search.getKeyword());
		AjaxResponse result = new AjaxResponse();
		List<TweetEntity> tweet = this.tweetDao.findByKeyword(search.getKeyword());
		result.setKeyword(search.getKeyword());
		result.setTweets(tweet);
		return result;
	}
	
	@PostMapping(value="/api/cluster")
	public AjaxResponse cluster(@Valid @RequestBody SearchCriteria search) throws Exception{
		System.out.println("-------------------/CLUSTER " + search.getKeyword());
		AjaxResponse result = new AjaxResponse();
		Map<String, Integer> clusters = this.clusteringService.cluster(
				this.clusteringService.getTweetList(search.getKeyword())
				);
		
		return result;
	}
}
