package com.forexai.model;

public class Pattern {
    private String name;
    private String description;
    private String filePath;

    public Pattern(String name, String description, String filePath) {
        this.name = name;
        this.description = description;
        this.filePath = filePath;
    }

    // Getters and setters omitted for brevity

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFilePath() { return filePath; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}
