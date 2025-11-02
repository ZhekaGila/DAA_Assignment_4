package algorithms;

import model.*;
import java.util.*;

public class Kosaraju {

    private Graph graph;
    private boolean[] visited;
    private Deque<Integer> stack;
    private List<List<Integer>> sccList;


    public Kosaraju(Graph graph) {
        this.graph = graph;
        this.visited = new boolean[graph.getNodeCount()];
        this.stack = new ArrayDeque<>();
        this.sccList = new ArrayList<>();
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
        for(Edge e: graph.getAdjacency().get(v)){
            int u = e.getTo();
            if(!visited[u]){
                dfs(u);
            }
        }
        stack.push(v);
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
        component.add(v);

        for(int u: transposedAdj.get(v)){
            if(!visited[u]){
                secondDfs(u, component, transposedAdj);
            }
        }
    }


    private void findSccList(){

        firstDfs();

        Map<Integer, List<Integer>> transposedAdj = transpose();

        Arrays.fill(visited,false);

        while(!stack.isEmpty()){
            int v = stack.pop();
            if(!visited[v]){
                List<Integer> component = new ArrayList<>();
                secondDfs(v,component,transposedAdj);
                sccList.add(component);
            }
        }
    }

    public List<List<Integer>> getScc(){
        findSccList();
        return sccList;
    }
}
