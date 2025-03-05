package Buddy.tasks;

public class Deadline extends Task {
    private String by;

    public Deadline(String description,String by) {
        super(description);
        this.by = by;
    }

    public String toFileFormat() {
        String status = (isDone ? "1" : "0");
        return "D | " + status + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

}
