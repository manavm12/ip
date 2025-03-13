package Buddy.tasks;

import Buddy.exceptions.EmptyTaskDescriptionException;
import Buddy.exceptions.InvalidDeadlineFormatException;
import Buddy.exceptions.InvalidEventFormatException;
import Buddy.exceptions.InvalidTaskIndexException;
import Buddy.exceptions.UnknownCommandException;
import Buddy.exceptions.BuddyException;

import Buddy.ui.Ui;
import Buddy.storage.Storage;
import Buddy.parser.Parser;

import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class TaskList {

    private final Ui ui;
    private final Storage storage;
    private final Parser parser;
    private final ArrayList<Task> taskList;

    public TaskList(Ui ui, Storage storage, Parser parser) {
        this.ui = ui;
        this.storage = storage;
        this.taskList = storage.loadTasksFromFile();
        this.parser = parser;
    }

    /**
     * Adds a task to the task list based on user input.
     * <p>
     * Determines the type of task (todo, deadline, or event) and adds it accordingly.
     * After adding, the updated list is saved to a file.
     *
     * @param input The user input specifying the task to be added.
     * @throws UnknownCommandException If the input does not match any valid task type.
     */
    public void addToList(String input) throws BuddyException {
        if (input.startsWith("todo")) {
            addTodo(input);
        } else if (input.startsWith("deadline")) {
            addDeadline(input);
        } else if (input.startsWith("event")) {
            addEvent(input);
        } else {
            throw new UnknownCommandException();
        }

        storage.saveTasksToFile(taskList);
        ui.printDivider();
    }

    /**
     * Adds a new todo task to the task list.
     * <p>
     * Extracts the task description from the input and creates a new {@link Todo} task.
     * If the input is only "todo" without a description, an exception is thrown.
     *
     * @param input The user input specifying the todo task.
     * @throws EmptyTaskDescriptionException If the task description is missing.
     */
    private void addTodo(String input) throws EmptyTaskDescriptionException {
        if (input.trim().equals("todo")) {
            throw new EmptyTaskDescriptionException("todo");
        }

        String task = input.substring(5);
        taskList.add(new Todo(task));

        ui.addToListResponse(taskList);
    }

    /**
     * Adds a new deadline task to the task list.
     * <p>
     * Extracts the task description and deadline from the input and creates a new {@link Deadline} task.
     * If the input lacks a description or is incorrectly formatted, an exception is thrown.
     *
     * @param input The user input specifying the deadline task.
     * @throws EmptyTaskDescriptionException If the task description is missing.
     * @throws InvalidDeadlineFormatException If the deadline format is incorrect.
     */
    private void addDeadline(String input) throws EmptyTaskDescriptionException, InvalidDeadlineFormatException {
        if (input.trim().equals("deadline")) {
            throw new EmptyTaskDescriptionException("deadline");
        }

        String[] deadlineDetails = parser.parseDeadline(input);

        String taskName = deadlineDetails[0];
        String taskDeadline = deadlineDetails[1];

        if (taskDeadline.startsWith("by")) {
            taskDeadline = formatDate(taskDeadline.substring(3));
        }

        taskList.add(new Deadline(taskName, taskDeadline));

        ui.addToListResponse(taskList);
    }

    /**
     * Adds a new event task to the task list.
     * <p>
     * Extracts the task description, start time, end time from the input
     * and creates a new {@link Event} task.
     * If the input lacks a description or is incorrectly formatted, an exception is thrown.
     *
     * @param input The user input specifying the deadline task.
     * @throws EmptyTaskDescriptionException If the task description is missing.
     * @throws InvalidEventFormatException If the deadline format is incorrect.
     */
    private void addEvent(String input) throws EmptyTaskDescriptionException, InvalidEventFormatException {
        if (input.trim().equals("event")){
            throw new EmptyTaskDescriptionException("event");
        }

        String[] eventDetails = parser.parseEvent(input);

        String taskName = eventDetails[0];
        String taskStart = eventDetails[1];
        String taskEnd = eventDetails[2];

        if (taskStart.startsWith("from")){
            taskStart = formatDate(taskStart.substring(5));
        }
        if (taskEnd.startsWith("to")){
            taskEnd = formatDate(taskEnd.substring(3));
        }

        taskList.add(new Event(taskName,taskStart,taskEnd));

        ui.addToListResponse(taskList);
    }

    /**
     * Marks a task as completed.
     * <p>
     * Retrieves the task at the specified index and marks it as done.
     * If the index is invalid, an exception is thrown. Saves the updated list to file.
     *
     * @param input The user input specifying the task index to mark as done.
     * @throws InvalidTaskIndexException If the task index is out of range.
     * @throws NumberFormatException If the input is not a valid number.
     */
    public void markTaskAsDone(String input) throws InvalidTaskIndexException {
        try {
            int index = parser.parseTaskIndex(input);
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsDone();
            storage.saveTasksToFile(taskList);

            ui.markTaskResponse(task);

        } catch (NumberFormatException e) {
            ui.markTaskWarning();
        }
    }

    /**
     * Unmarks a task, indicating the task is incomplete.
     * <p>
     * Retrieves the task at the specified index and marks it as not done.
     * If the index is invalid, an exception is thrown. Saves the updated list to file.
     *
     * @param input The user input specifying the task index to unmark.
     * @throws InvalidTaskIndexException If the task index is out of range.
     * @throws NumberFormatException If the input is not a valid number.
     */
    public void unMarkTaskAsDone(String input) throws InvalidTaskIndexException {
        try {
            int index = parser.parseTaskIndex(input);
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsNotDone();
            storage.saveTasksToFile(taskList);

            ui.unmarkTaskResponse(task);

        } catch (NumberFormatException e) {
            ui.unmarkTaskWarning();
        }
    }

    /**
     * Deletes a task from the task list.
     * <p>
     * Retrieves the task at the specified index and removes it from the list.
     * If the index is invalid, an exception is thrown. Saves the updated list to file.
     *
     * @param input The user input specifying the task index to delete.
     * @throws InvalidTaskIndexException If the task index is out of range.
     * @throws NumberFormatException If the input is not a valid number.
     */
    public void deleteTasks(String input) throws InvalidTaskIndexException {
        try {
            int index = parser.parseTaskIndex(input);
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            taskList.remove(task);
            storage.saveTasksToFile(taskList);

            ui.deleteTaskResponse(task,taskList);

        } catch (NumberFormatException e) {
            ui.deleteTaskWarning();
        }
    }

    /**
     * Retrieves the current list of tasks.
     * @return The list of tasks.
     */
    public  ArrayList<Task> getTaskList(){
        return taskList;
    }

    /**
     * Searches for tasks that contain the given keyword in their description.
     * <p>
     * Iterates through the list of tasks and prints all tasks whose description
     * contains the specified keyword. If no matching tasks are found,
     * a message is displayed.
     *
     * @param input The user input containing the "find" command followed by the keyword.
     */
    public void findTask(String input) {
        String keyword = input.split(" ")[1];
        ui.findTaskMessage();

        int index = 1;
        for (Task task : taskList) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(index + "." + task);
                index++;
            }
        }

        if (index == 1) {
            ui.noTaskFoundMessage();
        }

    }

    /**
     * Converts a date string from a recognized input format into a readable format.
     * <p>
     * The method attempts to parse the input date string using supported formats.
     * If successful, it returns the date formatted as "MMM dd yyyy, hh:mma".
     * If the input does not match any expected formats, the original string is returned.
     *
     * @param date The date string to be formatted.
     * @return The formatted date string in "MMM dd yyyy, hh:mma" format, or the original input if parsing fails.
     */
    private String formatDate(String date) {
        DateTimeFormatter[] inputFormatters = {
                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")
        };
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");
        for (DateTimeFormatter formatter : inputFormatters) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(date.trim(), formatter);
                return dateTime.format(outputFormatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        return date;
    }

}
