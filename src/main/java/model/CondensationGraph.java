package model;

import java.util.*;

public class CondensationGraph {

    private final List<List<Integer>> sccList;
    private final Map<Integer, Set<Integer>> dag;

    public CondensationGraph(Graph graph, List<List<Integer>> sccList) {
        this.sccList = sccList;
        this.dag = new HashMap<>();
        buildCondensation(graph);
    }

    private void buildCondensation(Graph graph) {
        Map<Integer, Integer> vertexToScc = new HashMap<>();
        for (int i = 0; i < sccList.size(); i++) {
            for (int v : sccList.get(i)) {
                vertexToScc.put(v, i);
            }
            dag.put(i, new HashSet<>());
        }

        for (Edge e : graph.getEdges()) {
            int sccFrom = vertexToScc.get(e.getFrom());
            int sccTo = vertexToScc.get(e.getTo());
            if (sccFrom != sccTo) {
                dag.get(sccFrom).add(sccTo);
            }
        }

    }

    public Map<Integer, Set<Integer>> getDag() {
        return dag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Condensation graph (DAG):\n");
        for (var entry : dag.entrySet()) {
            int comp = entry.getKey();
            Set<Integer> neighbors = entry.getValue();
            sb.append("Component ")
                    .append(comp)
                    .append(" ")
                    .append(sccList.get(comp))
                    .append(" → ")
                    .append(neighbors)
                    .append("\n");
        }
        return sb.toString();
    }

}
