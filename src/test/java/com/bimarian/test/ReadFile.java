package com.bimarian.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadFile {
	public static void main1(String[] args) {
	     // Create an array of 4 strings (indexes 0 - 3)

		String arrayOfStrings[] = new String[5];

		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:/Users/admin1/Desktop/num.txt"));

			String line = "";

			int counter = 0;

			while (((line = reader.readLine()) != null) && (counter < 4)) {
				arrayOfStrings[counter] = line;
				counter++;
			}

			for (String readline : arrayOfStrings) {
				System.out.println(readline);
			}

			reader.close();
		}
		catch(Exception ex) { 
			System.out.println("Exception: " + ex.getMessage()); }
    }
	
	 public static void main(String[] args) throws FileNotFoundException {
	        File f = new File("C:/Users/admin1/Desktop/num.txt");
	        Scanner b = new Scanner(f);
	        
	        while(b.hasNext()) {
	        	String line = b.next();
	        	
	        	line.split(",");
	        }
	        
	        int[] arr = new int[b.nextInt()];
	            for(int i = 0; i < arr.length; i++){
	                arr[i] = b.nextInt();
	            }
	        for (int o : arr){
	            System.out.println(o);
	        }
	        b.close();
	    }
}
