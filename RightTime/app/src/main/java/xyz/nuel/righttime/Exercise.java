package xyz.nuel.righttime;

import java.io.Serializable;

public class Exercise implements Serializable {

    private String name;
    private String duration;
    private String reps;
    private String sets;
    private String description;
    private int points;
    //private String notes;

    public Exercise() {

    }



    public Exercise(String description, String duration, String name, int points, String reps, String sets) {
        this.name = name;
        this.duration = duration;
        this.reps = reps;
        this.sets = sets;
        this.description = description;
        this.points = points;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {

        return points;
    }
}