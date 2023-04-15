package MegaMaps.Search;

import java.util.ArrayList;

public interface Puzzle extends Comparable {
    Puzzle getParent();

    boolean isSolved();

    ArrayList<Puzzle> getNextStates();
}
