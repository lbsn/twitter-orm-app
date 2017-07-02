package lbsn.twitter_orm_app.tweetsearch;

import java.io.Serializable;
import java.util.ArrayList;

import twitter4j.Status;

public class SerializableTweet implements Serializable{
	ArrayList<Status> statusList;
	
	public SerializableTweet(ArrayList<Status> statusList){
		this.statusList = statusList;
	}
	
	public ArrayList<Status> getList(){
		return this.statusList;
	}
}
