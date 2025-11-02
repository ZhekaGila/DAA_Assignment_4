package graph.topo;

import metrics.PerfomanceTracker;
import graph.scc.CondensationGraph;
import java.util.*;


public class Kahn {
    private CondensationGraph condensation;
    private Map<Integer, Set<Integer>> dag;
    private List<List<Integer>> sccList;
    private PerfomanceTracker tracker;

    public Kahn(CondensationGraph condensation, PerfomanceTracker tracker){
        this.condensation = condensation;
        this.dag = condensation.getDag();
        this.sccList = condensation.getSccList();
        this.tracker = tracker;
    }

    public List<Integer> sort(){
        tracker.reset();
        tracker.startTimer();

        Map<Integer, Integer> inDegree = new HashMap<>();

        for(Integer node: dag.keySet()){
            inDegree.put(node, 0);
        }

        for(Set<Integer> neighbors: dag.values()){
            for(Integer neighbor : neighbors){
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor,0) + 1);
                tracker.incOperations();
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
                tracker.incStackPushes();
            }
        }

        List<Integer> topoOrder = new ArrayList<>();

        while (!queue.isEmpty()) {
            int node = queue.poll();
            tracker.incStackPops();
            topoOrder.add(node);

            for (int neighbor : dag.getOrDefault(node, Collections.emptySet())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                tracker.incOperations();
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (topoOrder.size() != dag.size()) {
            System.out.println("Graph has cycle! We can't order it");
        }

        tracker.stopTimer();

        return topoOrder;
    }

    public void printTopologicalOrder() {
        List<Integer> topoOrder = sort();
        System.out.println("Topological order:");
        for (Integer node: topoOrder) {
            System.out.print(node + " ");
        }
        System.out.println();
        for (int comp : topoOrder) {
            System.out.println("Component " + comp + " -> " + sccList.get(comp));
        }
    }

}
