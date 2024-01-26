package org.example;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.List;

public class GraphFile {
    public static void saveGraph(Graph graphToSave) {
        JFrame frame = new JFrame("Save graph as");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph Editor files (*.ge)", "ge");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".ge")) {
                file = new File(file.toString() + ".ge");
            }
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                outputStream.writeObject(graphToSave);
                JOptionPane.showMessageDialog(frame, "Graph has been saved as: " + file.getName());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error");
            }
        }
    }

    public static void openFile(List<Graph> graphs) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            graphs.add(openObjectFile(selectedFile));
        }
    }

    private static Graph openObjectFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object loadedObject = objectInputStream.readObject();

            // Процесс загруженного объекта, например, отображение его данных
            if (loadedObject instanceof Graph) {
                Graph readedGraph = (Graph) loadedObject;
                JOptionPane.showMessageDialog(null, "Graph" + readedGraph.getName() + " successfully loaded ", "Success", JOptionPane.INFORMATION_MESSAGE);
                return readedGraph;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while opening the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
