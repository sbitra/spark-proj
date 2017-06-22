package com.bimarian.spark.spark_proj;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
 * @author Rajashekar Yedla
 * 
 */
public class SampleClass {
	@SuppressWarnings("serial")
	public static void main(String[] args) {

		//Creating Spark Context object, this is the base object in Spark
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Count").setMaster("local"));
		
		//Loading the data in to Spark RDD
	    JavaRDD<String> file = sc.textFile("C:/Users/admin1/Desktop/text.txt");

	    // split each document into words
	    JavaRDD<String> words = file.flatMap(new FlatMapFunction<String, String>() {
	      public Iterable<String> call(String s) { 
	    	  return Arrays.asList(s.split(" "));
	      }
	    });
	    
	    // count the occurrence of each word
	    JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
	      public Tuple2<String, Integer> call(String s) {
	    	  return new Tuple2<String, Integer>(s, 1);
	      }
	    });
	    
	    JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
	      public Integer call(Integer a, Integer b) {
	    	  return a + b;
	      }
	    });
	    
	    System.out.println("Word Count Output: "+counts.collect());
	    
	    counts.saveAsTextFile("sparkwordoutput2.csv");
	    
	    sc.close();
	  }
}