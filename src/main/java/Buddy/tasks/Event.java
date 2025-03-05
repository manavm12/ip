package Buddy.tasks;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String description,String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String toFileFormat() {
        String status = (isDone ? "1" : "0");
        return "E | " + status + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: "+ to +")";
    }
}
