package cli;

import io.JsonReader;
import graph.scc.Kosaraju;
import graph.scc.CondensationGraph;
import graph.topo.Kahn;
import graph.dagsp.PathLength;
import model.Graph;
import model.Node;
import metrics.PerfomanceTracker;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Benchmark {

    private static final String DATA_FOLDER = "data/";

    public void runAll() {
        File folder = new File(DATA_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.println("No JSON files found in " + DATA_FOLDER);
            return;
        }

        for (File file : files) {
            System.out.println("=== Processing file: " + file.getName() + " ===");

            Graph graph = JsonReader.readGraphFromJson(file.getPath());
            List<Node> nodes = graph.getNodes();

            PerfomanceTracker kosarajuTracker = new PerfomanceTracker();
            PerfomanceTracker kahnTracker = new PerfomanceTracker();
            PerfomanceTracker dagspTracker = new PerfomanceTracker();

            Kosaraju kosaraju = new Kosaraju(graph,kosarajuTracker);
            List<List<Integer>> sccList = kosaraju.getScc();

            CondensationGraph condensation = new CondensationGraph(graph, sccList);

            Kahn sorter = new Kahn(condensation, kahnTracker);

            List<Integer> topoOrder = sorter.sort();

            PathLength pathLength = new PathLength(condensation, nodes, topoOrder, dagspTracker);
            int sourceScc = topoOrder.get(0);

            Map<Integer, Integer> shortest = pathLength.shortestPaths(sourceScc);
            Map<Integer, Integer> longest = pathLength.longestPaths(sourceScc);



            System.out.println("SCCs:");
            for (List<Integer> comp : sccList) {
                System.out.println(comp + " (size=" + comp.size() + ")");
            }

            System.out.println("Condensation DAG:");
            System.out.println(condensation.getDag());
            System.out.println();

            System.out.println("Topological order of SCCs:");
            System.out.println(topoOrder);
            System.out.println();

            System.out.println("Shortest paths from SCC " + sourceScc + ":");
            System.out.println(shortest);
            System.out.println();

            System.out.println("Longest paths from SCC " + sourceScc + ":");
            System.out.println(longest);
            System.out.println();

            System.out.println("Kosaraju Metrics:");
            System.out.println("Execution time (ns): " + kosarajuTracker.getExecutionTime());
            System.out.println("DFS visits: " + kosarajuTracker.getDFSVisits());
            System.out.println("DFS edges: " + kosarajuTracker.getDFSEdges());
            System.out.println("Stack pushes: " + kosarajuTracker.getStackPushes());
            System.out.println("Stack pops: " + kosarajuTracker.getStackPops());
            System.out.println("Total operations: " + kosarajuTracker.getOperations());
            System.out.println();

            System.out.println("Kahn Metrics:");
            System.out.println("Execution time (ns): " + kahnTracker.getExecutionTime());
            System.out.println("Stack pushes: " + kahnTracker.getStackPushes());
            System.out.println("Stack pops: " + kahnTracker.getStackPops());
            System.out.println("Total operations: " + kosarajuTracker.getOperations());
            System.out.println();

            System.out.println("Dagsp Metrics:");
            System.out.println("Execution time (ns): " + dagspTracker.getExecutionTime());
            System.out.println("Relaxations: " + dagspTracker.getRelaxations());
            System.out.println("Total operations: " + kosarajuTracker.getOperations());
            System.out.println();
        }
    }
}
