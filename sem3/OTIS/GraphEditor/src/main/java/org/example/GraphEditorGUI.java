package org.example;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GraphEditorGUI {
    private JFrame frame;
    private List<Graph> graphs;
    private Graph currentGraph = null;
    private JTextArea outputTextArea;
    private JComboBox<String> graphComboBox;
    private JTable incidenceMatrixTable;

    public GraphEditorGUI() {
        graphs = new ArrayList<>();
        frame = new JFrame("Graph Editor");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(2000, 1000);

        URL iconURL = Main.class.getResource("/icon.ico");
        ImageIcon icon = new ImageIcon(iconURL);

        // Устанавливаем иконку для JFrame
        frame.setIconImage(icon.getImage());

        // Устанавливаем иконку для JFrame
        frame.setIconImage(icon.getImage());


        //context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save graph as file");
        saveMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                GraphFile.saveGraph(currentGraph);
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
        });

        JMenuItem openMenuItem = new JMenuItem("Open file");
        openMenuItem.addActionListener(e -> {
            GraphFile.openFile(graphs);
            graphComboBox.addItem(graphs.get(graphs.size() - 1).getName());
        });

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(frame, "You might have not saved some graph, are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        contextMenu.add(saveMenuItem);
        contextMenu.add(openMenuItem);
        contextMenu.add(exitMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);

        frame.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt)) {
                    contextMenu.show(frame, 10, 10);
                }
            }
        });


        //header
        JPanel buttonPanel = new JPanel();
        JButton createGraphButton = new JButton("Create a new graph");
        JMenu nodeMenu = new JMenu("Node Actions");
        //nodeActions
        JMenuItem addNodeMenuItem = new JMenuItem("Add Node");
        JMenuItem setNodeContentMenuItem = new JMenuItem("Set Node Content");
        JMenuItem getNodeContentMenuItem = new JMenuItem("Get Node Content");
        JMenuItem delNodeContentMenuItem = new JMenuItem("Delete Node Content");
        JMenuItem renameNodeMenuItem = new JMenuItem("Rename Node");
        JMenuItem setNodeColorItem = new JMenuItem("Set Node Color");
        JMenuItem getNodeDegreeItem = new JMenuItem("Get Node Degree");
        JMenuItem removeNodeMenuItem = new JMenuItem("Remove Node");
        JMenu edgeMenu = new JMenu("Edge Actions");
        //edgeActions
        JMenuItem addEdgeMenuItem = new JMenuItem("Add Edge");
        JMenuItem setEdgeColorItem = new JMenuItem("Set Edge Color");
        JMenuItem removeEdgeMenuItem = new JMenuItem("Remove Edge");
        JMenu actionsMenu = new JMenu("Graph Actions");
        //graphActions
        JMenuItem findEulirianCycleItem = new JMenuItem("Find Eulirian Cycle");
        JMenuItem findDiameterItem = new JMenuItem("Find Diameter");
        JMenuItem findRadiusItem = new JMenuItem("Find Radius");
        JMenuItem findCenterItem = new JMenuItem("Find Center");
        JMenuItem findVectorProductItem = new JMenuItem("Vector Product");
        JMenuItem findCartesianProductItem = new JMenuItem("Cartesian Product");
        JMenuItem findHamiltonCycleItem = new JMenuItem("Find Hamilton Cycle");
        JMenuItem createTreeGraphItem = new JMenuItem("Create Graph Tree");

        JButton visualizeGraphButton = new JButton("Visualize Graph");


        graphComboBox = new JComboBox<>();
        graphComboBox.addItem("Select a graph");
        graphComboBox.addActionListener(e -> {
            int selectedIndex = graphComboBox.getSelectedIndex();
            if (selectedIndex > 0) {
                currentGraph = graphs.get(selectedIndex - 1);
                updateGraphInformation();
            }
        });

        createGraphButton.addActionListener(e -> {
            String graphName = JOptionPane.showInputDialog(frame, "Enter the name for the new graph:");
            if (graphName != null && !graphName.isEmpty()) {
                Graph newGraph = new Graph(graphName);
                graphs.add(newGraph);
                graphComboBox.addItem(newGraph.getName());
                JOptionPane.showMessageDialog(frame, "Graph " + graphName + " created.");
            }
        });

        addNodeMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                if (nodeName != null && !nodeName.isEmpty()) {
                    if (currentGraph.addNode(nodeName)) {
                        JOptionPane.showMessageDialog(frame, "New graph node " + nodeName + " created.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Node already exist.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        setNodeContentMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                String nodeContent = JOptionPane.showInputDialog(frame, "Enter the node content:");
                if (currentGraph.getNodeIndex(nodeName) != -1 && nodeName != null && !nodeName.isEmpty() && !nodeContent.isEmpty()) {
                    currentGraph.setNodeContent(nodeName, nodeContent);
                    JOptionPane.showMessageDialog(frame, "Node has been set to: " + nodeContent + ".");
                } else {
                    JOptionPane.showMessageDialog(frame, "Node doesn't exist.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        getNodeContentMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                if (currentGraph.getNodeIndex(nodeName) != -1 && nodeName != null && !nodeName.isEmpty()) {
                    String nodeContent = currentGraph.getNodeContent(nodeName);
                    if (nodeContent != null) {
                        JOptionPane.showMessageDialog(frame, "Node content: " + nodeContent + ".");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Node content is empty.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Node doesn't exist.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        delNodeContentMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                if (currentGraph.getNodeIndex(nodeName) != -1 && nodeName != null && !nodeName.isEmpty()) {
                    currentGraph.deleteNodeContent(nodeName);
                    JOptionPane.showMessageDialog(frame, "Node content has been deleted.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Node doesn't exist.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });


        removeNodeMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                if (nodeName != null && !nodeName.isEmpty()) {
                    if (currentGraph.removeNode(nodeName)) {
                        JOptionPane.showMessageDialog(frame, "Node " + nodeName + " deleted.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Node doesn't exist.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        renameNodeMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                String newNodeName = JOptionPane.showInputDialog(frame, "Enter the new node name:");
                if (nodeName != null && !nodeName.isEmpty() && newNodeName != null && !newNodeName.isEmpty()) {
                    if (currentGraph.renameNode(nodeName, newNodeName)) {
                        JOptionPane.showMessageDialog(frame, "Node " + nodeName + " has been renamed to"
                                + newNodeName + ".");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Node" + nodeName + "doesn't exist.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        setNodeColorItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                String color = JOptionPane.showInputDialog(frame, "Enter the HEX color code:");
                if (nodeName != null && !nodeName.isEmpty() && color != null && !color.isEmpty()) {
                    currentGraph.getNodes().get(currentGraph.getNodeIndex(nodeName)).setColor(color);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        getNodeDegreeItem.addActionListener(e -> {
            if (currentGraph != null) {
                String nodeName = JOptionPane.showInputDialog(frame, "Enter the node name:");
                if (nodeName != null && !nodeName.isEmpty()) {
                    if (currentGraph.containsNode(nodeName)) {
                        JOptionPane.showMessageDialog(frame, nodeName + " Degree: " + currentGraph.getNodeDegree(nodeName));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Node " + nodeName + " doesn't exist");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });


        addEdgeMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String firstNode = JOptionPane.showInputDialog(frame, "Enter the first node's name:");
                String secondNode = JOptionPane.showInputDialog(frame, "Enter the second node's name:");
                int directedEdge = JOptionPane.showConfirmDialog(frame, "Is the edge directed?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (firstNode != null && !firstNode.isEmpty() && secondNode != null && !secondNode.isEmpty()) {
                    if (directedEdge == JOptionPane.YES_OPTION) {
                        currentGraph.addEdge(firstNode, secondNode);
                    } else {
                        currentGraph.addEdge(firstNode, secondNode);
                        currentGraph.addEdge(secondNode, firstNode);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        setEdgeColorItem.addActionListener(e -> {
            if (currentGraph != null) {
                String firstNode = JOptionPane.showInputDialog(frame, "Enter the first node's name:");
                String secondNode = JOptionPane.showInputDialog(frame, "Enter the second node's name:");
                String color = JOptionPane.showInputDialog(frame, "Enter the HEX color code:");
                if (firstNode != null && !firstNode.isEmpty() && secondNode != null && !secondNode.isEmpty()) {
                    for (GraphEdge edge : currentGraph.getEdges()) {
                        if (edge.getVertex1().equals(firstNode) && edge.getVertex2().equals(secondNode)) {
                            edge.setColor(color);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        removeEdgeMenuItem.addActionListener(e -> {
            if (currentGraph != null) {
                String firstNode = JOptionPane.showInputDialog(frame, "Enter the first node's name:");
                String secondNode = JOptionPane.showInputDialog(frame, "Enter the second node's name:");
                if (firstNode != null && !firstNode.isEmpty() && secondNode != null && !secondNode.isEmpty()) {
                    if (currentGraph.removeEdge(firstNode, secondNode)) {
                        JOptionPane.showMessageDialog(frame, "Edge successfully deleted.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Edge doesn't exist.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findEulirianCycleItem.addActionListener(e -> {
            if (currentGraph != null) {
                List<String> eulirianCycles = currentGraph.findEulerianCycles();
                if (eulirianCycles.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Graph doesn't have Eulirian Cycle.");
                } else {
                    StringBuilder cycles = new StringBuilder("");
                    for (String cycle : eulirianCycles) {
                        cycles.append(cycle).append('\n');
                    }
                    JOptionPane.showMessageDialog(frame, "Eulirian Cycles: \n" + cycles);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findDiameterItem.addActionListener(e -> {
            if (currentGraph != null) {
                int diameter = currentGraph.getDiameter();
                if (diameter == -1) {
                    JOptionPane.showMessageDialog(frame, "Graph doesn't have any edge.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Graph Diameter: " + diameter);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findRadiusItem.addActionListener(e -> {
            if (currentGraph != null) {
                int radius = currentGraph.getRadius();
                if (radius == -1) {
                    JOptionPane.showMessageDialog(frame, "Graph doesn't have any edge.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Graph Radius: " + radius);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findCenterItem.addActionListener(e -> {
            if (currentGraph != null) {
                List<GraphNode> centerNodes = currentGraph.getCenter();
                if (centerNodes == null) {
                    JOptionPane.showMessageDialog(frame, "Graph doesn't have center.");
                } else {
                    StringBuilder output = new StringBuilder("");
                    for (GraphNode node : centerNodes) {
                        output.append(node.getID()).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, "Graph Center:\n " + output);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findVectorProductItem.addActionListener(e -> {
            String firstGraphName = JOptionPane.showInputDialog(frame, "Enter the first graph name:");
            String secondGraphName = JOptionPane.showInputDialog(frame, "Enter the second graph name:");

            Graph firstGraph = null;
            Graph secondGraph = null;

            for (Graph graph : graphs) {
                if (firstGraphName.equals(graph.getName())) {
                    firstGraph = graph;
                }
                if (secondGraphName.equals(graph.getName())) {
                    secondGraph = graph;
                }
            }

            if (firstGraph != null && secondGraph != null) {
                Graph vectorProductGraph = Graph.vectorProduct(firstGraph, secondGraph);
                graphs.add(vectorProductGraph);
                graphComboBox.addItem(vectorProductGraph.getName());
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findCartesianProductItem.addActionListener(e -> {
            String firstGraphName = JOptionPane.showInputDialog(frame, "Enter the first graph name:");
            String secondGraphName = JOptionPane.showInputDialog(frame, "Enter the second graph name:");

            Graph firstGraph = null;
            Graph secondGraph = null;

            for (Graph graph : graphs) {
                if (firstGraphName.equals(graph.getName())) {
                    firstGraph = graph;
                }
                if (secondGraphName.equals(graph.getName())) {
                    secondGraph = graph;
                }
            }

            if (firstGraph != null && secondGraph != null) {
                Graph cartesianProductGraph = Graph.cartesianProduct(firstGraph, secondGraph);
                graphs.add(cartesianProductGraph);
                graphComboBox.addItem(cartesianProductGraph.getName());
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        findHamiltonCycleItem.addActionListener(e -> {
            if (currentGraph != null) {
                List<String> hamiltonCycles = currentGraph.findHamiltonianCycles();
                if (hamiltonCycles.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Graph doesn't contains any Hamilton cycle");
                    return;
                }
                StringBuilder cycles = new StringBuilder();
                for (String cycle : hamiltonCycles) {
                    cycles.append(cycle).append("\n");
                }
                JOptionPane.showMessageDialog(frame, "Hamilton Cycles: \n" + cycles + "\n\n");
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });

        createTreeGraphItem.addActionListener(e -> {
            if (currentGraph != null) {
                Graph treeGraph;
                treeGraph = currentGraph.toTree();
                if (treeGraph == null) {
                    JOptionPane.showMessageDialog(frame, "Graph isn't connected. Reducing a graph to a tree is impossible.");
                    return;
                }
                treeGraph = MinimumSpanningTree.createMinimumSpanningTree(currentGraph);
                graphs.add(treeGraph);
                graphComboBox.addItem(treeGraph.getName());
                JOptionPane.showMessageDialog(frame, "Graph " + treeGraph.getName() + " created.");
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
            updateGraphInformation();
        });


        visualizeGraphButton.addActionListener(e -> {
            if (currentGraph != null) {
                visualizeGraph(currentGraph);
            } else {
                JOptionPane.showMessageDialog(frame, "No graph is selected. Create or select a graph first.");
            }
        });

        buttonPanel.add(createGraphButton);

        buttonPanel.add(graphComboBox);
        buttonPanel.add(visualizeGraphButton);

        nodeMenu.add(addNodeMenuItem);
        nodeMenu.add(setNodeContentMenuItem);
        nodeMenu.add(getNodeContentMenuItem);
        nodeMenu.add(delNodeContentMenuItem);
        nodeMenu.add(renameNodeMenuItem);
        nodeMenu.add(setNodeColorItem);
        nodeMenu.add(getNodeDegreeItem);
        nodeMenu.add(removeNodeMenuItem);

        edgeMenu.add(addEdgeMenuItem);
        edgeMenu.add(setEdgeColorItem);
        edgeMenu.add(removeEdgeMenuItem);

        actionsMenu.add(findEulirianCycleItem);
        actionsMenu.add(findDiameterItem);
        actionsMenu.add(findRadiusItem);
        actionsMenu.add(findCenterItem);
        actionsMenu.add(findVectorProductItem);
        actionsMenu.add(findCartesianProductItem);
        actionsMenu.add(findHamiltonCycleItem);
        actionsMenu.add(createTreeGraphItem);

        menuBar.add(fileMenu);
        menuBar.add(nodeMenu);
        menuBar.add(edgeMenu);
        menuBar.add(actionsMenu);

        frame.setJMenuBar(menuBar);
        frame.add(buttonPanel, BorderLayout.NORTH);

        //body
        outputTextArea = new JTextArea();
        frame.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        // Create a JTable for the incidence matrix
        incidenceMatrixTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(incidenceMatrixTable);
        tableScrollPane.setPreferredSize(new Dimension(500, 700));
        frame.add(tableScrollPane, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private void updateGraphInformation() {
        outputTextArea.setText(""); // Clear the text area
        if (currentGraph != null) {
            currentGraph.printGraphToTextArea(outputTextArea);
            currentGraph.printIncidenceMatrixToTable(incidenceMatrixTable);
        }
    }

    private void visualizeGraph(Graph graph) {
        JFrame graphFrame = new JFrame("Graph Visualization");
        graphFrame.setSize(1200, 900);

        mxGraph mxgraph = new mxGraph();
        Object parent = mxgraph.getDefaultParent();
        mxGraphComponent graphComponent = new mxGraphComponent(mxgraph);
        graphFrame.add(graphComponent);

        mxgraph.getModel().beginUpdate();

        try {
            Object[] vertices = new Object[graph.getNodes().size()];
            for (int i = 0; i < graph.getNodes().size(); i++) {
                vertices[i] = (mxCell) mxgraph.insertVertex(parent, null, graph.getNodes().get(i).getID(), 0, 0, 30, 30, "strokeColor=" + graph.getNodes().get(i).getColor() + ";shape=ellipse");
            }

            for (GraphEdge edge : graph.getEdges()) {
                int sourceIndex = graph.getNodeIndex(edge.getVertex1());
                int targetIndex = graph.getNodeIndex(edge.getVertex2());
                mxgraph.insertEdge(parent, null, "", vertices[sourceIndex], vertices[targetIndex], "strokeColor=" + edge.getColor());
            }
        } finally {
            mxgraph.getModel().endUpdate();
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(mxgraph);
        layout.execute(parent);

        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setVisible(true);
    }
}