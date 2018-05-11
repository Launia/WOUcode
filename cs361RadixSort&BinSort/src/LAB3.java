/*
 * This is lab 3. In this lab I will be implementing both a Radix sort algorithm
 * and a Bucket sort algorithm. With these two different sorting algorithms, I will
 * use them to sort 10,000,000 numbers. 
 * 
 * Launia Davis
 * CS361 - Algorithms
 * March 17, 2018
 */

//needed Java implementation for Radix sort
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LAB3 {

	private static final String myFile = "C:\\cstemp\\lab3_data.txt"; //text file with 10 mill. numbers
	
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
	
	//source for the following code: https://www.geeksforgeeks.org/radix-sort/
	// Method will get the maximum value out of the array
	static int getMax(int arr[], int n)
	{
		int x = arr[0];
		for (int i = 1; i < n; i++)
		{
			if (arr[i] > x)
			{
				x = arr[i];
			}
		}
		return x;
	}
	
	/* Method will execute a counting sort on the array according
	 * according to the digit represented by variable 'exp'
	 */
	static void countSort(int arr[], int n, int exp)
	{
		int output[] = new int[n]; // output array
		int i;
		int count[] = new int[10];
		Arrays.fill(count, 0);
		
		// store the count of occurrences in count[]
		for (i = 0; i < n; i++)
		{
			count[(arr[i]/exp)%10]++;
		}
		
		// change count[i] so that count[i] now contains actual
		// position of this digit in output[]
		for (i = 1; i <  10; i++)
		{
			count[i] += count[i-1];
		}
		
		// building the output array
		for (i = n - 1; i >= 0; i--)
		{
			output[count[(arr[i]/exp)%10] - 1] = arr[i];
			count[(arr[i]/exp)%10]--;
		}
		
		// copy the output array to arr[], so that arr[] now contains
		// sorted numbers according to current digit
		for (i = 0; i < n; i++)
		{
			arr[i] = output[i];
		}
	}
	
	/* Method will execute the Radix sort by making method calls to
	 * previous methods in this project. It will sort through an array
	 * of n size.
	 */
	static void radixSort(int arr[], int n)
	{
		// find the maximum number to know number of digits
		int m = getMax(arr, n);
		
		// do counting sort for every digit. Note that instead of passing
		// digit number, exp is passed. exp is 10^i, where i is current digit number
		for (int exp = 1; m/exp > 0; exp *= 10)
		{
			countSort(arr, n, exp);
		}
	}
	
	// Method prints the array
	static void printArray(int arr[], int n)
	{
		// I set the size of the for loop to stop at 30 just for times sake while working on this
		System.out.println("Results of array sorted by Radix Sort:");
		for (int i = 0; i < 30; i++)
		{
			System.out.println(arr[i] + " ");
		}
		System.out.println("");
	}
	
	// method for the bucket sort
	// source: http://www.javacodex.com/Sorting/Bucket-Sort
	public static void sort(int[] a, int maxVal)
	{
		int[] bucket = new int[maxVal + 1];
		for(int i = 0; i < bucket.length; i++)
		{
			bucket[i] = 0;
		}
		for(int i = 0; i < a.length; i++)
		{
			bucket[a[i]]++;
		}
		
		int outPos = 0;
		for(int i = 0; i < bucket.length; i++)
		{
			for(int j = 0; j < bucket[i]; j++)
			{
				a[outPos++] = i;
			}
		}
	}
	
	public static void main(String[] args)
	{
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
			//print sum of array = 49995450626157
			System.out.print("The sum of my array is: " + mySum + "\n");
			System.out.println("");
		
		
		/* This is a sample to make sure the the radix sort is performing the sort correctly
		 * results: 2, 24, 45, 66, 75, 90, 170, 802
		int arr[] = {170, 45, 75, 90, 802, 24, 2, 66};
		int n = arr.length;
		radixSort(arr, n);
		printArray(arr, n);
		 */
		
		// Running the Radix sort on text file data
		System.out.println("Radix sort with text file");
		int i = myList.size();
		long startTime = System.currentTimeMillis();
		int [] myRadixList = convertIntegers(myList, i);
		radixSort(myRadixList, i);
		long endTime = System.currentTimeMillis();
		System.out.print("Total time in milliseconds for Radix Sort: " + (endTime - startTime) + "\n");  
		printArray(myRadixList, i);
		
		/* This is a sample to make sure that the bucket sort was performing correctly
		 * results: 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5
		int maxVal = 5;
		int [] data = {5,3,0,2,4,1,0,5,2,3,1,4};
		System.out.println("Before bucket sort: " + Arrays.toString(data));
		sort(data, maxVal);
		System.out.println("After bucket sort: " + Arrays.toString(data));
	   	 */	
		
		// Running the Bucket sort in text file data
		long startTimeB = System.currentTimeMillis();
		int [] myBucketList = convertIntegers(myList, i);
		int myMaxVal = myBucketList[0];
		//loop will find the largest value in the array
		for(int j = 1; j < i; j++){
			if (myMaxVal < myBucketList[j])
			{
				myMaxVal = myBucketList[j];
			}
		}
		sort(myBucketList, myMaxVal);
		long endTimeB = System.currentTimeMillis();
		System.out.print("Total time in milliseconds for Bucket Sort: " + (endTimeB - startTimeB) + "\n");  
		System.out.println("Bucket sort with text file");
		System.out.println("Results of array sorted by Bucket Sort:");
		for(int j = 0; j < i; j++)
		{
			if(j < 30)
			{
				System.out.println(myBucketList[j]);	
			}
			// following if statement was checking to see if any values were missing in the array
			// once the value reached 7737, there were multiple values that were missing in data
			// I know this part is just extra work, just curious if numbers were missing
			/*if(j > 0)
			{
				if(myBucketList[j]-1 != myBucketList[j-1])
				{
					System.out.println(myBucketList[j-1]);
					System.out.println(myBucketList[j]);
				}
			}*/
		}
	
	}
		//If no file is read in throw an exception
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
