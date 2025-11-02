import io.JsonReader;
import model.Graph;
import algorithms.Kosaraju;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/tasks.json";

        Graph graph = JsonReader.readGraphFromJson(path);

        Kosaraju kosaraju = new Kosaraju(graph);

        List<List<Integer>> sccList = kosaraju.getScc();

        for (List<Integer> component : sccList) {
            System.out.println("SCC: " + component + ", size=" + component.size());
        }

    }
}