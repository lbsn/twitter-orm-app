package lbsn.twitter_orm_app.service.classification;

import org.springframework.stereotype.Service;

import uk.ac.wlv.sentistrength.SentiStrength;

@Service
public class SentimentClassifier extends SentiStrength{
	private String ss_data;
	
	public SentimentClassifier(){
		this.ss_data = getClass().getResource("/data/SentStrength_Data/").getPath();
		String ssInit [] = {"sentidata", this.ss_data, "trinary"};
		this.initialise(ssInit);
	}
	
	public String score(String text){
		return this.computeSentimentScores(text).split(" ")[2];
	}
}
