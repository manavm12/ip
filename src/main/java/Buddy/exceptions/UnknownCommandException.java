package Buddy.exceptions;

public class UnknownCommandException extends BuddyException {
    public UnknownCommandException() {
        super("Uh-oh! That command flew right over my head. Try 'todo', 'deadline', or 'event'!");
    }
}
