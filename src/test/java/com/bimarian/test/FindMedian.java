package com.bimarian.test;

public class FindMedian {
	public static void main(String[] args) {
		FindMedian obj = new FindMedian();
		obj.method();
	}
	
	private void method() {
				int[] array = new int[]{5,2,8,10,1,7};
				
				System.out.print(array.length+"indexes\n");
				
				System.out.println("before sort: "+array[0]+"--"+array[1]+"--"+array[2]+"--"+array[3]+"--"+array[4]+"--"+array[5]);
				
				int[] medianArray = new int[array.length];
				
				for (int j = 0; j<array.length; j++) {
					
					medianArray[j] = array[j];
					
			        for (int k = 0; k < medianArray.length; k++){
			            if (medianArray[j] < medianArray[k]) {
			                int temp = medianArray[j];
			                medianArray[j] = medianArray[k];
			                medianArray[k] = temp; 
			            }
			        }
			        if (medianArray.length % 2 == 1)
						System.out.println("Median is: "+medianArray[medianArray.length/2]);
					else if(medianArray.length == 1)
						System.out.println("Median is: "+medianArray[medianArray.length-1]);
					else
						System.out.println("Median is: "+(medianArray[medianArray.length/2] + medianArray[(medianArray.length/2)-1])/2.0);
			    }
				
				System.out.println("after sort: "+array[0]+"--"+array[1]+"--"+array[2]+"--"+array[3]+"--"+array[4]+"--"+array[5]);

/*				if (array.length % 2 == 1)
					System.out.println("Median is: "+array[array.length/2]);
				else
					System.out.println("Median is: "+(array[array.length/2] + array[(array.length/2)-1])/2.0);
*/
	}	
}
