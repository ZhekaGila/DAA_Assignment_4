package io;

import com.google.gson.*;
import model.*;

import java.io.*;
import java.util.*;

public class JsonReader {

    public static Graph readGraphFromJson(String filePath) {
        try {
            JsonObject root = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

            int n = root.get("n").getAsInt();
            boolean directed = root.get("directed").getAsBoolean();
            int source = root.get("source").getAsInt();
            String weightModel = root.get("weight_model").getAsString();

            List<Node> nodes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                nodes.add(new Node(i, 0));
            }

            List<Edge> edges = new ArrayList<>();
            JsonArray edgesJson = root.getAsJsonArray("edges");
            for (JsonElement e : edgesJson) {
                JsonObject edgeObj = e.getAsJsonObject();
                int from = edgeObj.get("u").getAsInt();
                int to = edgeObj.get("v").getAsInt();
                long weight = edgeObj.get("w").getAsLong();
                edges.add(new Edge(from, to, weight));
            }

            return new Graph(n, directed, weightModel, source, nodes, edges);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
