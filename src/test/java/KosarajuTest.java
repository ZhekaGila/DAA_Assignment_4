
import graph.scc.Kosaraju;
import metrics.PerfomanceTracker;
import model.Graph;
import model.Node;
import model.Edge;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KosarajuTest {

    @Test
    void testSimpleSCC() {
        List<Node> nodes = Arrays.asList(
                new Node(0, 1),
                new Node(1, 1),
                new Node(2, 1),
                new Node(3, 1),
                new Node(4, 1)
        );
        List<Edge> edges = Arrays.asList(
                new Edge(0,1,1),
                new Edge(1,2,1),
                new Edge(2,0,1),
                new Edge(3,4,1)
        );
        Graph graph = new Graph(5,true,"edge",0,nodes,edges);

        PerfomanceTracker tracker = new PerfomanceTracker();

        Kosaraju kosaraju = new Kosaraju(graph,tracker);
        List<List<Integer>> scc = kosaraju.getScc();

        assertEquals(3, scc.size());
    }
}
