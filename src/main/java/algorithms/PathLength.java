package algorithms;

import model.CondensationGraph;
import model.Node;

import java.util.*;

public class PathLength {

    private Map<Integer, Set<Integer>> dag;
    private List<List<Integer>> sccList;
    private Map<Integer, Integer> compDuration;
    private List<Integer> topoOrder;

    private int[] previousList;

    public PathLength(CondensationGraph condensation, List<Node> nodes, List<Integer> topoOrder) {
        this.dag = condensation.getDag();
        this.sccList = condensation.getSccList();
        this.topoOrder = topoOrder;

        this.compDuration = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            int sum = 0;
            for (int v : sccList.get(i)) {
                sum += nodes.get(v).getDuration();
            }
            compDuration.put(i, sum);
        }

        this.previousList = new int[sccList.size()];
    }

    public Map<Integer, Integer> shortestPaths(int sourceScc) {
        int n = sccList.size();
        Map<Integer, Integer> dist = new HashMap<>();
        Arrays.fill(previousList, -1);

        for (int i = 0; i < n; i++) dist.put(i, Integer.MAX_VALUE);
        dist.put(sourceScc, compDuration.get(sourceScc));

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MAX_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDist = dist.get(u) + compDuration.get(v);
                if (newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    previousList[v] = u;
                }
            }
        }

        return dist;
    }

    public Map<Integer, Integer> longestPaths(int sourceScc) {
        int n = sccList.size();
        Map<Integer, Integer> dist = new HashMap<>();
        Arrays.fill(previousList, -1);

        for (int i = 0; i < n; i++) dist.put(i, Integer.MIN_VALUE);
        dist.put(sourceScc, compDuration.get(sourceScc));

        for (int u : topoOrder) {
            if (dist.get(u) == Integer.MIN_VALUE) continue;
            for (int v : dag.getOrDefault(u, Collections.emptySet())) {
                int newDist = dist.get(u) + compDuration.get(v);
                if (newDist > dist.get(v)) {
                    dist.put(v, newDist);
                    previousList[v] = u;
                }
            }
        }

        return dist;
    }

    public List<Integer> reconstructPath(int targetScc) {
        List<Integer> path = new ArrayList<>();
        int currentScc  = targetScc;
        while (currentScc  != -1) {
            path.add(currentScc );
            currentScc  = previousList[currentScc ];
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
