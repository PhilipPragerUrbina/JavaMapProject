package MegaMaps.Sorting;

public class Circle implements Comparable<Circle>{

    private double r;

    public Circle(double r) {
        this.r = r;
    }

    @Override
    public int compareTo(Circle o) {
        return Double.compare(r,o.r);
    }

    public double getRadius(){
        return r;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "r=" + r +
                '}';
    }
}
