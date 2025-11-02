import io.JsonReader;
import metrics.PerfomanceTracker;
import model.Graph;
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

        System.out.println("dfsVisits: " + kosaTracker.getDFSVisits());
        System.out.println("dfsEdges: " + kosaTracker.getDFSEdges());
        System.out.println("stackPushes: " + kosaTracker.getStackPushes());
        System.out.println("stackPops: " + kosaTracker.getStackPops());
        System.out.println("operations: " + kosaTracker.getOperations());
        System.out.println("executionTime: " + kosaTracker.getExecutionTime() + " Ns");
    }
}