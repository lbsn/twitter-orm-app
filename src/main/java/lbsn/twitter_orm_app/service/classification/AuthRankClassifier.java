package lbsn.twitter_orm_app.service.classification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import lbsn.twitter_orm_app.service.cleaning.CleanOptions;
import lbsn.twitter_orm_app.service.cleaning.TweetCleaner;
import lbsn.twitter_orm_app.service.cleaning.TweetCleanerConfiguration;
import twitter4j.User;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Utils;

@Service
public class AuthRankClassifier {
	private final String MODEL_NAME = "authRank.model";
	private final FilteredClassifier model;
	
	public AuthRankClassifier() throws FileNotFoundException, Exception{
		String modelPath = this.getClass().
				getResource("/models/" + this.MODEL_NAME).
				getPath();
		this.model = (FilteredClassifier) SerializationHelper.
				read(new FileInputStream(modelPath));
	}

	public Instances makeDataset(){
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		// Boolean
		attributes.add(new Attribute("verified", Arrays.asList("true","false")));
		attributes.add(new Attribute("profile_use_background_image", Arrays.asList("true","false")));
		attributes.add(new Attribute("default_profile", Arrays.asList("true","false")));
		attributes.add(new Attribute("geo_enabled", Arrays.asList("true","false")));
		attributes.add(new Attribute("default_profile_image", Arrays.asList("true","false")));
		attributes.add(new Attribute("notifications", Arrays.asList("true","false")));
		attributes.add(new Attribute("is_translator", Arrays.asList("true","false")));
		attributes.add(new Attribute("contributors_enabled", Arrays.asList("true","false")));
		attributes.add(new Attribute("url_in_profile", Arrays.asList("true","false")));
		
		// Numeric
		attributes.add(new Attribute("followers_count"));
		attributes.add(new Attribute("friends_count"));
		attributes.add(new Attribute("favourites_count"));
		attributes.add(new Attribute("statuses_count"));
		
		// String
		attributes.add(new Attribute("description", true));
		
		// Class
		attributes.add(new Attribute("opinion_maker", Arrays.asList("non_opinion_maker","opinion_maker")));
		
		// Creates Instances object
		Instances dataset = new Instances("authRankRelation", attributes, 0);
		dataset.setClass(dataset.attribute("opinion_maker"));

		return dataset;		
	}
	
	public Instances addInstance(Instances dataset, User user){
		// Empty dataset
		dataset.delete();
		
		// Create instance and add it to dataset
		Instance instance = new DenseInstance(dataset.numAttributes());
		instance.setDataset(dataset);
		dataset.add(instance);

		// Set attributes values in instance
		// Boolean
		instance.setValue(dataset.attribute("verified"), 
				String.valueOf(user.isVerified()));
		instance.setValue(dataset.attribute("profile_use_background_image"), 
				String.valueOf(user.isProfileUseBackgroundImage()));
		instance.setValue(dataset.attribute("default_profile"), 
				String.valueOf(user.isDefaultProfile()));
		instance.setValue(dataset.attribute("geo_enabled"), 
				String.valueOf(user.isGeoEnabled()));
		instance.setValue(dataset.attribute("default_profile_image"), 
				String.valueOf(user.isDefaultProfileImage()));
		instance.setValue(dataset.attribute("notifications"), 
				String.valueOf(false));
		instance.setValue(dataset.attribute("is_translator"), 
				String.valueOf(user.isTranslator()));
		instance.setValue(dataset.attribute("contributors_enabled"), 
				String.valueOf(user.isContributorsEnabled()));
		instance.setValue(dataset.attribute("url_in_profile"), 
				String.valueOf(user.getDescriptionURLEntities() != null && user.getDescriptionURLEntities().length != 0));
		// Numeric
		instance.setValue(dataset.attribute("followers_count"), 
				user.getFollowersCount());
		instance.setValue(dataset.attribute("friends_count"), 
				user.getFriendsCount());
		instance.setValue(dataset.attribute("favourites_count"), 
				user.getFavouritesCount());
		instance.setValue(dataset.attribute("statuses_count"), 
				user.getStatusesCount());

		// Description can be missing
		if(user.getDescription() == null){
			instance.setValue(dataset.attribute("description"),
					Utils.missingValue());
		}
		else{
			instance.setValue(dataset.attribute("description"), 
					this.cleanDescription(user.getDescription()));
		}
		
		return dataset;
	}
	
	private String cleanDescription(String description){
		TweetCleanerConfiguration tcc = new TweetCleanerConfiguration.
				Builder().
				punctuation(CleanOptions.PUNCTUATION.KEEP_APOSTROPHE).
				build();
		TweetCleaner tc = new TweetCleaner(tcc);
		return tc.clean(description);
				
	}
	
	public boolean classify(Instances dataset) throws Exception{
		double pred = this.model.classifyInstance(dataset.instance(0));
		String classValue = dataset.classAttribute().value((int) pred);
		return (classValue == "opinion_maker") ? true : false;
	}
}
