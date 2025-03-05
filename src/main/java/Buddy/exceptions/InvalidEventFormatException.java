package Buddy.exceptions;

public class InvalidEventFormatException extends BuddyException {
    public InvalidEventFormatException() {
        super("Whoa, I can’t time travel without proper details! Use: event [task] /from [start] /to [end]");

    }
}
