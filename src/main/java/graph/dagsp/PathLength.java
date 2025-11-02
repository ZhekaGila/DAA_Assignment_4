package graph.dagsp;

import graph.scc.CondensationGraph;
import metrics.PerfomanceTracker;
import model.Edge;
import java.util.*;

public class PathLength {

    private Map<Integer, Set<Integer>> dag; 
    private List<List<Integer>> sccList; 
    private List<Integer> topoOrder;      
    private PerfomanceTracker tracker;

    private int[] previousScc;
    private Map<String, Integer> edgeWeights;

    public PathLength(CondensationGraph condensation, List<Edge> edges, List<Integer> topoOrder, PerfomanceTracker tracker) {
        this.dag = condensation.getDag();
        this.sccList = condensation.getSccList();
        this.topoOrder = topoOrder;
        this.tracker = tracker;

        this.previousScc = new int[sccList.size()];
        this.edgeWeights = new HashMap<>();


        for (Edge e : edges) {
            String key = e.getFrom() + "-" + e.getTo();
            edgeWeights.put(key, (int) e.getWeight());
        }
    }


    private int getEdgeWeight(int u, int v) {
        return edgeWeights.getOrDefault(u + "-" + v, 1); 
    }

    public Map<Integer, Integer> shortestPaths(int sourceScc) {
        tracker.reset();
        tracker.startTimer();

        int n = sccList.size();
        Map<Integer, Integer> distance = new HashMap<>();
        Arrays.fill(previousScc, -1);

        for (int i = 0; i < n; i++) distance.put(i, Integer.MAX_VALUE);
        distance.put(sourceScc, 0);

        for (int u : topoOrder) {
            if (distance.get(u) == Integer.MAX_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDist = distance.get(u) + getEdgeWeight(u, v);
                if (newDist < distance.get(v)) {
                    distance.put(v, newDist);
                    previousScc[v] = u;
                    tracker.incRelaxations();
                }
            }
        }

        tracker.stopTimer();
        return distance;
    }


    public Map<Integer, Integer> longestPaths(int sourceScc) {
        int n = sccList.size();
        Map<Integer, Integer> distance = new HashMap<>();
        Arrays.fill(previousScc, -1);

        for (int i = 0; i < n; i++) distance.put(i, Integer.MIN_VALUE);
        distance.put(sourceScc, 0);

        for (int u : topoOrder) {
            if (distance.get(u) == Integer.MIN_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDist = distance.get(u) + getEdgeWeight(u, v);
                if (newDist > distance.get(v)) {
                    distance.put(v, newDist);
                    previousScc[v] = u;
                    tracker.incRelaxations();
                }
            }
        }

        tracker.stopTimer();
        return distance;
    }
}
