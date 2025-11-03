package model;

public class Node {
    private int id;

    public Node(int id, long duration) {
        this.id = id;
    }

    public int getId() { return id; }

    @Override
    public String toString() {
        return "Node{id="id"}";
    }
}
