package MegaMaps.Search;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchMain {
    public static void main(String[] args) {
        //Test case
        SokobanBoard board = new SokobanBoard(new SokobanBoard.CellState[][]{
                {SokobanBoard.CellState.PLAYER , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.WALL , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY}


        }, new Point[]{new Point(2,1), new Point(4,2)}, null);

        printSteps(solve(board,new ArrayList<>()),1);



    }

    private static void printSteps(SokobanBoard final_board, int count){

        if(final_board.getParent() != null){
            printSteps(final_board.getParent(),count+1);
        }
        System.out.println("Step " + count);
            System.out.println(final_board);


    }

    private static  SokobanBoard solve(SokobanBoard start,ArrayList<SokobanBoard> explored){
        explored.add(start);
        if(start.isSolved()) return start;
        ArrayList<SokobanBoard> options = start.getNextStates();
        while (!options.isEmpty()) {
            int best = 0; //todo add heuristic
            SokobanBoard option = options.remove(best);
            if(explored.contains(option)) continue;;
            SokobanBoard solved = solve(option, explored);
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
