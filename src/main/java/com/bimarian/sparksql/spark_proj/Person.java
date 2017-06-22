package com.bimarian.sparksql.spark_proj;

import java.io.Serializable;

/*
 * @Author Rajashekar Yedla
 */
public class Person implements Serializable {
	
	private static final long serialVersionUID = -7924807765089697098L;

	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name+","+age;
	}
}
