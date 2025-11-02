package model;

public class Edge {
    private int from;
    private int to;
    private long weight;

    public Edge(int from, int to, long weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int getFrom() { return from; }
    public int getTo() { return to; }
    public long getWeight() { return weight; }

    @Override
    public String toString() {
        return from + " -> " + to + " (w=" + weight + ")";
    }
}
