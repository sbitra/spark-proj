package com.bimarian.sparksql.spark_proj;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.api.java.JavaSQLContext;
import org.apache.spark.sql.api.java.JavaSchemaRDD;
import org.apache.spark.sql.api.java.Row;

/*
 * @Author Rajashekar Yedla
 */
public class SparkSqlExample {
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf().setAppName("Spark_Sql_Example").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		
		JavaSQLContext sqlContext = new JavaSQLContext(sc);
		
		JavaRDD<Person> rdd = sc.textFile("C:/Users/admin1/Desktop/input.txt").map(new Function<String, Person>() {

			public Person call(String line) throws Exception {
				String[] parts = line.split(",");
				
				Person person = new Person();
				
				person.setName(parts[0]);
				person.setAge(Integer.parseInt(parts[1]));
				
				return person;
			}
		});
		
		JavaSchemaRDD schemaRDD = sqlContext.applySchema(rdd, Person.class);
		schemaRDD.registerTempTable("Person_Data");
		
		String sql = "SELECT name, age FROM Person_Data ";

		JavaSchemaRDD output = sqlContext.sql(sql);
		
		JavaRDD<Person> finalOutput = output.map(new Function<Row, Person>() {
			public Person call(Row row) throws Exception {
				Person bean = new Person();
				
				bean.setName(row.getString(0));
				bean.setAge(row.getInt(1));
				
				return bean;
			}
			
		});
		
//		finalOutput.saveAsTextFile("SparkSQL_wordcount.csv");
		
		System.out.println(finalOutput.collect());
	}
}
