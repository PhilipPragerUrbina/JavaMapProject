package MegaMaps.Search;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The box to target puzzle also known as warehouse keeper
 * This represents the game state
 * A player can move boxes around a grid with walls. The player must put a box on all targets.
 */
public class SokobanBoard implements Comparable<SokobanBoard>{
    /**
     * In the official game you can not push rows of boxes so leave this at false
     * But it is interesting to allow the player to push multiple boxes at a time.
     */
    private final boolean ALLOW_CASCADED_PUSH = false;

    /**
     * Turn this on to optimize heuristic based on tree depth
     * Find solutions that take fewer steps(more optimal)
     * Great for smaller and medium puzzles
     * Can significantly increase memory and time performance for larger puzzles, not recommend for most cases.
     * Breadth first search is also a good alternative.
     */
    private final Boolean A_STAR = false;

     int heuristic_score = 0 ; //Bigger is better
    int tree_depth;

    /**
     * The heuristic
     */
    @Override
    public int compareTo(SokobanBoard o) {
        //Order is reversed on purpose. We want the one with the highest score to have the highest priority(Which lower spot in queue)
        return Integer.compare(o.heuristic_score, heuristic_score);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(grid);
    }

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
     * @param tree_depth Depth in search tree for A* heuristic
     * @throws IllegalArgumentException Game state is illegal
     */
   public SokobanBoard(CellState[][] grid, Point[] targets , SokobanBoard parent, int tree_depth) throws IllegalArgumentException{
       assert (grid != null);
       assert (targets != null);
       this.targets = targets;
       this.grid = grid;
       this.parent = parent;
       this.tree_depth = tree_depth;

       //Validate game state
       if(getCount(CellState.PLAYER) != 1) throw new IllegalArgumentException("Player count must be 1.");
       if(getCount(CellState.BOX) != targets.length) throw new IllegalArgumentException("There must be 1 target per box.");
       //Find player
       for (int x = 0; x < getGridWidth(); x++) {
           for (int y = 0; y < getGridHeight(); y++) {
               if(grid[x][y].equals(CellState.PLAYER)){
                   p_x = x;
                   p_y = y;
                   break;
               }
           }
       }
   }

   //todo doc
   void calculateHeuristic(){
       //Assign score
       int score = 0;
       Point player_pos = new Point(p_x,p_y);
       ArrayList<Point> boxes = getBoxPositions();
       for (Point box: boxes ) {
           score -= box.distance(player_pos);
           for (Point target: targets) {
               score -= box.distance(target);
           }
       }
       //Additional heuristic for problems where there are some boxes on targets, and they need to be moved to put a remaining box on the target
       //Basically boxes not on targets are very expensive
       for (Point p : targets) {
           if(!grid[p.x][p.y].equals(CellState.BOX)){
               score -= 1000;
           }
       }

       if(A_STAR){
           heuristic_score = score*20 - tree_depth; //A* is weighted less
       }else{
           heuristic_score = score;
       }
   }

