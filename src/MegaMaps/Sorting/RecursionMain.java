package MegaMaps.Sorting;

import java.util.ArrayList;
import java.util.Arrays;

//Recursive puzzles
public class RecursionMain {

    public static void main(String[] args) {
        System.out.println(findBestJobs(
                new int[]{ 4 , 10 , 100,10,12,24},
                new int[]{ 10 , 5 , 1,100,2,1000000},
                24
                ,0
        ));
        System.out.println(queen8(new int[8], 0));

       hanoiTower(4);
    }

    /**
     * Print towers of hanoi instructions
     * @param count Number of discs
     */
    public static void hanoiTower(int count){
        //array is zero initialized, which is where all the discs start
        hanoiTower(count-1,new int[count], 0,2);
    }

    /**
     * Towers of hanoi
     * Move pegs from peg A to peg C,Disk can not be on smaller disk,Only disk at top of peg can be moved
     * @param n (Number of disks)-1
     * @param discs Disc positions(pin index) in order of increasing disk size. The disc array is not necessary and is only for visualization purposes.
     * @param start Start pin index 0,1,2
     * @param target End pin index 0,1,2
     */
    public static void hanoiTower(int n, int[] discs,int start,int target){
        //discs have tower id and are in increasing order
        if(n == -1) return; //No more discs
        int helper_pin =  3 - (start+target); //Get pin that is neither target, nor start
        hanoiTower(n-1,discs,start, helper_pin); //Move to helper pin
        System.out.println("Move disk " + n+1 + " to  pin " + target+1); //Output instruction(With normal person counts rather than indices)
        printDisks(discs);
        discs[n] = target;//move
        hanoiTower(n-1, discs,helper_pin,target); //Move to target
    }
   static void printDisks(int[] disks){
        for (int n = 0; n < disks.length; n++) {
            for (int j = 0; j < 3; j++) {
                if(disks[n] == j){
                    int width = (n+1)*2;
                    int total_width = (disks.length)*2;
                    for (int i = 0; i < total_width/2 - width; i++) {
                        System.out.print(" ");
                    }
                    for (int i = total_width/2 - width; i < total_width/2 + width; i++) {
                        System.out.print("-");
                    }
                    for (int i = total_width/2 + width; i < total_width+6; i++) {
                        System.out.print(" ");
                    }
                }else{
                    for (int i = 0; i <= disks.length*2+5; i++) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();

        }
    }


    //Don't consider queens on same column by representing column with index
    public static boolean queen8(int[] board, int queen_id){
        //Check if possible so far
        for (int i = 0; i < queen_id-1; i++) {
            if(queenThreatens(i,board[i], queen_id-1, board[queen_id-1])) return false; //Not possible: prune
        }

        //Check if board is full
        if(queen_id == board.length)  { //Solution found
            //print board
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board.length; y++) {
                    if(board[x] == y){
                        System.out.print(" Q ");
                    }else{
                        System.out.print(" - ");
                    }
                }
                System.out.println();
            }

            return true; //All queens don't threaten each other
        }

        //For all possibilities of current queen
        for (int i = 0; i < board.length; i++) {
            //Create new board
            int[] board_copy = board.clone();
            board_copy[queen_id] = i;
            //Move down tree
            if(queen8(board_copy, queen_id+1)) return true; //found solution
        }
        return false; //not found
    }

    //Check if two queens threaten each other
    static boolean  queenThreatens(int x1,int y1, int x2,int y2){
        return (Math.abs(y2-y1) == Math.abs(x2-x1)) || //Check if on same diagonal
        (x1 == x2 || y1 == y2) ; //Check same row or column
    }

    //Find the max amount of money you can make with jobs that take hours(jobTimes) and make dollars(jobPrices) in a given amount of time.
    public static int findBestJobs(int[] jobTimes, int[] jobPrices, int maxTime, int startingIndex) {
        if(maxTime < 0) return -jobPrices[startingIndex-1]; //Impossible to do in time. Make the sum 0.
        if(startingIndex == jobTimes.length) return 0; //Out of jobs
        int with = findBestJobs(jobTimes,jobPrices, maxTime-jobTimes[startingIndex], startingIndex+1)+ jobPrices[startingIndex]; //Try with the job, adding money but taking away time
        int with_out = findBestJobs(jobTimes,jobPrices, maxTime, startingIndex+1); //Try without
        return Math.max(with, with_out); //Return the maximum
    }





}
