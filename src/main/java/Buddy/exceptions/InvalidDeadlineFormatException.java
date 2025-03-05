package Buddy.exceptions;

public class InvalidDeadlineFormatException extends BuddyException {
    public InvalidDeadlineFormatException() {
        super("Oops! That deadline format looks funky. Try: deadline [task] /by [date]");
    }
}
