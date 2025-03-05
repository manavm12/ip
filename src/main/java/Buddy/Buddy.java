package Buddy;

import Buddy.exceptions.EmptyTaskDescriptionException;
import Buddy.exceptions.InvalidDeadlineFormatException;
import Buddy.exceptions.InvalidEventFormatException;
import Buddy.exceptions.InvalidTaskIndexException;
import Buddy.exceptions.UnknownCommandException;
import Buddy.exceptions.BuddyException;
import Buddy.tasks.Deadline;
import Buddy.tasks.Event;
import Buddy.tasks.Task;
import Buddy.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Buddy {

    private static final String FILE_PATH = "data/buddy.txt";
    private static final String DIRECTORY_PATH = "data";


    private static final String INTRODUCTION = "Yo! I'm Buddy.Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";
    private static final String WARNING = "Warning: Skipping corrupted deadline line - ";

    private final Scanner scanner;

    private final ArrayList<Task> taskList = new ArrayList<>();
    private int taskCount;


    public Buddy() {
        this.scanner = new Scanner(System.in);
        this.taskCount = 0;
        loadTasksFromFile();
    }

    private void printDivider() {
        System.out.println(DIVIDER);
    }

    private void setIntroduction() {
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();
    }

    private void sayGoodbye() {
        printDivider();
        System.out.println(GOODBYE);
        printDivider();
    }

    private void addToList(String input) {
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

            saveTasksToFile();
            printDivider();

        } catch (BuddyException e) {
            System.out.println(e.getMessage());
            printDivider();
        }
    }

    private void addResponse() {
        System.out.println("Understood buddy! I've added it to your Buddy.Buddy.taskList.Task list");

        String lastTask = taskList.get(taskCount - 1).toString();
        System.out.println("    " + lastTask);

        System.out.println("You know what to do with " + taskCount + " tasks!");
    }

    private void addTodo(String input) throws EmptyTaskDescriptionException {
        if (input.trim().equals("todo")) {
            throw new EmptyTaskDescriptionException("todo");
        }

        String task = input.substring(5);
        taskList.add(new Todo(task));
        taskCount++;

        addResponse();
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
        taskCount++;

        addResponse();
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
        taskCount++;

        addResponse();
    }

    private void listTasks() {
        printDivider();
        for (int i = 0; i < taskCount; i++){
            int index = i+1;
            Task task = taskList.get(i);
            System.out.println(index + ". " + task.toString());
        }
        printDivider();
    }


    private void markTaskAsDone(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsDone();
            saveTasksToFile();

            System.out.println("There you go! Good job finishing that task");
            System.out.println(task);
            printDivider();

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Whoa! I need a valid task number. Try something like: mark 2");
        }
    }

    private void unMarkTaskAsDone(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            task.markAsNotDone();
            saveTasksToFile();

            System.out.println("I've marked this task as not done. Go for it and complete it!");
            System.out.println(task);
            printDivider();

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Whoa! I need a valid task number. Try something like: unmark 2");
        }
    }

    private void deleteTasks(String input) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new InvalidTaskIndexException();
            }

            Task task = taskList.get(index);
            taskList.remove(task);
            taskCount--;
            saveTasksToFile();

            System.out.println("Done. I have deleted this task");
            System.out.println("    " + task);
            System.out.println("Now you have " + taskCount + " tasks remaining");
            printDivider();

        } catch (InvalidTaskIndexException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Whoa! I need a valid task number. Try something like: delete 1");
        }
    }

    private void saveTasksToFile() {
        try {
            File dir = new File(DIRECTORY_PATH);
            if (!dir.exists()) {
                boolean isCreated = dir.mkdir();
                if (!isCreated) {
                    System.out.println("Warning: Failed to create directory 'data'.");
                    return;
                }
            }

            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                fw.write(taskList.get(i).toFileFormat() + System.lineSeparator());
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 3) {
                    System.out.println(WARNING + line);
                    continue;
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task;

                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        System.out.println(WARNING + line);
                        continue;
                    }
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    if (parts.length < 5) {
                        System.out.println(WARNING + line);
                        continue;
                    }
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                default:
                    System.out.println(WARNING + line);
                    continue;
                }

                if (isDone) {
                    task.markAsDone();
                }

                taskList.add(task);
                taskCount++;
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found.");
        }
    }

    private String getCommand(String input) {
        return input.split(" ")[0];
    }

    public void start() {
        setIntroduction();
        String input = scanner.nextLine();
        String command = getCommand(input);

        while (true) {
            switch (command) {
            case "bye":
                sayGoodbye();
                break;
            case "list":
                listTasks();
                break;
            case "mark":
                markTaskAsDone(input);
                break;
            case "unmark":
                unMarkTaskAsDone(input);
                break;
            case "delete":
                deleteTasks(input);
                break;
            default:
                addToList(input);
                break;
            }
            if (input.equals("bye")) {
                break;
            }
            input = scanner.nextLine();
            command = getCommand(input);
        }
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.start();
    }
}
