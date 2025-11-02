package algorithms;

import metrics.PerfomanceTracker;
import model.*;
import java.util.*;

public class Kosaraju {

    private Graph graph;
    private boolean[] visited;
    private Deque<Integer> stack;
    private List<List<Integer>> sccList;
    private PerfomanceTracker tracker;

    public Kosaraju(Graph graph, PerfomanceTracker tracker) {
        this.graph = graph;
        this.visited = new boolean[graph.getNodeCount()];
        this.stack = new ArrayDeque<>();
        this.sccList = new ArrayList<>();
        this.tracker = tracker;
    }

    public void firstDfs(){
        Arrays.fill(visited,false);
        for(int i = 0; i < graph.getNodeCount(); i++){
            if(!visited[i]){
                dfs(i);
            }
        }
    }

    private void dfs(int v){
        visited[v] = true;
        tracker.incDFSVisits();

        for(Edge e: graph.getAdjacency().get(v)){
            tracker.incDFSEdges();
            int u = e.getTo();
            if(!visited[u]){
                dfs(u);
            }
        }
        stack.push(v);
        tracker.incStackPushes();
    }

    private Map<Integer, List<Integer>> transpose() {
        Map<Integer, List<Integer>> transposed = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            transposed.put(i, new ArrayList<>());
        }

        for (Edge e : graph.getEdges()) {
            int from = e.getFrom();
            int to = e.getTo();

            transposed.get(from).add(to);
        }

        return transposed;
    }


    private void secondDfs(int v, List<Integer> component, Map<Integer, List<Integer>> transposedAdj){
        visited[v] = true;
        tracker.incDFSVisits();
        component.add(v);

        for(int u: transposedAdj.get(v)){
            tracker.incDFSEdges();
            if(!visited[u]){
                secondDfs(u, component, transposedAdj);
            }
        }
    }


    private void findSccList(){
        tracker.reset();
        tracker.startTimer();

        firstDfs();

        Map<Integer, List<Integer>> transposedAdj = transpose();

        Arrays.fill(visited,false);

        while(!stack.isEmpty()){
            tracker.incStackPops();
            int v = stack.pop();
            if(!visited[v]){
                List<Integer> component = new ArrayList<>();
                secondDfs(v,component,transposedAdj);
                sccList.add(component);
            }
        }

        tracker.stopTimer();
    }

    public List<List<Integer>> getScc(){
        findSccList();
        return sccList;
    }
}
