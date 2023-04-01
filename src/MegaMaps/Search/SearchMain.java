package MegaMaps.Search;

import java.awt.*;
import java.util.Scanner;

public class SearchMain {
    public static void main(String[] args) {
        SokobanBoard board = new SokobanBoard(new SokobanBoard.CellState[][]{
                {SokobanBoard.CellState.PLAYER , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.WALL , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.EMPTY, SokobanBoard.CellState.EMPTY},
                {SokobanBoard.CellState.EMPTY , SokobanBoard.CellState.BOX, SokobanBoard.CellState.EMPTY}


        }, new Point[]{new Point(2,1), new Point(4,2)}, null);
        Scanner in = new Scanner(System.in);

        while(!board.isSolved()){
            System.out.println(board);
            System.out.println("\n wasd \n");
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
