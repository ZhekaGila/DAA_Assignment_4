package graph.dagsp;

import graph.scc.CondensationGraph;
import model.Node;
import metrics.PerfomanceTracker;
import java.util.*;

public class PathLength {

    private Map<Integer, Set<Integer>> dag;
    private List<List<Integer>> sccList;
    private Map<Integer, Integer> compDuration;
    private List<Integer> topoOrder;
    private PerfomanceTracker tracker;

    private int[] previousScc;

    public PathLength(CondensationGraph condensation, List<Node> nodes, List<Integer> topoOrder, PerfomanceTracker tracker) {
        this.dag = condensation.getDag();
        this.sccList = condensation.getSccList();
        this.topoOrder = topoOrder;
        this.tracker = tracker;


        this.compDuration = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            int sum = 0;
            for (int v : sccList.get(i)) {
                sum += nodes.get(v).getDuration();
            }
            compDuration.put(i, sum);
        }

        this.previousScc = new int[sccList.size()];
    }

    public Map<Integer, Integer> shortestPaths(int sourceScc) {
        tracker.reset();
        tracker.startTimer();


        int n = sccList.size();
        Map<Integer, Integer> distance = new HashMap<>();
        Arrays.fill(previousScc, -1);

        for (int i = 0; i < n; i++) distance.put(i, Integer.MAX_VALUE);
        distance.put(sourceScc, compDuration.get(sourceScc));

        for (int u : topoOrder) {
            if (distance.get(u) == Integer.MAX_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDistance = distance.get(u) + compDuration.get(v);
                if (newDistance < distance.get(v)) {
                    distance.put(v, newDistance);
                    previousScc[v] = u;
                    tracker.incRelaxations();
                }
            }
        }

        return distance;
    }

    public Map<Integer, Integer> longestPaths(int sourceScc) {
        int n = sccList.size();
        Map<Integer, Integer> distance = new HashMap<>();
        Arrays.fill(previousScc, -1);

        for (int i = 0; i < n; i++) distance.put(i, Integer.MIN_VALUE);
        distance.put(sourceScc, compDuration.get(sourceScc));

        for (int u : topoOrder) {
            if (distance.get(u) == Integer.MIN_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDistance = distance.get(u) + compDuration.get(v);
                if (newDistance > distance.get(v)) {
                    distance.put(v, newDistance);
                    previousScc[v] = u;
                    tracker.incRelaxations();
                }
            }
        }

        tracker.stopTimer();
        return distance;
    }

    public List<Integer> reconstructPath(int targetScc) {
        List<Integer> path = new ArrayList<>();
        int current  = targetScc;
        while (current  != -1) {
            path.add(current );
            current  = previousScc[current];
        }
        Collections.reverse(path);
        return path;
    }

    public Map<Integer, Integer> getCompDuration() {
        return compDuration;
    }

    public List<Integer> getTopoOrder() {
        return topoOrder;
    }

    public Map<Integer, Set<Integer>> getDag() {
        return dag;
    }

    public List<List<Integer>> getSccList() {
        return sccList;
    }
}
