package com.bimarian.spark.spark_proj;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
This program will count the num of customers (records) from the input file.
It performs the count() operation on the given data file
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkCountFunction {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {
		
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Count").setMaster("local"));
		
		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		
		System.out.println(customerInputFile.count());
		
		JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, String>(customerSplit[0], customerSplit[3]);
			}
		}).distinct();
		
		System.out.println("Number of customers(count fun): "+customerPairs.count());
		sc.close();
	}
}