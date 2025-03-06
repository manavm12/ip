package Buddy.tasks;

import Buddy.exceptions.EmptyTaskDescriptionException;
import Buddy.exceptions.InvalidDeadlineFormatException;
import Buddy.exceptions.InvalidEventFormatException;
import Buddy.exceptions.InvalidTaskIndexException;
import Buddy.exceptions.UnknownCommandException;
import Buddy.exceptions.BuddyException;

import Buddy.ui.Ui;
import Buddy.storage.Storage;

import java.util.ArrayList;


public class TaskList {

    private final Ui ui;
    private final Storage storage;
    private final ArrayList<Task> taskList;

    public TaskList(Ui ui, Storage storage) {
        this.ui = ui;
        this.storage = storage;
        this.taskList = storage.loadTasksFromFile();
    }

    public void addToList(String input) {
        try {
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

        } catch (BuddyException e) {
            System.out.println(e.getMessage());
            ui.printDivider();
        }
    }

    private void addTodo(String input) throws EmptyTaskDescriptionException {
        if (input.trim().equals("todo")) {
            throw new EmptyTaskDescriptionException("todo");
        }

        String task = input.substring(5);
        taskList.add(new Todo(task));

        ui.addToListResponse(taskList);
    }

    private void addDeadline(String input) throws EmptyTaskDescriptionException, InvalidDeadlineFormatException {
        if (input.trim().equals("deadline")) {
            throw new EmptyTaskDescriptionException("deadline");
        }

        String[] deadlineDetails = input.substring(9).split("/");

        if (deadlineDetails.length < 2) {
            throw new InvalidDeadlineFormatException();
        }

        String taskName = deadlineDetails[0];
        String taskDeadline = deadlineDetails[1];

        if (taskDeadline.startsWith("by")) {
            taskDeadline = taskDeadline.substring(3);
        }

        taskList.add(new Deadline(taskName, taskDeadline));

        ui.addToListResponse(taskList);
    }

    private void addEvent(String input) throws EmptyTaskDescriptionException, InvalidEventFormatException {
        if (input.trim().equals("event")){
            throw new EmptyTaskDescriptionException("event");
        }

        String[] eventDetails = input.substring(6).split("/");

        if (eventDetails.length < 3) {
            throw new InvalidEventFormatException();
        }

        String taskName = eventDetails[0];
        String taskStart = eventDetails[1];
        String taskEnd = eventDetails[2];

        if (taskStart.startsWith("from")){
            taskStart = taskStart.substring(5);
        }
        if (taskEnd.startsWith("to")){
            taskEnd = taskEnd.substring(3);
        }

        taskList.add(new Event(taskName,taskStart,taskEnd));

        ui.addToListResponse(taskList);
    }

    public void markTaskAsDone(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsDone();
            storage.saveTasksToFile(taskList);

            ui.markTaskResponse(task);

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            ui.markTaskWarning();
        }
    }

    public void unMarkTaskAsDone(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsNotDone();
            storage.saveTasksToFile(taskList);

            ui.unmarkTaskResponse(task);

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            ui.unmarkTaskWarning();
        }
    }

    public void deleteTasks(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskList.size()) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            taskList.remove(task);
            storage.saveTasksToFile(taskList);

            ui.deleteTaskResponse(task,taskList);

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            ui.deleteTaskWarning();
        }
    }

    public  ArrayList<Task> getTaskList(){
        return taskList;
    }

}
