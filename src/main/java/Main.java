import graph.scc.Kosaraju;
import graph.topo.Kahn;
import io.JsonReader;
import metrics.PerfomanceTracker;
import model.Graph;
import graph.scc.CondensationGraph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/tasks.json";

        Graph graph = JsonReader.readGraphFromJson(path);
        PerfomanceTracker kosaTracker = new PerfomanceTracker();
        PerfomanceTracker kahnTracker = new PerfomanceTracker();

        Kosaraju kosaraju = new Kosaraju(graph, kosaTracker);

        List<List<Integer>> sccList = kosaraju.getScc();

        for (List<Integer> component : sccList) {
            System.out.println("SCC: " + component + ", size=" + component.size());
        }
        System.out.println();

        CondensationGraph condGraph = new CondensationGraph(graph, sccList);

        Kahn sorter = new Kahn(condGraph, kahnTracker);
        sorter.printTopologicalOrder();

    }
}