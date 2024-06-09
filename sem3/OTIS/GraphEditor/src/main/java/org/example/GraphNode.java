package org.example;

import java.io.Serializable;

public class GraphNode implements Serializable {
    private String id;
    private String color;

    private String content;

    public GraphNode(String ID) {
        this.id = ID;
        this.color = "#000";
        this.content = null;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getID() {
        return id;
    }

    public void setID(String newID) {
        this.id = newID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void deleteContent() {
        this.content = null;
    }
}
