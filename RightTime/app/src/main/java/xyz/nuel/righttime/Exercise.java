package xyz.nuel.righttime;

public class Exercise {

    private String name;
    private String duration;
    private String reps;
    private String sets;

    public Exercise() {
    }

    public Exercise(String duration, String name, String reps, String sets) {
        this.name = name;
        this.duration = duration;
        this.reps = reps;
        this.sets = sets;
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


}
