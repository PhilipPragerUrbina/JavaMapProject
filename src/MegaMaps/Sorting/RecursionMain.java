package MegaMaps.Sorting;

public class RecursionMain {

    public static void main(String[] args) {
        System.out.println(groupSum(0, new int[]{2, 4, 8}, 9));
    }


    //Don't consider queens on same column by representing column with index
    public static boolean queen8(int[] board, int queen_id){
        //Check if board is full
        if(queen_id == 9)  return checkQueenCombo(board); //Check if this branch is the solution

        //For all possibilities of current queen
        for (int i = 0; i < 8; i++) {
            //Create new board
            int[] board_copy = board.clone();
            board_copy[queen_id] = i;
            //Move down tree
            if(queen8(board_copy, queen_id+1))return true;//found solution
        }
        return false; //not found
    }
    static boolean  checkQueenCombo(int[] board){
        //We know not on same column

        //for each queen
        for (int i = 0; i < 8; i++) {
            //Check across row(Dont count self)

            //Check diagonal

        }

        return true;
    }


}
