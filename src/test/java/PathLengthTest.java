
import graph.dagsp.PathLength;
import metrics.PerfomanceTracker;
import model.Node;
import model.Graph;
import model.Edge;
import graph.scc.CondensationGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathLengthTest {

    @Test
    void testShortestAndLongestPath() {
        List<Node> nodes = Arrays.asList(
                new Node(0, 2),
                new Node(1, 3),
                new Node(2, 5)
        );
        List<Edge> edges = Arrays.asList(
                new Edge(0,1,1),
                new Edge(1,2,1)
        );
        Graph g = new Graph(3,true,"edge",0,nodes,edges);

        List<List<Integer>> sccList = Arrays.asList(
                Arrays.asList(0),
                Arrays.asList(1),
                Arrays.asList(2)
        );

        PerfomanceTracker tracker = new PerfomanceTracker();
        CondensationGraph condensation = new CondensationGraph(g,sccList);
        List<Integer> topoOrder = Arrays.asList(0,1,2);

        PathLength pl = new PathLength(condensation, nodes, topoOrder, tracker);

        assertEquals(2+3+5, pl.longestPaths(0).get(2)); // проверяем длину пути
        assertEquals(2, pl.shortestPaths(0).get(0)); // путь до первой вершины
    }
}
