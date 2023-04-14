package MegaMaps.Search;

public interface Puzzle implements Comparable{
    Puzzle getParent();

    boolean isSolved();

    Puzzle[] getNextStates();
}
