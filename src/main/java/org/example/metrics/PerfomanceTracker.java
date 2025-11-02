package org.example.metrics;

public class PerfomanceTracker {

    private int dfsNodeVisitsCount = 0;
    private int dfsEdgesTraversedCount = 0;
    private int queueOperationsTotal = 0;
    private int edgeRelaxationsCount = 0;
    private int operations = 0;
    private double executionTime = 0;

    private long startTime = 0;
    private long endTime = 0;
}
