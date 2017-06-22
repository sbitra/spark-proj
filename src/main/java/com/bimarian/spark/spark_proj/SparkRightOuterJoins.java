package com.bimarian.spark.spark_proj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.google.common.base.Optional;

/**
This program performs Right Outer Joins operation on the given 2 data files
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkRightOuterJoins {

	@SuppressWarnings({ "serial"})
	public static void main(String[] args) throws IOException {
		  
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark RightOuterJoin").setMaster("local"));

		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, String>(customerSplit[0], customerSplit[1]);
			}
		}).distinct();
    
		JavaRDD<String> transactionInputFile = sc.textFile(args[1]);
		JavaPairRDD<String, String> transactionPairs = transactionInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] transactionSplit = s.split(",");
				return new Tuple2<String, String>(transactionSplit[2], transactionSplit[3]+","+transactionSplit[1]);
			}
		});
    
		//Right Outer join operation
		JavaPairRDD<String, Iterable<Tuple2<Optional<String>, String>>> rightJoinOutput = customerPairs.rightOuterJoin(transactionPairs).groupByKey().sortByKey();

		Map<String, Iterable<Tuple2<Optional<String>, String>>> map = rightJoinOutput.collectAsMap();
		
		Map<String, Iterable<Tuple2<Optional<String>, String>>> treeMap = new TreeMap<String, Iterable<Tuple2<Optional<String>,String>>>(map);

		Iterable<Tuple2<Optional<String>, String>> mapValue;

		Iterator<Tuple2<Optional<String>, String>> iterator ;

		Tuple2<Optional<String>, String> outputTuple;

		String toupleKey = null;

		String toupleValue = null;

		//Writing the output to a file
		File outputFile = new File("C:/Users/admin1/Desktop/output.txt"); //Please specify the path where you want to have program output file
        BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
        
		for (Map.Entry<String,Iterable<Tuple2<Optional<String>, String>>> entry : treeMap.entrySet()) {
			mapValue = treeMap.get(entry.getKey());
			if(mapValue != null) {
				iterator = mapValue.iterator();
				if(iterator != null) {
					while (iterator.hasNext()) {
						outputTuple = (Tuple2<Optional<String>, String>) iterator.next();

						if(outputTuple._1().isPresent()){
							toupleKey = outputTuple._1().get();
							toupleValue = outputTuple._2();							
						}

						output.write(entry.getKey()+","+toupleKey+","+toupleValue);
						output.newLine();
					}									
				}
			}
		}

		output.close();
		sc.close();
	}
}