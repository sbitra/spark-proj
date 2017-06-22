package com.bimarian.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
	    File f = new File("C:/Users/admin1/Desktop/num.txt");
        Scanner b = new Scanner(f);
        
        System.out.println("--- STARTING ---");
        
        String data = null;
        
        while(b.hasNext()) {
        	if(data != null) {
        		data = data +","+ b.next();
        	} else {
            	data = b.next();        		
        	}
        }
        
        System.out.println("--- ENDING ---");
        
        System.out.println("DATA: "+data);

        b.close();
        
        String[] parts = data.split(",");
        
        List<Integer> medianList = new ArrayList<Integer>();
        
        for(String part : parts) {
        	medianList.add(Integer.parseInt(part));
        	Collections.sort(medianList);
        	if(medianList.size()%2 == 1) {
        		System.out.println("Median is: "+medianList.get(medianList.size()/2)+" for the elements "+medianList);
        	} else {
        		System.out.println("Median is: "+((medianList.get(medianList.size()/2)+medianList.get(medianList.size()/2-1)))/2.0+" for the elements "+medianList);
        	}
        }

	}
}
