package lab2;

/* This is Lab 2 parts 5 & 6.
 * 
 * Launia Davis
 * Lab 2
 * CS361 - Algorithms
 * February 25, 2018
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lab2A {

	private static final String myFile = "C:\\cstemp\\lab1_data.txt"; //text file with 10 mill. numbers
	private static final String mySampleFile = "C:\\Users\\launi\\workspace\\testLab2\\Lab2\\lab2\\src\\lab2\\SampleFile.txt"; 
	//smaller file used during my process for ease of debugging
	
	
	/* This method converts Integers to int so that I can make method call with primitive int's
	 */
	public static int[] convertIntegers(List<Integer> integers, int mySize)
	{
	    int[] ret = new int[integers.size()];
	    if (mySize > ret.length){
	    	mySize = ret.length;
	    }
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}	
	
	/* This method will be using a merge sort to sort an
	 * array that is given in as its parameter.
	 */
	public static void auxMergeSort(int[] myArray, int myStart, int myEnd){
		if (myEnd <= 1){
			//System.out.print("This list is sorted. It either has one or no elements.");
		}
		else {
			//first half array created
			int[] firstHalf = new int[myEnd/2];
			System.arraycopy(myArray, myStart, firstHalf, myStart, myEnd/2);
			//breaking down first half array further
			auxMergeSort(firstHalf, myStart, firstHalf.length);
			
			//second half length to find starting from original array
			int secondHalfLen = myEnd-myEnd/2;
			//second half array created
			int[] secondHalf = new int[secondHalfLen];
			System.arraycopy(myArray, myEnd/2, secondHalf, myStart, secondHalfLen);
			//breaking down second half array further
			auxMergeSort(secondHalf, myStart, secondHalf.length);
			
			merge(firstHalf, secondHalf, myArray);
		}
	}	
	
	/* This is the merge method that'll use the auxMergeSort method to complete 
	 * the sort between the two halves of the original array myArray
	 */
	public static void merge(int[] array1, int[] array2, int [] myArray){
		int cnt1 = 0; //current index of array1
		int cnt2 = 0; //current index of array2
		int cnt3 = 0; //current index of myArray
		
		while (cnt1 < array1.length && cnt2 < array2.length){
			/*for the if statement below, the value that is in index position of cnt1 in array1 is
			  placed in index position of cnt3 in myArray then each cnt is incremented*/
			if (array1[cnt1] > array2[cnt2]){  //switched from < to > so that array is sorted in descending order
				myArray[cnt3++] = array1[cnt1++]; 
			} else {
				myArray[cnt3++] = array2[cnt2++];
			}
		}
		while (cnt1 < array1.length) {
			myArray[cnt3++] = array1[cnt1++];
		}
		while (cnt2 < array2.length) {
			myArray[cnt3++] = array2[cnt2++];
		}
	}		
	
	/*
	 * The following 3 methods (quickSelection, partition, and swap) all work together to run a quick 
	 * selection algorithm that will use myFile to eventually display the 10 largest elements in the array
	 * source: https://bidhutkarki.wordpress.com/2015/11/05/quick-select-algorithm-java-implementation/
	 */
    public static int quickSelect(int[] arr, int startIndex, int endIndex, int k) {
        if (startIndex == endIndex) {
            return arr[startIndex];
        }
        int pivot = partition(arr, startIndex, endIndex);
        if (pivot == k) {
            return arr[pivot];
        } else if (k < pivot) {
            return quickSelect(arr, startIndex, pivot - 1, k);
        } else {
            return quickSelect(arr, pivot + 1, endIndex, k);
        }
    }

    private static int partition(int[] arr, int startIndex, int endIndex) {
        int pivot = endIndex;
        int partitionIndex = startIndex;
        for (int i = startIndex; i <= endIndex - 1; i++) {
            if (arr[i] >= arr[pivot]) {
                swap(arr, i, partitionIndex);

                partitionIndex++;
            }
        }

        swap(arr, partitionIndex, pivot);

        // partitionIndex will be the rearranged pivot
        return partitionIndex;
    }

    private static void swap(int[] arr, int firstIndex, int secondIndex) {
        int temp = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = temp;
    }
	
	/*
	 * Main method from Lab 1 that will read in a file and place those elements into an array.
	 * The majority of this code is duplicated from my Lab 1 with modifications needed for Lab 2
	 * starting at the second for loop.
	 */
	public static void main(String[] args) {

		BufferedReader myBuff = null;
		FileReader myFRead = null;
		
		//reading in the file
		try {
			myFRead = new FileReader (myFile);
			myBuff = new BufferedReader (myFRead);
			String myString = myBuff.readLine();

			long mySum = 0; //need to hold a large number
			
			//create new array
			ArrayList<Integer> myList = new ArrayList<Integer>();
			
			while (myString != null){
				//add to list
				myList.add(Integer.parseInt(myString));				
				mySum = mySum + Long.parseLong(myString);
				myString = myBuff.readLine();
			}
			//print sum of array to make sure myFile was read in correctly. = 49999995000000
			System.out.print("The sum of my array is: " + mySum + "\n");
			
			
			//calling auxMergeSort method with array that starts at size 1,000 and then 
			//grows in size multiplied by 10 till it reaches array size of 10,000,000
			for (int i = 1000; i <= 10000000; i = i*10){
				System.out.print("Run for array size: " + i + "\n" );
				int myStart = 0;
				int [] myNewList = convertIntegers(myList, i);
				int myEnd = myNewList.length;
				//System.out.print("Before merge sort: " + Arrays.toString(myNewList) + "\n");
				long startTimeA = System.currentTimeMillis();		
				auxMergeSort(myNewList, myStart, i);
				long endTimeA = System.currentTimeMillis();
				System.out.print("Total time in milliseconds for merge sort: " + (endTimeA - startTimeA) + "\n");
				 System.out.println("The top 10 elements in descending order:");
				 System.out.println("Element 1: " + myNewList[0]);
				 System.out.println("Element 2: " + myNewList[1]);
				 System.out.println("Element 3: " + myNewList[2]);
				 System.out.println("Element 4: " + myNewList[3]);
				 System.out.println("Element 5: " + myNewList[4]);
				 System.out.println("Element 6: " + myNewList[5]);
				 System.out.println("Element 7: " + myNewList[6]);
				 System.out.println("Element 8: " + myNewList[7]);
				 System.out.println("Element 9: " + myNewList[8]);
				 System.out.println("Element 10: " + myNewList[9]);
			}
			//calling quickSelect method with array that starts at size 1,000 and then 
			//grows in size by multiples of 10 till it reaches size 10,000,000
			for (int i = 1000; i <= 10000000; i = i*10){
				System.out.print("Run for array size: " + i + "\n" );
				long startTimeA = System.currentTimeMillis();
				int [] myNewList = convertIntegers(myList, i);
				 int largest = quickSelect(myNewList, 0, myNewList.length - 1, 0);
				 long endTimeA = System.currentTimeMillis();
				 System.out.print("Total time in milliseconds for quickselection: " + (endTimeA - startTimeA) + "\n");  
				 System.out.println("The top 10 elements in descending order:");
				 System.out.println("Element 1: " + myNewList[0]);
				 System.out.println("Element 2: " + myNewList[1]);
				 System.out.println("Element 3: " + myNewList[2]);
				 System.out.println("Element 4: " + myNewList[3]);
				 System.out.println("Element 5: " + myNewList[4]);
				 System.out.println("Element 6: " + myNewList[5]);
				 System.out.println("Element 7: " + myNewList[6]);
				 System.out.println("Element 8: " + myNewList[7]);
				 System.out.println("Element 9: " + myNewList[8]);
				 System.out.println("Element 10: " + myNewList[9]);
			}

		}
		//If no file is read in throw an exception
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

