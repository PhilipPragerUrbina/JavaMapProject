package MegaMaps.Search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The box to target puzzle also known as warehouse keeper
 * This represents the game state
 * A player can move boxes around a grid with walls. The player must put a box on all targets.
 */
public class SokobanBoard {
    /**
     * A cell in the world or grid
     */
    public enum CellState {
        EMPTY, //No obstacle
        WALL, //Obstacle
        BOX, //Movable box
        TARGET, //Target with no box on it
        PLAYER, //Current player position
        SOLVED //Box and target on top of each other
    }

   final private CellState[][] grid; //x,y
    final private SokobanBoard parent;
    //Player position
    int p_x, p_y;

    /**
     * Create a game state
     * @param grid The state of the grid
     * Only 1 player allowed. There must be 1 target per box.
     * @param parent The last game state or null
     * @throws IllegalArgumentException Game state is illegal
     */
   public SokobanBoard(CellState[][] grid, SokobanBoard parent) throws IllegalArgumentException{
       assert (grid != null);
       this.grid = grid;
       this.parent = parent;
       //Validate game state
       if(getCount(CellState.PLAYER) != 1) throw new IllegalArgumentException("Player count must be 1.");
       if(getCount(CellState.BOX) != getCount(CellState.TARGET)) throw new IllegalArgumentException("There must be 1 target per box.");
       //Find player
       for (int x = 0; x < getGridWidth(); x++) {
           for (int y = 0; y < getGridHeight(); y++) {
               if(grid[x][y].equals(CellState.PLAYER)){
                   p_x = x;
                   p_y = y;
                   return;
               }
           }
       }
   }

    /**
     * Get the count of a type of cell state in the grid
     * @param target Target to search for
     * @return The number of that target present
     */
   public  int getCount(CellState target){
        int count = 0;
       for (CellState[] state_column: grid) {
           for (CellState state: state_column) {
                if(target.equals(state)) count++;
           }
       }
       return count;
    }

    /**
     * Get the width(x) of the grid
     */
    int getGridWidth(){
       return grid.length;
    }
    /**
     * Get the height(y) of the grid
     */
    int getGridHeight(){
        return grid[0].length;
    }

    /**
     * Get a copy of the board
     */
    public SokobanBoard copy(){
        CellState[][] copied_grid = new CellState[getGridWidth()][getGridHeight()];
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                copied_grid[x][y] = grid[x][y]; //Enum can be assigned
            }
        }
       return new SokobanBoard(copied_grid, this);
    }

    /**
     * Check if the grid states are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SokobanBoard that = (SokobanBoard) o;
        return Arrays.deepEquals(grid, that.grid);
    }

    /**
     * Check if the state is solved
     */
    public boolean isSolved(){
        return getCount(CellState.TARGET) == 0;
    }

    /**
     * Get simple representation of the grid state
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                CellState state = grid[x][y];
                switch (state){
                    case EMPTY:
                        out.append(" ");
                        break;
                    case WALL:
                        out.append("W");
                        break;
                    case BOX:
                        out.append("B");
                        break;
                    case TARGET:
                        out.append("T");
                        break;
                    case PLAYER:
                        out.append("P");
                        break;
                    case SOLVED:
                        out.append("S");
                        break;
                }
                out.append(" ");
            }
            out.append('\n');
        }
        return out.toString();
    }

    /**
     * Get the parent for getting solve instructions so far
     * @return Last state
     */
    public SokobanBoard getParent(){
       return parent;
   }

    /**
     * Get next possible states
     */
   public ArrayList<SokobanBoard> getNextStates(){
       ArrayList<SokobanBoard> states = new ArrayList<>();
        //Move up
       if(canMove(p_x,p_y,p_x,p_y +1 )){
           SokobanBoard child = copy();
           child.move(p_x,p_y +1);
           states.add(child);
       }
       //Move right
       if(canMove(p_x,p_y,p_x+1,p_y )){
           SokobanBoard child = copy();
           child.move(p_x+1,p_y );
           states.add(child);
       }
       //Move left
       if(canMove(p_x,p_y,p_x-1,p_y )){
           SokobanBoard child = copy();
           child.move(p_x-1,p_y );
           states.add(child);
       }
       //Move down
       if(canMove(p_x,p_y,p_x,p_y -1 )){
           SokobanBoard child = copy();
           child.move(p_x,p_y -1);
           states.add(child);
       }
       return states;
   }

    /**
     * Perform a move assuming it is legal
     * Move player to new position x,y
     */
   public void move(int x, int y){
       move(x,y,p_x,p_y);
       p_x = x;
       p_y = y;
   }

    /**
     * Perform a move ASSUMING it is legal
     * From last_x,last_y to x,y
     * Can be box,player, or solved
     */
    public void move(int x, int y, int last_x,int last_y){
        //Move out of way
        if(grid[x][y].equals(CellState.BOX) || grid[x][y].equals(CellState.SOLVED)) {
            move(x + (x-last_x),y + (y - last_y),x,y);
        };
         //Move self
        //Check to see if box and target need to be
        //Target is eliminated when box goes on it and becomes SOLVED(can be changed back to target and box if pushed).
        if(grid[last_x][last_y].equals(CellState.BOX) && grid[x][y].equals(CellState.TARGET) ){
            grid[x][y] = CellState.SOLVED;
            grid[last_x][last_y] = CellState.EMPTY;
        }else if(grid[x][y].equals(CellState.BOX) && grid[last_x][last_y].equals(CellState.TARGET) ){ //Box and target need to be separated
            grid[x][y] = CellState.BOX;
            grid[last_x][last_y] = CellState.TARGET;
        }else{
            grid[x][y] = grid[last_x][last_y];
            grid[last_x][last_y] = CellState.EMPTY;
        }
    }

    /**
     * Check if a box or player would be able to make this move
     * From last_x,last_y to x,y
     * Assumes the two coordinates are already adjacent
     * Does not check what kind of thing is moving
     * @return True if possible
     */
   public boolean canMove(int x, int y, int last_x,int last_y){
       if(!inBounds(x,y)) return  false;
       if(grid[x][y].equals(CellState.WALL)) return false;
       if(grid[x][y].equals(CellState.EMPTY) || grid[x][y].equals(CellState.TARGET) ) return true;
       //Target is a movable object. Test if it would be able to move out of the way.
       return canMove(x + (x-last_x),y + (y - last_y),x,y); //Figure out which way to push based on last position
   }

    /**
     * Is position within the bounds of the grid
     */
   public boolean inBounds(int x,int y){
       return x > -1 && y > -1 && x < getGridWidth() && y < getGridHeight();
   }


    // todo Heuristic: the inverse of the maximum distance of a box to the nearest target.

}
