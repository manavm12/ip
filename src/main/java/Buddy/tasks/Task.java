package Buddy.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String toFileFormat() {
        return "";
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "["+ this.getStatusIcon() + "] " + this.description;
    }
}
