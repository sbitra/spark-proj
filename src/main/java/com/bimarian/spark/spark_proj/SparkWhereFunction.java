package com.bimarian.spark.spark_proj;

import java.io.IOException;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
This program finds the record from the input datafile with the given search condition on a specific column
It performs where operation on the given data file
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkWhereFunction {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {
		
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark where").setMaster("local"));
		
		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		
		JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, String>(customerSplit[0], customerSplit[3]);
			}
		});

		List<Tuple2<String, String>> list = customerPairs.collect();
		int temp = 0;
		
		for(Tuple2<String, String> tuple : list) {
			if(tuple._1().equals("00009167")){
				System.out.println("Record found: "+tuple);
				temp++;
			}
		}
		
		if(temp == 0) {
			System.out.println("Record not found");
		}
		sc.close();
	}
}