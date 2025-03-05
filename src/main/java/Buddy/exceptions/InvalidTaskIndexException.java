package Buddy.exceptions;

public class InvalidTaskIndexException extends BuddyException {
    public InvalidTaskIndexException() {
        super("Oops! That task index doesn't exist. Please enter a valid number.");
    }
}