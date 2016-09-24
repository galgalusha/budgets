package galko.budgets.service_layer.dto;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
