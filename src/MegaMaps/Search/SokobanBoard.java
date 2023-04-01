package MegaMaps.Search;

import java.awt.*;
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
        PLAYER //Current player position
    }

   final private CellState[][] grid; //x,y
    final private Point[] targets; //Does not change
    final private SokobanBoard parent;
    //Player position
    private int p_x, p_y;

    /**
     * Create a game state
     * @param grid The state of the grid
     * Only 1 player allowed. There must be 1 target per box.
     * @param targets The positions of the targets. ASSUMES in bounds.
     * @param parent The last game state or null
     * @throws IllegalArgumentException Game state is illegal
     */
   public SokobanBoard(CellState[][] grid, Point[] targets , SokobanBoard parent) throws IllegalArgumentException{
       assert (grid != null);
       assert (targets != null);
       this.targets = targets;
       this.grid = grid;
       this.parent = parent;

       //Validate game state
       if(getCount(CellState.PLAYER) != 1) throw new IllegalArgumentException("Player count must be 1.");
       if(getCount(CellState.BOX) != targets.length) throw new IllegalArgumentException("There must be 1 target per box.");
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
       return new SokobanBoard(copied_grid, targets, this); //Targets can be re-used
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
        for (Point p : targets) {
            if(!grid[p.x][p.y].equals(CellState.BOX)){
                return false;
            }
        }
        return true;
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
                    case PLAYER:
                        out.append("P");
                        break;
                }
                out.append(" ");
                for (Point target: targets) {
                    if(target.x == x && target.y == y){
                        out.deleteCharAt(out.length()-1);
                        out.append("T"); //Can be at same time as other entities
                        break;
                    }
                }

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
    private void move(int x, int y, int last_x,int last_y){
        //Move out of way
        if(grid[x][y].equals(CellState.BOX)) {
            move(x + (x-last_x),y + (y - last_y),x,y);
        };

        //Move self
        grid[x][y] = grid[last_x][last_y];
        grid[last_x][last_y] = CellState.EMPTY;
    }

    /**
     * Get player horizontal position
     */
    public int getPlayerX(){
        return p_x;
    }

    /**
     * Get player vertical position
     */
    public int getPlayerY(){
        return p_y;
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
       if(grid[x][y].equals(CellState.EMPTY)) return true;
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
