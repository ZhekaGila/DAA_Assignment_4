package model;

public class Node {
    private int id;
    private long duration;

    public Node(int id, long duration) {
        this.id = id;
        this.duration = duration;
    }

    public int getId() { return id; }
    public long getDuration() { return duration; }

    @Override
    public String toString() {
        return "Node{id=" + id + ", duration=" + duration + "}";
    }
}