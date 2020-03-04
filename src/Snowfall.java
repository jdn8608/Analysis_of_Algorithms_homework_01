import java.util.Scanner;
/*
    Name: Joseph Nied
    Class: CSCI
    HW: 1
    Question: 3
    Description: This algorithm goes through a cumulative list and sees if within a 3 consecutive data entrees
    represent more than 50% of the accumulation

 */

public class Snowfall {
    public static void main(String[] args) {
        //Create a scanner for input
        Scanner scanner = new Scanner(System.in);
        //Retrieves the size of the input data
        int SIZE = scanner.nextInt();
        //Creates an array of the input size + 1
        int data[] = new int[SIZE+1];
        //Sets the 'day 0' data to 0
        data[0]= 0;

        //Retrieves the rest of the data
        for(int i = 1; i <= SIZE; i++){
            data[i] = scanner.nextInt();

        }

        scanner.close();    //closes the scanner so it is not open for a unknown amount of time

        /*Sets the Bounds of initial points of the data (start being the start of the array, end being the end of the array
            middle being the middle of the array. HalfData represents the numerical value of half the total snow fall*/
        int startBound = 0;
        int midBound = data.length/2;
        int endBound = data.length-1;
        double halfData = data[data.length-1]/2;

        //While loop runs until we have 3 consecutive pieces of data
        while((endBound-startBound)>2){
            if(data[midBound] > halfData)
                //Sets the new end bound to the current middle bound
                endBound=midBound;


             else if(data[midBound] < halfData)
                 //Sets the new start bound to the current middle bound
                startBound = midBound;

             //If the middle is equal to 50% of the total Snow fall
             else {
                 //If this is the case, the middle may be apart of the largest difference, we must set bounds before and
                    //after the middle then
                 startBound = midBound - 1;
                endBound = midBound + 1; }

             //Sets the middle bound to halfway between the start and end bounds
            midBound = ((endBound-startBound)/2) + startBound;

        }


        //Compares the largest increase of snowfall between 3 days to 50% of the total snowfall
        if(data[endBound]-data[startBound-1] > halfData)
            System.out.println("YES");
        else
            System.out.println("NO");
    }
}
