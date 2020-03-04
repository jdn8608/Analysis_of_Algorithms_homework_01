import java.rmi.MarshalException;
import java.util.Scanner;
/*
    Name: Joseph Nied
    Class: CSCI
    HW: 1
    Question: 4
    Description:  Runs a Gale Shapely Matching problem with two sets and sees if there are 2 completely
     different matches or not
 */

public class Matches {

    public static void main(String[] args) {
        //Get the Size of the preference lists
        Scanner scanner = new Scanner(System.in);
        int SIZE = scanner.nextInt();

        //Variables to store the preference
        int prefGroup1[][] = new int[SIZE][SIZE];
        int prefGroup2[][] = new int[SIZE][SIZE];

        //Variables to copy the groups to so that the matrices can be edited
        int g1[][] = new int[prefGroup1.length][prefGroup1.length];
        int g2[][] = new int[prefGroup2.length][prefGroup2.length];

        //Fill the Matrices
        for(int row = 0; row < SIZE; row++)
            for (int col = 0; col < SIZE; col++)
                prefGroup1[row][col] = scanner.nextInt();

        for(int row = 0; row < SIZE; row++)
            for(int col = 0; col < SIZE; col++)
                prefGroup2[row][col] = scanner.nextInt();

        //Closes the Scanner
        scanner.close();

        int answer1[];
        int answer2[];

        //Matches groups with group 1 being the askers
        copyMatrix(prefGroup1, g1);
        copyMatrix(prefGroup2, g2);
        answer1 = Match2(0, g1, g2);

        //Matches groups with group 2 being the askers
        copyMatrix(prefGroup1, g1);
        copyMatrix(prefGroup2, g2);
        answer2 = Match2(0, g2, g1);

        //Parses through each pairing for comparisons
         int spot = 0;
         //Boolean to tell if there is 2 different pairings
         boolean answer = true;
         //Loops through the two matchings and sees if they are completely different or not
         while(spot < answer1.length){
                if((answer2[spot] == answer1[spot])) {
                    answer = false;
                }
                spot++;
            }
        if(answer){
            System.out.println("YES");
        } else{
            System.out.println("NO");
        }





    }


    /*
    Pre-Condition: In this, the variables related to the teachers(group 1) are the askers. This is due to our class
            discussion/ introduction on the topic. Also the start variable is used if a user wanted to start with a certain
            asker/teacher
    Post-Condition: returns the array of group1's matching list
     */
    private static int[] Match2(int start, int pref1[][], int pref2[][]){
        //Variable to tell how many people from preference list one is matched or not
        int amountMatched = pref1.length;
        //Arrays to tell if certain askers or waiters are free or not
        boolean teacherTaken[] = new boolean[pref1.length];
        boolean studentTaken[] = new boolean[pref2.length];
        //Initializes the arrays to false
        for(int i = 0; i < teacherTaken.length; i++) {
            teacherTaken[i] = false;
            studentTaken[i] = false;
        }

        //Arrays that store who everyone is partnered with
        int teacherPartners[] = new int[pref1.length];
        int studentPartners[] = new int[pref2.length];

        //variable that allows a certain person to start asking first if desired
        boolean firstTime = true;
        //Initializes the Teacher which in this case, is the asker
        int Teacher =start;
        //Loops through until all is matched
        while(amountMatched>0){
            //loops through the teachers to find the next unmatched one
            for(int rotation = 0;rotation<teacherPartners.length; rotation++){
                //Allows for dictation on who should start asking first
                if(firstTime){
                    rotation = start;
                    firstTime = false;
                }
                //if the teacher is not taken, we will have this teacher start to ask and match
                if(!teacherTaken[rotation]){
                    Teacher = rotation;
                    rotation=teacherPartners.length;
                }
            }

            //With the determined teacher, we will start asking students to match
            for(int s = 0; s < pref1.length; s++) {
                //Retrieves the next student from the preference list
                int student = pref1[Teacher][s];
                //If this student has already been asked by this teacher before, we will skip over them
                if (student != -1) {
                    if (!studentTaken[student]) {//Student is not taken
                        //Sets the boolean lists to show that these people are now taken
                        teacherTaken[Teacher] = true;
                        studentTaken[student] = true;
                        //Sets the partners so that we know that they are matched
                        teacherPartners[Teacher] = student;
                        studentPartners[student] = Teacher;
                        //sets this student to -1 on the preference list so we know we asked them
                        pref1[Teacher][s] = -1;
                        //ends the loops for this teacher
                        s = pref1.length;
                        amountMatched--;
                    }
                    //Student is taken but we will check to see if our teacher is preferred
                    //If our teacher is preferred we will proceed down below
                    else if (isPreferred(Teacher, studentPartners[student], pref2[student])) {
                        //Grabs the old teacher and un-matches with this student
                        int oldTeach = studentPartners[student];
                        teacherTaken[oldTeach] = false;
                        teacherPartners[oldTeach] = -1;
                        //Matches our teacher with this student
                        teacherTaken[Teacher] = true;
                        teacherPartners[Teacher] = student;
                        studentPartners[student] = Teacher;
                        //Marks that this student was asked by this teacher
                        pref1[Teacher][s] = -1;
                        //ends the loop for this teacher
                        s = pref1.length;
                    } else {
                        //Marks that this student was asked by this teacher
                        pref1[Teacher][s] = -1;

                    }
                }
            }
        }
        return teacherPartners;
    }

    //Compares the two people and sees which one is ranked higher on the preference list given
    private static boolean isPreferred(int newPerson, int currentPerson, int list[]){
        int newPos=0, curPos=0;
        //Loops through the preference list
        for(int i = 0; i < list.length; i++){
            //finds the spot of the new person
            if(newPerson == list[i])
                newPos = i;
            //finds the spot of the current matched person
            else if(currentPerson == list[i])
                curPos = i;
        }

        //Returns true if the new position is favored
        return newPos < curPos;
    }

    //Copys the matrix from the origin to destination pointer variable
    private static void copyMatrix(int origin[][], int destination [][]){
        for(int i = 0; i < origin.length; i++)
            for(int k = 0; k < origin.length; k++)
                destination[i][k]=origin[i][k];
    }

    }
