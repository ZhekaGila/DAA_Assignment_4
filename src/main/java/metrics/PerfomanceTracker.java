package org.example.metrics;

public class PerfomanceTracker {

    private int dfs_vertices = 0;
    private int dfs_edges = 0;
    private int kahn_pushes = 0;
    private int kahn_pops = 0;
    private int kahn_edges_processed = 0;
    private int relaxations = 0;
    private int operations = 0;
    private double executionTime = 0;

    private long startTime = 0;
    private long endTime = 0;
}
