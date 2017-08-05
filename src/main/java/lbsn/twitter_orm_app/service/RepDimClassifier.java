package lbsn.twitter_orm_app.service;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

@Service
public class RepDimClassifier {
	private final String MODEL_NAME = "repDim.model";
	private final FilteredClassifier model;
	
	public RepDimClassifier() throws FileNotFoundException, Exception{
		String modelPath = this.getClass().
				getResource("/models/" + this.MODEL_NAME).
				getPath();
		this.model = (FilteredClassifier) SerializationHelper.
				read(new FileInputStream(modelPath));
	}
	
	public Instances makeDataset(){
		// Creates attributes
		Attribute text = new Attribute("text", true);

		// Values for dimension
		ArrayList<String> values = new ArrayList<String>();
		values.add("Products & Services");
		values.add("Performance");
		values.add("Innovation");
		values.add("Citizenship");
		values.add("Leadership");
		values.add("Workplace");
		values.add("Governance");
		Attribute dimension = new Attribute("dimension", values);

		// Adds attributes to attribute list
		ArrayList<Attribute> attr = new ArrayList<Attribute>();
		attr.add(text);
		attr.add(dimension);

		// Creates Instances object
		Instances dataset = new Instances("repDimRelation", attr, 0);
		dataset.setClass(dataset.attribute("dimension"));

		return dataset;
	}
	
	public Instances addInstance(Instances dataset, String tweetText){
		// Delete existing instances
		dataset.delete();
		
		// Add new instance
		Instance instance = new DenseInstance(2);
		instance.setDataset(dataset);
		instance.setValue(dataset.attribute("text"), this.cleanText(tweetText));
		dataset.add(instance);
		return dataset;
	}
	
	private String cleanText(String text){
		TweetCleanerConfiguration cleanerConfig = new TweetCleanerConfiguration.
				Builder().
				url(CleanOptions.URL.REMOVE).
				changeCase(CleanOptions.CHANGE_CASE.RETAIN).
				number(CleanOptions.NUMBER.RETAIN).
				emoticon(CleanOptions.EMOTICON.REMOVE).
				stemming(CleanOptions.STEMMING.FALSE).
				stopwords(CleanOptions.STOPWORDS.TRUE).
				build();

		TweetCleaner tc = new TweetCleaner(cleanerConfig);
		return tc.clean(text);
	}

	public String classify(Instances dataset){
		double pred;
		try {
			pred = this.model.classifyInstance(dataset.instance(0));
			return dataset.classAttribute().value((int) pred);
		} 
		catch (Exception e) {
			System.out.println("------------- EXCEPTION REPDIM: ");
			e.printStackTrace();
			return null;
		}
	}	

}


