package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.*;


public class Graph implements Serializable {
    private String name;
    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private int[][] incidenceMatrix;

    public Graph(String name) {
        this.name = name;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public String getName() {
        return name;
    }

    public GraphNode getNodeById(String id) {
        for (GraphNode node : nodes) {
            if (node.getID().equals(id)) {
                return node;
            }
        }
        return null;
    }

    private boolean isNodesConnected(GraphNode node1, GraphNode node2) {
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(node1.getID()) && edge.getVertex2().equals(node2.getID())) {
                return true;
            }
            if (edge.getVertex1().equals(node2.getID()) && edge.getVertex2().equals(node1.getID())) {
                return true;
            }
        }
        return false;
    }


    public boolean addNode(String id) {
        for (GraphNode node : nodes) {
            if (node.getID().equals(id)) {
                System.out.println("Node already exist");
                return false;
            }
        }
        nodes.add(new GraphNode(id));
        updateIncidenceMatrix();
        return true;
    }

    public void setNodeContent(String nodeID, String content) {
        GraphNode node = getNodeById(nodeID);
        if (node != null) {
            node.setContent(content);
        }
    }

    public String getNodeContent(String nodeID) {
        GraphNode node = getNodeById(nodeID);
        if (node != null) {
            return node.getContent();
        } else {
            return null;
        }
    }

    public void deleteNodeContent(String nodeID) {
        GraphNode node = getNodeById(nodeID);
        if (node != null) {
            node.deleteContent();
        }
    }

    public boolean renameNode(String ID, String newID) {
        if (containsNode(ID)) {
            for (GraphEdge edge : edges){
                if (edge.getVertex1().equals(ID)){
                    edge.setVertex1(newID);
                }
                if (edge.getVertex2().equals(ID)){
                    edge.setVertex2(newID);
                }
            }
            nodes.get(getNodeIndex(ID)).setID(newID);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeNode(String id) {
        if (!containsNode(id)) {
            System.out.println("Node does not exist in the graph.");
            return false;
        }

        int nodeIndex = getNodeIndex(id);

        List<GraphEdge> edgesToRemove = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(id) || edge.getVertex2().equals(id)) {
                edgesToRemove.add(edge);
            }
        }
        edges.removeAll(edgesToRemove);
        nodes.remove(nodeIndex);
        return true;
    }

    public void addEdge(String vertex1, String vertex2) {
        if (!containsNode(vertex1) || !containsNode(vertex2)) {
            System.out.println("One or both of the nodes do not exist in the graph.");
            return;
        }

        for (GraphEdge edge : edges) {
            if ((Objects.equals(edge.getVertex1(), vertex1)) && (Objects.equals(edge.getVertex2(), vertex2))) {
                return;
            }
        }

        edges.add(new GraphEdge(vertex1, vertex2));
        updateIncidenceMatrix();
    }

    public boolean removeEdge(String vertex1, String vertex2) {
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) {
                edges.remove(edge);
                return true;
            }
        }
        return false;
    }

    private void updateIncidenceMatrix() {
        int numNodes = nodes.size();
        int numEdges = edges.size();
        int[][] newMatrix = new int[numNodes][numEdges];

        for (int edgeIndex = 0; edgeIndex < numEdges; edgeIndex++) {
            GraphEdge edge = edges.get(edgeIndex);
            int node1Index = getNodeIndex(edge.getVertex1());
            int node2Index = getNodeIndex(edge.getVertex2());
            newMatrix[node1Index][edgeIndex] = -1;
            newMatrix[node2Index][edgeIndex] = 1;
        }

        incidenceMatrix = newMatrix;
    }

    public int getNodeIndex(String id) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getID().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containsNode(String id) {
        return getNodeIndex(id) != -1;
    }

    public void printGraph() {
        System.out.println("Graph Name: " + name);
        System.out.println("Nodes:");
        for (GraphNode node : nodes) {
            System.out.println("Node " + node.getID());
        }

        System.out.println("Edges:");
        for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {
            GraphEdge edge = edges.get(edgeIndex);
            System.out.println("Edge " + edgeIndex + ": " +
                    "Vertex " + edge.getVertex1() + " - Vertex " + edge.getVertex2());
        }

        System.out.println("Incidence Matrix:");
        System.out.print("  ");
        for (int j = 0; j < edges.size(); j++) {
            System.out.print("Edge" + j + " ");
        }
        System.out.println();

        for (int i = 0; i < nodes.size(); i++) {
            System.out.print("Node" + nodes.get(i).getID() + " ");
            for (int j = 0; j < edges.size(); j++) {
                System.out.print(incidenceMatrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public void printIncidenceMatrixToTable(JTable table) {
        updateIncidenceMatrix();

        int numNodes = nodes.size();
        int numEdges = edges.size();

        String[] columnNames = new String[numEdges + 1];
        columnNames[0] = "Nodes";
        for (int j = 1; j < numEdges + 1; j++) {
            columnNames[j] = "Edge " + j;
        }

        Object[][] data = new Object[numNodes][numEdges + 1]; // +1 for row names

        for (int i = 0; i < numNodes; i++) {
            data[i][0] = "Node " + nodes.get(i).getID(); // Set row name
            for (int j = 0; j < numEdges; j++) {
                data[i][j + 1] = incidenceMatrix[i][j];
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }


    public void printGraphToTextArea(JTextArea textArea) {
        textArea.append("Graph Name: " + name + "\n\n");
        textArea.append("Eulerian Graph: " + isEulerianGraph() + "\n\n");
        textArea.append("Node Count: " + nodes.size() + "\n\n");
        textArea.append("Nodes:\n");
        for (GraphNode node : nodes) {
            textArea.append("Node " + node.getID() + "\t Node Degree: " + getNodeDegree(node.getID()) + " (In: " + getNodeInDegree(node.getID()) + " Out :" + getNodeOutDegree(node.getID()) + ")\n");
        }

        textArea.append("\n\n\n");
        textArea.append("Edges Count:" + edges.size() + "\n");
        textArea.append("Edges:\n");
        for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {
            GraphEdge edge = edges.get(edgeIndex);
            textArea.append("Edge " + edgeIndex + ": " +
                    "Vertex " + edge.getVertex1() + " - Vertex " + edge.getVertex2() + "\n");
        }
    }

    //Degree
    public int getNodeDegree(String id) {
        return getNodeInDegree(id) + getNodeOutDegree(id);
    }

    public int getNodeOutDegree(String id) {
        int outDegree = 0;
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(id)) {
                outDegree++;
            }
        }
        return outDegree;
    }

    private int getNodeInDegree(String id) {
        int inDegree = 0;
        for (GraphEdge edge : edges) {
            if (edge.getVertex2().equals(id)) {
                inDegree++;
            }
        }
        return inDegree;
    }
    //Eulirian

    public boolean isEulerianGraph() {
        if (!isConnected()) {
            return false;
        }

        int[] degrees = new int[nodes.size()];
        for (GraphEdge edge : edges) {
            degrees[getNodeIndex(edge.getVertex1())]++;
            degrees[getNodeIndex(edge.getVertex2())]++;
        }

        for (int degree : degrees) {
            if (degree % 2 != 0) {
                return false;
            }
        }

        return true;
    }

    public List<String> findEulerianCycles() {
        List<String> eulerianCycles = new ArrayList<>();
        if (!isConnected()) {
            System.out.println("Graph isn't connected.");
            return eulerianCycles = null;
        }

        for (GraphNode node : nodes) {
            List<String> cycle = findEulerianCycleFromNode(node);
            if (cycle != null) {
                eulerianCycles.add(String.join(" -> ", cycle));
            }
        }
        return eulerianCycles;
    }

    private List<String> findEulerianCycleFromNode(GraphNode startNode) {
        List<String> cycle = new ArrayList<>();
        cycle.add(startNode.getID());

        List<GraphEdge> unusedEdges = new ArrayList<>(edges);
        GraphNode currentNode = startNode;

        while (!unusedEdges.isEmpty()) {
            GraphNode nextNode = null;
            for (GraphEdge edge : unusedEdges) {
                if (edge.getVertex1().equals(currentNode.getID())) {
                    nextNode = getNodeById(edge.getVertex2());
                } else if (edge.getVertex2().equals(currentNode.getID())) {
                    nextNode = getNodeById(edge.getVertex1());
                }

                if (nextNode != null) {
                    cycle.add(nextNode.getID());
                    unusedEdges.remove(edge);
                    currentNode = nextNode;
                    break;
                }
            }

            if (nextNode == startNode) {
                if (unusedEdges.isEmpty()) {
                    return cycle;
                } else {
                    return null;
                }
            }
        }

        return null;
    }

    public boolean isConnected() {
        if (nodes.isEmpty()) {
            return false;
        }

        boolean[] visited = new boolean[nodes.size()];
        dfs(0, visited);

        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }

        return true;
    }

    private void dfs(int nodeIndex, boolean[] visited) {
        visited[nodeIndex] = true;
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(nodes.get(nodeIndex).getID()) && !visited[getNodeIndex(edge.getVertex2())]) {
                dfs(getNodeIndex(edge.getVertex2()), visited);
            } else if (edge.getVertex2().equals(nodes.get(nodeIndex).getID()) && !visited[getNodeIndex(edge.getVertex1())]) {
                dfs(getNodeIndex(edge.getVertex1()), visited);
            }
        }
    }

    private int getEccentricity(GraphNode node) {
        int maxDistance = -1;
        for (GraphNode target : nodes) {
            if (!target.equals(node)) {
                int distance = shortestPathDistance(node.getID(), target.getID());
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    public int shortestPathDistance(String sourceId, String targetId) {
        GraphNode sourceNode = nodes.get(getNodeIndex(sourceId));
        GraphNode targetNode = nodes.get(getNodeIndex(targetId));

        if (sourceNode == null || targetNode == null) {
            return -1;
        }

        Map<GraphNode, Integer> distances = new HashMap<>();
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();

        for (GraphNode node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(sourceNode, 0);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.poll();

            if (currentNode.equals(targetNode)) {
                return distances.get(currentNode);
            }

            visited.add(currentNode);

            for (GraphEdge edge : getAdjacentEdges(currentNode)) {
                GraphNode neighbor = nodes.get(getNodeIndex(edge.getVertex2()));

                if (!visited.contains(neighbor)) {
                    int newDistance = distances.get(currentNode) + 1;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return -1;
    }

    public List<GraphEdge> getAdjacentEdges(GraphNode node) {
        List<GraphEdge> adjacentEdges = new ArrayList<>();
        for (GraphEdge edge : edges) {
            if (edge.getVertex1().equals(node.getID())) {
                adjacentEdges.add(edge);
            }
        }
        return adjacentEdges;
    }

    public int getDiameter() {
        if (edges.isEmpty()) {
            return -1;
        }
        int maxEccentricity = -1;
        for (GraphNode node : nodes) {
            int eccentricity = getEccentricity(node);
            if (eccentricity > maxEccentricity) {
                maxEccentricity = eccentricity;
            }
        }
        return maxEccentricity;
    }

    public int getRadius() {
        if (edges.isEmpty()) {
            return -1;
        }

        int minEccentricity = Integer.MAX_VALUE;
        for (GraphNode node : nodes) {
            int eccentricity = getEccentricity(node);
            if (eccentricity < minEccentricity) {
                minEccentricity = eccentricity;
            }
        }
        return minEccentricity;
    }

    public List<GraphNode> getCenter() {
        int radius = getRadius();
        List<GraphNode> center = new ArrayList<>();
        if (edges.isEmpty()) {
            return center = null;
        }
        for (GraphNode node : nodes) {
            if (getEccentricity(node) == radius) {
                center.add(node);
            }
        }
        return center;
    }

    public List<String> findHamiltonianCycles() {
        List<String> hamiltonianCycles = new ArrayList<>();
        for (GraphNode node : nodes) {
            List<String> path = new ArrayList<>();
            path.add(node.getID());
            findHamiltonianCyclesRecursive(node, new ArrayList<>(nodes), path, hamiltonianCycles);
        }
        return hamiltonianCycles;
    }

    private void findHamiltonianCyclesRecursive(GraphNode current, List<GraphNode> remainingNodes, List<String> currentPath, List<String> hamiltonianCycles) {
        if (remainingNodes.isEmpty()) {
            if (currentPath.size() > 1 && currentPath.get(0).equals(currentPath.get(currentPath.size() - 1))) {
                hamiltonianCycles.add(String.join("->", currentPath));
            }
            return;
        }

        for (GraphNode neighbor : remainingNodes) {
            if (isNodesConnected(current, neighbor)) {
                currentPath.add(neighbor.getID());
                List<GraphNode> remaining = new ArrayList<>(remainingNodes);
                remaining.remove(neighbor);
                findHamiltonianCyclesRecursive(neighbor, remaining, currentPath, hamiltonianCycles);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }


    //TO TREE
    //to default tree
    public Graph toTree() {
        if (!isConnected()) {
            System.out.println("Graph isn't connected. Reducing the graph to a tree is impossible.");
            return null;
        }

        Graph tree = new Graph(this.getName() + "Tree");
        Set<GraphNode> visited = new HashSet<>();
        GraphNode root = this.getNodes().get(0); // Choose an arbitrary node as the root

        // Build the tree using DFS
        buildTree(tree, root, visited);

        return tree;
    }

    private void buildTree(Graph tree, GraphNode current, Set<GraphNode> visited) {
        visited.add(current);

        for (GraphEdge edge : getAdjacentEdges(current)) {
            GraphNode neighbor = getNodeById(edge.getVertex1().equals(current.getID()) ? edge.getVertex2() : edge.getVertex1());

            if (!visited.contains(neighbor)) {
                tree.addNode(current.getID());
                tree.addEdge(current.getID(), neighbor.getID());
                buildTree(tree, neighbor, visited);
            }
        }
    }


    public static Graph vectorProduct(Graph firstGraph, Graph secondGraph) {
        Graph productGraph = new Graph(firstGraph.getName() + "VectorProduct" + secondGraph.getName());

        // Checking nodes
        if (firstGraph.getNodes().isEmpty() || secondGraph.getNodes().isEmpty()) {
            System.out.println("One or both of the graphs do not have any nodes.");
            return productGraph;
        }

        for (GraphNode node1 : firstGraph.getNodes()) {
            for (GraphNode node2 : secondGraph.getNodes()) {
                GraphNode productNode = new GraphNode(node1.getID() + " x " + node2.getID());
                productGraph.addNode(productNode.getID());
            }
        }

        for (GraphNode node1 : firstGraph.getNodes()) {
            for (GraphNode node2 : secondGraph.getNodes()) {
                for (GraphEdge edge1 : firstGraph.getEdges()) {
                    for (GraphEdge edge2 : secondGraph.getEdges()) {
                        if (edge1.getVertex1().equals(node1.getID()) && edge2.getVertex1().equals(node2.getID())) {
                            String sourceNodeID = node1.getID() + " x " + node2.getID();
                            String targetNodeID = productGraph.getNodeById(edge1.getVertex2() + " x " + edge2.getVertex2()).getID();
                            productGraph.addEdge(sourceNodeID, targetNodeID);
                        }
                    }
                }
            }
        }

        return productGraph;
    }


    public static Graph cartesianProduct(Graph graph1, Graph graph2) {
        Graph productGraph = new Graph(graph1.getName() + " x " + graph2.getName());

        for (GraphNode node1 : graph1.getNodes()) {
            for (GraphNode node2 : graph2.getNodes()) {
                GraphNode productNode = new GraphNode(node1.getID() + " x " + node2.getID());
                productGraph.addNode(productNode.getID());
            }
        }

        for (GraphNode node1 : graph1.getNodes()) {
            for (GraphNode node2 : graph2.getNodes()) {
                for (GraphEdge edge1 : graph1.getEdges()) {
                    for (GraphEdge edge2 : graph2.getEdges()) {
                        if (edge1.getVertex1().equals(node1.getID()) && edge2.getVertex1().equals(node2.getID())) {
                            String sourceNodeID = node1.getID() + " x " + node2.getID();
                            String targetNodeID = productGraph.getNodeById(edge1.getVertex2() + " x " + edge2.getVertex1()).getID();
                            productGraph.addEdge(sourceNodeID, targetNodeID);
                        }

                        if (edge1.getVertex1().equals(node1.getID()) && edge2.getVertex1().equals(node2.getID())) {
                            String sourceNodeID = node1.getID() + " x " + node2.getID();
                            String targetNodeID = productGraph.getNodeById(edge1.getVertex1() + " x " + edge2.getVertex2()).getID();
                            productGraph.addEdge(sourceNodeID, targetNodeID);
                        }
                    }
                }
            }
        }

        return productGraph;
    }
}
