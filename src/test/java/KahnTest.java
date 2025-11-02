
import graph.scc.CondensationGraph;
import graph.topo.Kahn;
import metrics.PerfomanceTracker;
import model.Graph;
import model.Node;
import model.Edge;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KahnTest {

    @Test
    void testTopoOrder() {
        List<List<Integer>> sccList = Arrays.asList(
                Arrays.asList(0,1,2),
                Arrays.asList(3)
        );
        Graph g = new Graph(4,true,"edge",0,
                Arrays.asList(new Node(0,1), new Node(1,1), new Node(2,1), new Node(3,1)),
                Arrays.asList(new Edge(2,3,1))
        );

        PerfomanceTracker tracker = new PerfomanceTracker();
        CondensationGraph condensation = new CondensationGraph(g,sccList);
        Kahn kahn = new Kahn(condensation,tracker);
        List<Integer> topo = kahn.sort();

        assertEquals(2, topo.size());
        assertEquals(0, topo.get(0));
        assertEquals(1, topo.get(1));
    }
}
