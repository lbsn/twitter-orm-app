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
	private FilteredClassifier model;
	private Instances instances;

	public RepDimClassifier() throws FileNotFoundException, Exception{
		String modelPath = this.getClass().
				getResource("/models/" + this.MODEL_NAME).
				getPath();
		this.model = (FilteredClassifier) SerializationHelper.
				read(new FileInputStream(modelPath));
	}
	
	public void makeInstance(String tweetText){
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
		this.instances = new Instances("repDimRelation", attr, 0);
		this.instances.setClass(this.instances.attribute("dimension"));

		// Add text value
		Instance instance = new DenseInstance(2);
		instance.setValue(text, tweetText);
		instances.add(instance);
	}

	public String classify() throws Exception{
		double pred = this.model.classifyInstance(this.instances.instance(0));
		return this.instances.classAttribute().value((int) pred);
	}

}


