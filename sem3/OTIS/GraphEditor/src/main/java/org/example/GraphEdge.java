package org.example;

import java.io.Serializable;

public class GraphEdge implements Serializable {
    private String vertex1;
    private String vertex2;
    private String color;


    public GraphEdge(String vertex1, String vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.color = "#000";
    }

    public String getVertex1() {
        return vertex1;
    }
    public void setVertex1(String newVertex1){
        this.vertex1 = newVertex1;
    }

    public void setVertex2(String newVertex2){
        this.vertex2 = newVertex2;
    }

    public String getVertex2() {
        return vertex2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}