    /**
     * Create a board from  a plain text *.sok file
     * @param sokoban_file Location of file
     * @return Board
     * More info on the free form SOK format: https://alonso-delarte.medium.com/the-basics-of-sokoban-level-formats-for-designing-your-own-sokoban-levels-51882a7a36f0
     * @param level_number Sometimes there are multiple levels. Which one to choose starting at 0
     */
   public static SokobanBoard openBoard (File sokoban_file ,int level_number) throws IOException {
       BufferedReader reader =  new BufferedReader(new FileReader(sokoban_file));
       String line =reader.readLine();

       int level = 0;


       ArrayList<ArrayList<CellState>> resizable_grid = new ArrayList<>();
       ArrayList<Point> targets = new ArrayList<>();

       int max_width = 0;

        while(line != null ){
            //Ignore if line is empty
            //Ignore if line is extra metadata and not actual level data
            if(line.split("[^\\s#.p@b$_PB+*-]").length == 1 && !line.trim().isEmpty()){
                ArrayList<CellState> row = new ArrayList<>();

                for (int i  = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    //Supports both symbol sets
                    if (c == '#') {
                        row.add(CellState.WALL);
                    } else if(c == ' ' || c == '-' || c == '_'){
                        row.add(CellState.EMPTY);
                    } else if (c == '.') {
                        targets.add(new Point(resizable_grid.size(), i));
                        row.add(CellState.EMPTY);
                    } else if(c == 'p' || c == '@'){
                        row.add(CellState.PLAYER);
                    }else if(c == 'b' || c == '$'){
                        row.add(CellState.BOX);
                    }else if(c == 'P' || c == '+'){
                        row.add(CellState.PLAYER);
                        targets.add(new Point(resizable_grid.size(), i ));
                    }else if(c == 'B' || c == '*'){
                        row.add(CellState.BOX);
                        targets.add(new Point(resizable_grid.size(), i ));
                    }
                }
                if(row.size() > max_width){
                    max_width = row.size();
                }
                resizable_grid.add(row);
            }else{
                if(!resizable_grid.isEmpty()){
                    if(level_number == level){
                        break;
                    } else {
                        //reset
                       resizable_grid = new ArrayList<>();
                        targets = new ArrayList<>();
                       max_width = 0;
                        level++;
                    }
                }
            }
            line = reader.readLine();
        }
       reader.close();
        if(resizable_grid.isEmpty()){
            throw new IOException("Level does not exist: " + level_number);
        }
        CellState[][] grid = new CellState[resizable_grid.size()][max_width];
       for (int x = 0; x < grid[0].length; x++) {
           for (int y = 0; y < grid.length; y++) {
               ArrayList<CellState> row = resizable_grid.get(y);
               if(x < row.size()){
                   grid[y][x] = row.get(x);
               }else{
                   //fill
                   grid[y][x] = CellState.EMPTY;
               }
           }
       }
       Point[] target_array = new Point[targets.size()];
       for (int i = 0; i < targets.size(); i++) {
           target_array[i] = targets.get(i);
       }

       return new SokobanBoard(grid, target_array, null,0);

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
       return new SokobanBoard(copied_grid, targets, this, tree_depth+1); //Targets can be re-used
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
                        out.append("  ");
                        break;
                    case WALL:
                        out.append('#');
                        break;
                    case BOX:
                        out.append('b');
                        break;
                    case PLAYER:
                        out.append('p');
                        break;
                }
                out.append(' ');
                for (Point target: targets) {
                    if(target.x == x && target.y == y){
                        out.deleteCharAt(out.length()-1);
                        out.append('t'); //Can be at same time as other entities
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
       if(canMove(p_x,p_y+1,p_x,p_y  )){
           SokobanBoard child = copy();
           child.move(p_x,p_y +1);
           child.calculateHeuristic();
           states.add(child);
       }
       //Move right
       if(canMove(p_x+1,p_y,p_x,p_y )){
           SokobanBoard child = copy();
           child.move(p_x+1,p_y );
           child.calculateHeuristic();
           states.add(child);
       }
       //Move left
       if(canMove(p_x-1,p_y,p_x,p_y )){
           SokobanBoard child = copy();
           child.move(p_x-1,p_y );
           child.calculateHeuristic();
           states.add(child);
       }
       //Move down
       if(canMove(p_x,p_y-1,p_x,p_y  )){
           SokobanBoard child = copy();
           child.move(p_x,p_y -1);
           child.calculateHeuristic();
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
       //Boxes can not push boxes unless this mode is enabled
       if(!ALLOW_CASCADED_PUSH && (grid[x][y].equals(CellState.BOX) && grid[last_x][last_y].equals(CellState.BOX))) {
        return false;
       }
       //Target is a movable object. Test if it would be able to move out of the way.
       return canMove(x + (x-last_x),y + (y - last_y),x,y); //Figure out which way to push based on last position
   }

    /**
     * Is position within the bounds of the grid
     */
   public boolean inBounds(int x,int y){
       return x > -1 && y > -1 && x < getGridWidth() && y < getGridHeight();
   }

    /**
     * Get positions of boxes on grid
     */
    ArrayList<Point> getBoxPositions(){
       ArrayList<Point> positions = new ArrayList<>();
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                if(grid[x][y].equals(CellState.BOX)) positions.add(new Point(x,y));
            }
        }
        return positions;
    }

    /**
     * Get target positions on grid
     */
    Point[] getTargets(){
        return targets;
    }
}
