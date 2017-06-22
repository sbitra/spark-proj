package com.bimarian.spark.spark_proj;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
This program just returns the sample data randomly from the given input data file
It performs sample() function the given data file
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkSampleFunction {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {
		
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark sample").setMaster("local"));
		
		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		
		System.out.println(customerInputFile.count());
		
		JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, String>(customerSplit[0], customerSplit[3]);
			}
		}).distinct();
		
		System.out.println(customerPairs.count());
		
/*		Here we are just getting the size of sample data returned by sample() method.
		If we want to look at actual data instead of size we can remove calling size() method at below code*/
		
/*		If we give first parameter of sample() method as true, the resulted data may or may not have duplicate keys
		true means allow duplicates
		Second parameter is a double value to indicate fractions, in other words rough size of data*/
		System.out.println((customerPairs.sample(true, 2.5)).collect().size());

		//If we give first parameter of sample() method as false, the resulted data will not have duplicate keys
		//false means don't allow duplicates. And in this case the second parameter value should be in between 0-1
		System.out.println((customerPairs.sample(false, 0.5)).collect().size());
		
		sc.close();
	}
}