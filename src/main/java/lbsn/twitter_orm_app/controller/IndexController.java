package lbsn.twitter_orm_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import lbsn.twitter_orm_app.repository.TweetDao;

@Controller
public class IndexController{
	@Autowired
	private TweetDao tweetDao;
	
	@RequestMapping(value={"/", "/home"})
	public String index(){
		return "index";
	}
	
//	@GetMapping("/search")
//	public String tweetSearch(@ModelAttribute TweetSearch tweetSearch){
//		return "search";
//	}
//	
//	@PostMapping("/search")
//	public ModelAndView submit(@RequestParam(value="keyword") String keyword,
//			TweetSearch tweetSearch) throws TwitterException{
//		ArrayList<Status> tweets = tweetSearch.searchTweets();
//		for(Status s : tweets){
//			MyTweet t = new MyTweet();
//			t.setText(s.getText());
//			tweetDao.save(t);
//		}
//		ModelAndView model = new ModelAndView("tweet", "tweets", tweets);
//		return model;
//	}
//	
//	@RequestMapping("/test")
//	public ModelAndView score(SentimentClassifier sc){
//		String text = "I'm very happy to be here!";
//		String sentiment = sc.score(text);
//		return new ModelAndView("test", "model", sentiment);
//	}
}
	
