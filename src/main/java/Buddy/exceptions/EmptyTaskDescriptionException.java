package Buddy.exceptions;

public class EmptyTaskDescriptionException extends BuddyException{
    public EmptyTaskDescriptionException(String taskType) {
        super("Whoa there! A " + taskType + " needs a description. I can't read minds... yet!");
    }
}
