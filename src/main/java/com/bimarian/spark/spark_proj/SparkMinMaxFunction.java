package com.bimarian.spark.spark_proj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
This programs finds minimun value and maximum value on a selected column in the given data file
It performs min() and max() operation on the given data file
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkMinMaxFunction {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {
		
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Min_Max").setMaster("local"));
		
		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		
		JavaPairRDD<String, Double> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, Double>() {
			public Tuple2<String, Double> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, Double>(customerSplit[0], Double.parseDouble(customerSplit[3]));
			}
		});
    
		
/*		The below code is based on the Apache Spark API, Spark has given pre-defined min() and max() methods.
		But somehow I could not able to execute below code, throwing "NoSuchMethodException"*/

//		Tuple2<String, String> mapFunction = customerPairs.min(new Comparator<Tuple2<String,String>>() {
//			public int compare(Tuple2<String, String> o1, Tuple2<String, String> o2) {
//				if(o1 != null && o2 != null){
//					if(Double.parseDouble(o1._2()) < Double.parseDouble(o2._2())) {
//						return -1;
//					} else if(Double.parseDouble(o1._2()) > Double.parseDouble(o2._2())) {
//						return -1;
//					}
//					else {
//						return 0;						
//					}
//				}
//				return 0;
//			}
//		});
		
		
		List<Tuple2<String, Double>> l = customerPairs.collect();
		List<Double> valueList = new ArrayList<Double>();

		for(Tuple2<String, Double> tuple : l) {
			valueList.add(tuple._2());
		}
		
		Collections.sort(valueList);
		
		System.out.println("List: "+valueList);

		System.out.println("\nMinimum value: "+valueList.get(0));
		System.out.println("\nMaximum value: "+valueList.get(valueList.size()-1));
		
		sc.close();
	}
}