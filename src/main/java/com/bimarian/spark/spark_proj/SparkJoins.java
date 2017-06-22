/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package com.bimarian.spark.spark_proj;

import java.io.FileNotFoundException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

import com.google.common.base.Optional;

/**
This program performs joins operation on given 2 datasets and return the joined result
*/

/**
 * @author Rajashekar Yedla
 * 
 */
public class SparkJoins {
	@SuppressWarnings("serial")
	public static void main(String[] args) throws FileNotFoundException {
		Long startTime = System.currentTimeMillis();
		JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("Spark Joins").setMaster("local"));
		JavaRDD<String> customerInputFile = sc.textFile(args[0]);
		JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] customerSplit = s.split(",");
				return new Tuple2<String, String>(customerSplit[0], customerSplit[1]);
			}
		}).distinct();

		System.out.println("Customer Split Map to Pair: "+customerPairs.collect());
    
		JavaRDD<String> transactionInputFile = sc.textFile(args[1]);
		JavaPairRDD<String, String> transactionPairs = transactionInputFile.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] transactionSplit = s.split(",");
				return new Tuple2<String, String>(transactionSplit[2], transactionSplit[3]+","+transactionSplit[1]);
			}
		});

		System.out.println("Transaction Split Map to Pair: "+transactionPairs.collect());
    
		//Default Join operation (Inner join)
		JavaPairRDD<String, Tuple2<String, String>> joinsOutput = customerPairs.join(transactionPairs);
		System.out.println("Joins function Output: "+joinsOutput.collect());

		//Left Outer join operation, Also covered groupBy() and sortBy() methods
		//TODO: Need to extract the data from Options Type
		JavaPairRDD<String, Iterable<Tuple2<String, Optional<String>>>> leftJoinOutput = customerPairs.leftOuterJoin(transactionPairs).groupByKey().sortByKey();
		System.out.println("LeftOuterJoins function Output: "+leftJoinOutput.collect());

		//Right Outer join operation, Also covered groupBy() and sortBy() methods
		//TODO: Need to extract the data from Options Type
		JavaPairRDD<String, Iterable<Tuple2<Optional<String>, String>>> rightJoinOutput = customerPairs.rightOuterJoin(transactionPairs).groupByKey().sortByKey();
		System.out.println("RightOuterJoins function Output: "+rightJoinOutput.collect());

		Long endTime = System.currentTimeMillis();
		Long actualTime = endTime-startTime;

		System.out.println("TIme taken to done the job: "+(actualTime/1000));

		sc.close();
	}
}