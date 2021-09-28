package net.decenternet.technicalexam.domain;

public class Task {
    
    private Integer id;
    private String name;
    private String description;
    private Boolean isCompleted;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
