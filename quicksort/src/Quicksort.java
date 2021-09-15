import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Quicksort {	
	public static void quicksort(int[] sortArr, int loBound, int hiBound) {
		if (loBound < hiBound) {
			int q = partition(sortArr, loBound, hiBound);
			quicksort(sortArr, loBound, q-1);
			quicksort(sortArr, q+1, hiBound);
		}
	}
	
	public static void quicksort(int[] sortArr) {
		int loBound = 0;
		int hiBound = sortArr.length - 1;
		
		if (loBound < hiBound) {
			int q = partition(sortArr, loBound, hiBound);
			quicksort(sortArr, loBound, q-1);
			quicksort(sortArr, q+1, hiBound);
		}
	}
	
	public static void quicksort(int[] sortArr, int loBound, int hiBound, int cutOff) {
		if (loBound < hiBound) {
			if (hiBound - loBound > cutOff)  {
				int q = partition(sortArr, loBound, hiBound);
				quicksort(sortArr, loBound, q-1);
				quicksort(sortArr, q+1, hiBound);
			} else {
				return;
			}
		}
	}
	
	public static void quicksortTW(int[] arr, int loBound, int hiBound) {
		if (loBound < hiBound) {		
			int q[] = twPartition(arr, loBound, hiBound);
			quicksortTW(arr, loBound, q[0]);
			quicksortTW(arr, q[1], hiBound);
		}
	}
	
	public static void quicksortTW(int[] sortArr) {
		int loBound = 0;
		int hiBound = sortArr.length - 1;
		
		if (loBound < hiBound) {		
			int q[] = twPartition(sortArr, loBound, hiBound);
			quicksortTW(sortArr, loBound, q[0]);
			quicksortTW(sortArr, q[1], hiBound);
		}
	}
	
	public static void quicksortMOT(int[] sortArr, int loBound, int hiBound) {
		if (loBound < hiBound) {
			int q = medianOfThree(sortArr, loBound, hiBound);
			quicksortMOT(sortArr, loBound, q-1);
			quicksortMOT(sortArr, q+1, hiBound);
		}
	}
	
	public static void quicksortMOT(int[] sortArr) {
		int loBound = 0;
		int hiBound = sortArr.length - 1;
		
		if (loBound < hiBound) {
			int q = medianOfThree(sortArr, loBound, hiBound);
			quicksort(sortArr, loBound, q-1);
			quicksort(sortArr, q+1, hiBound);
		}
	}
		
	public static void quicksortMerge(int[] sortArr, int loBound, int hiBound, int cutOff) {
		if (loBound < hiBound) {
			int q = partition(sortArr, loBound, hiBound);
			quicksort(sortArr, loBound, q-1, cutOff);
			quicksort(sortArr, q+1, hiBound, cutOff);
			insertionsort(sortArr);
		}
	}
	
	public static void quicksortMerge(int[] sortArr, int cutOff) {
		int loBound = 0;
		int hiBound = sortArr.length - 1;
		
		if (loBound < hiBound) {
			int q = partition(sortArr, loBound, hiBound);
			quicksort(sortArr, loBound, q-1, cutOff);
			quicksort(sortArr, q+1, hiBound, cutOff);
			insertionsort(sortArr);
		}
	}
	
	private static void insertionsort(int[] sortArr) {
		for (int i = 0 - 1; i < sortArr.length; i++) {
			for (int j = 0; j < i - 1; j++) {
				if (sortArr[i] < sortArr[j]) {
					swap(sortArr, i, j);
				}
			}
		}
	}
	
	private static int partition(int[] arr, int loBound, int upBound) {
		int pivot = arr[upBound];
		int lessLimit = loBound - 1;
		
		for (int nextVal = loBound; nextVal < upBound; nextVal++) {
			if (arr[nextVal] <= pivot) {
				lessLimit += 1;
				swap(arr, lessLimit, nextVal);
			}
		}
		
		swap(arr, lessLimit + 1, upBound);
		return lessLimit + 1;
	} 
	
	private static int medianOfThree(int[] arr, int loBound, int upBound) {
		int pivot = (arr[upBound] + arr[upBound] + 
					 arr[(loBound + upBound)/2])/3;
		int lessLimit = loBound - 1;
		
		for (int nextVal = loBound; nextVal < upBound; nextVal++) {
			if (arr[nextVal] <= pivot) {
				lessLimit += 1;
				swap(arr, lessLimit, nextVal);
			}
		}
		
		swap(arr, lessLimit + 1, upBound);
		return lessLimit + 1;
	} 
	
	private static int[] twPartition(int[] arr, int loBound, int hiBound) {	
		if (hiBound - loBound <= 1) {
			if (arr[hiBound] < arr[loBound]) {
				swap(arr, hiBound, loBound);
			}
			
			int returnArray[] = {loBound, hiBound};
			return returnArray;
		}
		
		int mid = loBound;
		int pivot = arr[hiBound];
		while (mid <= hiBound) {
			if (arr[mid] < pivot) {
				swap(arr, loBound++, mid++);
			} else if (arr[mid] == pivot) {
				mid++;
			} else if (arr[mid] > pivot) {
				swap(arr, mid, hiBound--);
			}
		}
		
		int returnArray[] = {loBound-1, mid};
		return returnArray;
	}
	
	private static void swap(int[] swapArray, int swapIndex1, int swapIndex2) {
		int buffer;
		
		buffer = swapArray[swapIndex1];
		swapArray[swapIndex1] = swapArray[swapIndex2];
		swapArray[swapIndex2] = buffer;
	}

	
}
