package lbsn.twitter_orm_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToWordVector;

@Component
public class KMeansClusterer {
	private Instances data;
	private SimpleKMeans clusterer = new SimpleKMeans();

	public KMeansClusterer(){
		Attribute text = new Attribute("text", true);
		Attribute _id = new Attribute("_id", true);

		ArrayList<Attribute> attr = new ArrayList<Attribute>();
		// FIXME: This should store the MongoDb id, for future reference
		attr.add(_id);
		attr.add(text);

		this.data = new Instances("data", attr, 0);		
	}

	public void loadData(List<String> dataToLoad){
		for(String t : dataToLoad){
			Instance instance = new DenseInstance(2);
			instance.setDataset(this.data);

			// FIXME: This needs to be changed!
			instance.setValue(this.data.attribute(0), "_" + Math.random());
			instance.setValue(this.data.attribute(1), t);

			this.data.add(instance);	
		}		
	}

	public Instances filterData() throws Exception{
		Instances filteredData;

		Remove removeFilter = new Remove();
		removeFilter.setInputFormat(this.data);
		removeFilter.setAttributeIndices("0-0");
		filteredData = Filter.useFilter(this.data, removeFilter);

		// Set the tokenizer
		NGramTokenizer tokenizer = new NGramTokenizer();
		tokenizer.setNGramMinSize(1);
		tokenizer.setNGramMaxSize(2);
		tokenizer.setDelimiters(" ");

		// Set the filter
		StringToWordVector filter = new StringToWordVector();
		filter.setInputFormat(filteredData);
		// Tokenizer
		filter.setTokenizer(tokenizer);
		// Number of words
		filter.setWordsToKeep(1000);
		// TF transform
		filter.setTFTransform(true);

		filter.setAttributeNamePrefix("_");
		// IDF transform
		filter.setIDFTransform(true);

		filter.setLowerCaseTokens(false);

		return Filter.useFilter(filteredData, filter);
	}

	public void buildClusterer() throws Exception{
		clusterer.setNumClusters(5);
		clusterer.setPreserveInstancesOrder(true);
		clusterer.buildClusterer(filterData());
	}

	public int [] getAssignments() throws Exception{
		int [] assignments = this.clusterer.getAssignments();
		return assignments;

	}
}
