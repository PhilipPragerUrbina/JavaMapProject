package MegaMaps.Search;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class SearchMain {
    public static void main(String[] args) throws IOException {
        //Hardcoded test case
        SokobanBoard board = new SokobanBoard(new SokobanBoard.CellState[][]{
                {SokobanBoard.CellState.PLAYER , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.WALL , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY}
        }, new Point[]{new Point(2,1), new Point(4,2)}, null,0);

        //Open a puzzle file
        SokobanBoard board_2 = SokobanBoard.openBoard(new File("puzzles/Alonso-Del-Arte/sok/Frustratingly Difficult.sok"),9);
        System.out.println(board_2);
        printSteps(solveHeuristic(board_2));
    }

    /**
     * Print steps
     * @param final_board Final state to print steps to
     */
    private static void printSteps(SokobanBoard final_board){
    ArrayList<SokobanBoard> steps = new ArrayList<>();
    steps.add(final_board);
    while (final_board.getParent() != null){
        final_board = final_board.getParent();
        steps.add(final_board);
    }
        for (int i = steps.size()-1; i >= 0; i--) {
            System.out.println("Step " + (steps.size() - i));
            System.out.println(steps.get(i));
        }
    }



    //Dynamic ordering
    private static  SokobanBoard solveHeuristic(SokobanBoard start){
        HashSet<SokobanBoard> explored = new HashSet<>();
        Queue<SokobanBoard> frontier = new PriorityQueue<>(); //Orders by heuristic score
        //todo implement the comparable interface to add heuristic to a priority queue
        frontier.add(start);
        while (!frontier.isEmpty()){
            SokobanBoard current = frontier.remove();
            if(current.isSolved()) return current;
            for (SokobanBoard possibility: current.getNextStates()) {
                if(explored.contains(possibility)) continue;
                explored.add(possibility);
                frontier.add(possibility);
            }
        }
        return null;
    }


    //Depth first. not recursive
    private static  SokobanBoard solveDepth(SokobanBoard start){
        //todo test hash map as well. Also add a hash code to make comparison faster for both arrays and hash maps.
        HashSet<SokobanBoard> explored = new HashSet<>(); //todo pre-allocate
        Stack<SokobanBoard> frontier = new Stack<>();  //LIFO
        frontier.push(start);
        while (!frontier.empty()){
            SokobanBoard current = frontier.pop();
            if(current.isSolved()) return current;
            for (SokobanBoard possibility: current.getNextStates()) {
                if(explored.contains(possibility)) continue;
                explored.add(possibility);
                frontier.push(possibility);
            }
        }
        return null;
    }

    //breadth first. Finds faster solutions.
    private static  SokobanBoard solveBreadth(SokobanBoard start){
        HashSet<SokobanBoard> explored = new HashSet<>();
        Queue<SokobanBoard> frontier = new ArrayDeque<>(); //FIFO
        frontier.add(start);
        while (!frontier.isEmpty()){
            SokobanBoard current = frontier.remove();
            if(current.isSolved()) return current;
            for (SokobanBoard possibility: current.getNextStates()) {
                if(explored.contains(possibility)) continue;
                explored.add(possibility);
                frontier.add(possibility);
            }
        }
        return null;
    }

    //depth first
    private static  SokobanBoard solveRecursiveDepth(SokobanBoard start,ArrayList<SokobanBoard> explored, int max_depth){
        if(max_depth == 0) return null;
      //  System.out.println(start);
        explored.add(start);
        if(start.isSolved()) return start;
        ArrayList<SokobanBoard> options = start.getNextStates();
        for (SokobanBoard option : options) {
            if(explored.contains(option)) continue;
            SokobanBoard solved = solveRecursiveDepth(option, explored,max_depth-1);
            if (solved != null) {
                return solved;
            }
        }
        return null;
    }


    private static void  playBoard(SokobanBoard board){
        Scanner in = new Scanner(System.in);
        //Basic player
        while(!board.isSolved()){
            System.out.println(board);
            System.out.println("\n");
            char dir = in.nextLine().charAt(0);
            int x_move = 0;
            int y_move = 0;
            switch (dir){
                case 'w':
                    x_move--;
                    break;
                case 'a':
                    y_move--;
                    break;
                case 'd':
                    y_move++;
                    break;
                case 's':
                    x_move++;
            }
            if(x_move != 0 || y_move != 0){
                if(board.canMove(board.getPlayerX()+x_move, board.getPlayerY()+y_move, board.getPlayerX(), board.getPlayerY())){
                    board.move(board.getPlayerX()+x_move, board.getPlayerY()+y_move);
                }
            }
        }
        System.out.println(board);
        System.out.println("You win!!");
    }
}
