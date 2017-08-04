package lbsn.twitter_orm_app.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

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
	private FilteredClassifier model;
	private Instances instances;

	public Instances getInstances() {
		return instances;
	}

	public AuthRankClassifier() throws FileNotFoundException, Exception{
		String modelPath = this.getClass().
				getResource("/models/" + this.MODEL_NAME).
				getPath();
		this.model = (FilteredClassifier) SerializationHelper.
				read(new FileInputStream(modelPath));
	}

	public void makeInstance(TwitterProfile profile){
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
		this.instances = new Instances("authRankRelation", attributes, 0);
		this.instances.setClass(this.instances.attribute("opinion_maker"));

		// Create instance and add it to dataset
		Instance instance = new DenseInstance(attributes.size());
		instances.add(instance);
		instance.setDataset(instances);
		
		// Set attributes values in instance
		// Boolean
		instance.setValue(instances.attribute("verified"), 
				String.valueOf(profile.isVerified()));
		instance.setValue(instances.attribute("profile_use_background_image"), 
				String.valueOf(profile.useBackgroundImage()));
		instance.setValue(instances.attribute("default_profile"), 
				String.valueOf(profile.getExtraData().get("default_profile")));
		instance.setValue(instances.attribute("geo_enabled"), 
				String.valueOf(profile.isGeoEnabled()));
		instance.setValue(instances.attribute("default_profile_image"), 
				String.valueOf(profile.getExtraData().get("default_profile_image")));
		instance.setValue(instances.attribute("notifications"), 
				String.valueOf(profile.isNotificationsEnabled()));
		instance.setValue(instances.attribute("is_translator"), 
				String.valueOf(profile.isTranslator()));
		instance.setValue(instances.attribute("contributors_enabled"), 
				String.valueOf(profile.isContributorsEnabled()));
		instance.setValue(instances.attribute("url_in_profile"), 
				String.valueOf(profile.getUrl() != null && !profile.getUrl().isEmpty()));
		// Numeric
		instance.setValue(instances.attribute("followers_count"), 
				profile.getFollowersCount());
		instance.setValue(instances.attribute("friends_count"), 
				profile.getFriendsCount());
		instance.setValue(instances.attribute("favourites_count"), 
				profile.getFavoritesCount());
		instance.setValue(instances.attribute("statuses_count"), 
				profile.getStatusesCount());
		
		// Description can be missing
		if(profile.getDescription() == null){
			instance.setValue(instances.attribute("description"),
					Utils.missingValue());
		}
		else{
			instance.setValue(instances.attribute("description"), 
					this.cleanDescription(profile.getDescription()));
		}
		
	}
	
	private String cleanDescription(String description){
		TweetCleanerConfiguration tcc = new TweetCleanerConfiguration.
				Builder().
				punctuation(CleanOptions.PUNCTUATION.KEEP_APOSTROPHE).
				build();
		TweetCleaner tc = new TweetCleaner(tcc);
		return tc.clean(description);
				
	}
	
	public boolean classify() throws Exception{
		double pred = this.model.classifyInstance(this.instances.instance(0));
		String classValue = this.instances.classAttribute().value((int) pred);
		return (classValue == "opinion_maker") ? true : false;
	}
}
