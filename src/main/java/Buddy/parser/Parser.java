package Buddy.parser;

import Buddy.exceptions.InvalidDeadlineFormatException;
import Buddy.exceptions.InvalidEventFormatException;

public class Parser {

    public Parser() {}

    /**
     * Extracts the command keyword from the user input.
     *
     * @param input The full user input string.
     * @return The first word of the input, representing the command type.
     */
    public String getCommand(String input) {
        return input.split(" ")[0];
    }

    /**
     * Parses the deadline command input to extract the task name and deadline.
     *
     * @param input The full deadline command input in the format deadline <task> /by <date>.
     * @return A string array containing the task name at index 0 and the deadline at index 1.
     * @throws InvalidDeadlineFormatException If the input format is incorrect.
     */
    public String[] parseDeadline(String input) throws InvalidDeadlineFormatException {
        String[] deadlineDetails = input.substring(9).split("/");

        if (deadlineDetails.length < 2) {
            throw new InvalidDeadlineFormatException();
        }

        return new String[]{deadlineDetails[0].trim(), deadlineDetails[1].trim()};
    }

    /**
     * Parses the event command input to extract the task name, start time, and end time.
     *
     * @param input The full event command input in the format event <task> /from <start> /to <end>.
     * @return A string array containing the task name at index 0, the start time at index 1, and the end time at index 2.
     * @throws InvalidEventFormatException If the input format is incorrect.
     */
    public String[] parseEvent(String input) throws InvalidEventFormatException {
        String[] eventDetails = input.substring(9).split("/");

        if (eventDetails.length < 2) {
            throw new InvalidEventFormatException();
        }

        return new String[]{eventDetails[0].trim(), eventDetails[1].trim(), eventDetails[2].trim()};
    }

    /**
     * Extracts and returns the task index from the user input.
     *
     * @param input The full user command input in the format command <taskIndex>.
     * @return The index of the task.
     * @throws NumberFormatException If the input does not contain a valid integer.
     */
    public int parseTaskIndex(String input)  {
        return Integer.parseInt(input.split(" ")[1]) - 1;
    }



}
