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
This program performs sum on selected column and also performa average on selected column
It performs sum() and avg() functions on the given data file on a selected column
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkAvgSumFunction {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {

		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Avg_Sum").setMaster("local"));

		JavaRDD<String> customerInputFile = sc.textFile(args[0]);

		JavaPairRDD<String, Double> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, Double>() {
			public Tuple2<String, Double> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, Double>(customerSplit[0], Double.parseDouble(customerSplit[3]));
			}
		});

		List<Tuple2<String, Double>> l = customerPairs.collect();
		Double sum = 0.0;

		for(Tuple2<String, Double> tuple : l) {
			if(tuple._2() != null) {
				sum = sum + tuple._2();
			}
		}

		System.out.println("Sum is: "+sum);
		System.out.println("Avg is: "+sum/l.size()+1);

		sc.close();
	}
}