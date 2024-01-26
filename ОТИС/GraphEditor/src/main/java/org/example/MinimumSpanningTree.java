package org.example;

import java.util.*;

public class MinimumSpanningTree {
    public static Graph createMinimumSpanningTree(Graph graph) {
        Graph tree = new Graph(graph.getName() + "Tree");

        if (graph.getNodes().isEmpty()) {
            return tree;
        }

        // Найдем вершину с максимальной степенью (больше всего исходящих рёбер)
        GraphNode startNode = findNodeWithMaxDegree(graph);

        if (startNode == null) {
            // Если не удалось найти такую вершину, начнем с первой вершины
            startNode = graph.getNodes().get(0);
        }

        Set<String> visitedNodes = new HashSet<>();
        LinkedList<String> queue = new LinkedList<>();

        visitedNodes.add(startNode.getID());
        tree.addNode(startNode.getID());

        queue.add(startNode.getID());

        while (!queue.isEmpty()) {
            String currentNodeID = queue.poll();
            GraphNode currentNode = graph.getNodeById(currentNodeID);

            for (GraphNode neighbor : getUnvisitedNeighbors(graph, currentNode, visitedNodes)) {
                visitedNodes.add(neighbor.getID());
                tree.addNode(neighbor.getID());
                tree.addEdge(currentNodeID, neighbor.getID());

                queue.add(neighbor.getID());
            }
        }

        return tree;
    }

    private static GraphNode findNodeWithMaxDegree(Graph graph) {
        GraphNode maxDegreeNode = null;
        int maxDegree = -1;

        for (GraphNode node : graph.getNodes()) {
            int degree = graph.getNodeOutDegree(node.getID());
            if (degree > maxDegree) {
                maxDegree = degree;
                maxDegreeNode = node;
            }
        }

        return maxDegreeNode;
    }

    private static List<GraphNode> getUnvisitedNeighbors(Graph graph, GraphNode node, Set<String> visitedNodes) {
        List<GraphNode> neighbors = new ArrayList<>();
        for (GraphEdge edge : graph.getEdges()) {
            String neighborID = edge.getVertex1().equals(node.getID()) ? edge.getVertex2() : edge.getVertex1();
            if (!visitedNodes.contains(neighborID)) {
                neighbors.add(graph.getNodeById(neighborID));
            }
        }
        return neighbors;
    }
}
