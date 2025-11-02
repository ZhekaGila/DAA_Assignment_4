package model;

import java.util.*;

public class Graph {
    private int nodeCount;
    private boolean isDirected;
    private String weightModel;
    private int sourceNode;
    private List<Node> nodes;
    private List<Edge> edges;
    private Map<Integer, List<Edge>> adjacency;

    public Graph(int nodeCount, boolean isDirected, String weightModel, int sourceNode,
                 List<Node> nodes, List<Edge> edges) {
        this.nodeCount = nodeCount;
        this.isDirected = isDirected;
        this.weightModel = weightModel;
        this.sourceNode = sourceNode;
        this.nodes = nodes;
        this.edges = edges;
        buildAdjacency();
    }

    private void buildAdjacency() {
        adjacency = new HashMap<>();
        for (int i = 0; i < nodeCount; i++) {
            adjacency.put(i, new ArrayList<>());
        }
        for (Edge e : edges) {
            adjacency.get(e.getFrom()).add(e);
        }
    }

    public int getNodeCount() { return nodeCount; }
    public boolean isDirected() { return isDirected; }
    public String getWeightModel() { return weightModel; }
    public int getSourceNode() { return sourceNode; }
    public List<Node> getNodes() { return nodes; }
    public List<Edge> getEdges() { return edges; }
    public Map<Integer, List<Edge>> getAdjacency() { return adjacency; }
}
