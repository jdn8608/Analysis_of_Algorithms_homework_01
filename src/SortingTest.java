import java.util.*;
/*
    Name: Joseph Nied
    Class: CSCI
    HW: 1
    Question: 5
    Description:  Sorts n amount of random data in both Uniform and Gaussian Distribution in 3 different manors
     (Insertion, Merge, and Bucket Sort) and prints out how long each one took.
 */

public class SortingTest {
    public static void main(String[] args) {
        //Read in amount of input wanted
        final double SD = (1/10000);
        final double MEW = 0.5;
        //Code for if we want to enter in our own data for amount
        //Scanner scanner = new Scanner(System.in);
        //int amount = scanner.nextInt();

        int amount=100;
        while(amount<=100000) {
            System.out.println("Amount of Data: " + amount);

            //Create the Random Data
            double data[] = new double[amount];
            double finalT[];

            //Uniform Distribution Random Number Generator
            for (int i = 0; i < amount; i++) {
                data[i] = Math.random();
            }

            System.out.println("Uniform Distribution");

            //Insertion Sort
            long millis1 = System.currentTimeMillis();
            finalT = InsertionSort(data);
            long millis2 = System.currentTimeMillis();
            System.out.println("Insertion Sort: " + (millis2 - millis1));

            //Merge Sort
            millis1 = System.currentTimeMillis();
            finalT = MergeSort(data);
            millis2 = System.currentTimeMillis();
            System.out.println("MergeSort: " + (millis2 - millis1));

            //Bucket Sort
            millis1 = System.currentTimeMillis();
            finalT = BucketSort(data);
            millis2 = System.currentTimeMillis();
            System.out.println("BucketSort: " + (millis2 - millis1));

            //Gaussian Random Number Generator from the Random class
            for (int i = 0; i < amount; i++) {
                Random r = new Random();
                data[i] = (r.nextGaussian() * SD) + MEW;
            }

            System.out.println("Gaussian Distribution");

            //Insertion Sort
            millis1 = System.currentTimeMillis();
            finalT = InsertionSort(data);
            millis2 = System.currentTimeMillis();
            System.out.println("Insertion Sort: " + (millis2 - millis1));

            //Merge Sort
            millis1 = System.currentTimeMillis();
            finalT = MergeSort(data);
            millis2 = System.currentTimeMillis();
            System.out.println("MergeSort: " + (millis2 - millis1));

            //Bucket Sort
            millis1 = System.currentTimeMillis();
            finalT = BucketSort(data);
            millis2 = System.currentTimeMillis();
            System.out.println("BucketSort: " + (millis2 - millis1));

            amount = amount*10;
        }
    }

    //Post-Condition: Sorts data in O(n^2) time
    private static double[] InsertionSort(double data[]) {
        //Tells where up to in the array we have sorted (exclusive)
        int placeSorted = 1;
        //Pointer is the Traverser in this sorting algorithm
        int pointer;
        //Stores the data of a spot when a shift happens
        double tempVal;

        //Runs until we sort the whole array has been sorted
        while (placeSorted < data.length) {
            tempVal = data[placeSorted];
            //Sets the pointer to point at what is sorted in the array so far
            pointer = placeSorted - 1;
            //Loops through the sorted data and shifts the data by one to the right until the spot for the new data is found
            while (pointer >= 0 && tempVal < data[pointer]) {
                data[pointer + 1] = data[pointer];
                pointer--;
            }
            //Inserts the new data into it's correct position
            data[pointer + 1] = tempVal;
            //Increases where we have sorted up to
            placeSorted++;
        }
        return data;
    }

    //Post-Condition: Sorts the data recursively in O(nlogn) time
    private static double[] MergeSort(double data[]) {
        //Base Case of size of array = 1
        if (data.length == 1) {
            return data;
        } else {
            //Sets the Middle index
            int middle = data.length / 2;
            //Splits the array into two almost equal halves
            //A half
            double A[] = new double[middle];
            for (int i = 0; i < middle; i++)
                A[i] = data[i];
            A = MergeSort(A);   //Recursive call to split the array until it is just single elements
            //B half
            double B[] = new double[data.length - middle];
            for (int k = middle; k < data.length; k++)
                B[k - middle] = data[k];
            B = MergeSort(B);   //Recursive call to split the array until it is just single elements
            //Merge the two sorted arrays
            return Merge(A, B);
        }
    }

    //Post-Condition: Assist Method to Merge two sorted arrays into 1 sorted array
    private static double[] Merge(double A[], double B[]) {
        //Temp variables for each array to parse through
        int tempA = 0, tempB = 0;
        //Variable to parse through the new sorted array
        int pointer = 0;

        //Array used to store values of A and B so that this array is now sorted
        double Merged[] = new double[A.length + B.length];

        //Runs until A or B is empty
        while (tempA < A.length && tempB < B.length) {
            //Inserts A if A is smaller than B
            if (A[tempA] < B[tempB]) {
                Merged[pointer] = A[tempA];
                tempA++;
            }
            //Insert B if B is smaller than A
            else {
                Merged[pointer] = B[tempB];
                tempB++;
            }
            pointer++;
        }
        //If B is not empty, add remaining elements of B to the sorted array(Merged)
        while (tempB < B.length) {
            Merged[pointer] = B[tempB];
            pointer++;
            tempB++;
        }
        //If A is not empty, add remaining elements of A to the sorted array(Merged)
        while (tempA < A.length) {
            Merged[pointer] = A[tempA];
            pointer++;
            tempA++;
        }
        return Merged;
    }

    //Pre-Condition : Random Variables should be in a Uniformly Distributed Set
    //Post-Condition: Sorts the Array in O(n) time
    private static double[] BucketSort(double data[]) {
        //the array that contains lists at each element
        List BucketList[] = new List[data.length];
        for(int i = 0; i < BucketList.length; i++){
            BucketList[i] = new ArrayList<Double>();
        }

        //Adds data to each "Buckt" (list) based on the floor of the data * the length of the data
        for(int i = 0; i < data.length; i++){
            BucketList[(int)(Math.floor(data.length * data[i]))].add(data[i]);
        }

        //Parsing variable
        int pointer = 0;
        //the array of sorted elements
        double finalArray[] = new double[data.length];

        for(int k = 0; k < BucketList.length; k++) {
            //Makes a temp array thsgt is as long as the list at K's size
            double temp[] = new double[BucketList[k].size()];
            for(int j = 0; j < temp.length; j++)
                //Fills temp with the elements at k
                temp[j] = (double)BucketList[k].remove(0);
            //Sorts this data with Insertion Sort
            temp = InsertionSort(temp);
            //Adds data to the finalized array
            for(int l = 0; l < temp.length; l++) {
                finalArray[pointer] = temp[l];
                pointer++;
            }
        }
        return finalArray;
    }
}
