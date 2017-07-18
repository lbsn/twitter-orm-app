package lbsn.twitter_orm_app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tweet")
public class Tweet {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String username;
	
	public Tweet(String username){
		this.username = username;
	}
}
