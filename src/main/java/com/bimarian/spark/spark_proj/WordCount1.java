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
public class WordCount1 {
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		Long startTime = System.currentTimeMillis();

		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Count").setMaster("local"));
	    JavaRDD<String> file = sc.textFile(args[0]);

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
	    
//	    counts.saveAsTextFile("C:/Users/admin1/Desktop/Result.txt");
	    
	    Long endTime = System.currentTimeMillis();
	    Long actualTime = endTime-startTime;
	    
	    System.out.println("Time taken to done the job: "+(actualTime/1000));
	    
//	    counts.saveAsTextFile("/user/cloudera/shell_example/sparkwordoutput1.csv");
	    
	    System.out.println("Output: "+counts.collect());
	    
	    sc.close();
	  }
}