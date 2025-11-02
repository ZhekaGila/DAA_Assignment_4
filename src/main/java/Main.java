import io.JsonReader;
import metrics.PerfomanceTracker;
import model.Graph;
import model.CondensationGraph;
import algorithms.Kosaraju;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/tasks.json";

        Graph graph = JsonReader.readGraphFromJson(path);
        PerfomanceTracker kosaTracker = new PerfomanceTracker();

        Kosaraju kosaraju = new Kosaraju(graph, kosaTracker);

        List<List<Integer>> sccList = kosaraju.getScc();

        for (List<Integer> component : sccList) {
            System.out.println("SCC: " + component + ", size=" + component.size());
        }

        CondensationGraph condGraph = new CondensationGraph(graph, sccList);

        System.out.println(condGraph);

    }
}