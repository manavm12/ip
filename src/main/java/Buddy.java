import exceptions.*;
import java.io.*;
import java.util.Scanner;

public class Buddy {

    private static final String FILE_PATH = "data/duke.txt";
    private static final String DIRECTORY_PATH = "data";


    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";

    private final Scanner scanner;

    private final Task[] taskList;
    private int taskCount;
    private static final int MAX_LIST_LENGTH = 100;

    public Buddy() {
        this.scanner = new Scanner(System.in);
        this.taskList = new Task[MAX_LIST_LENGTH];
        this.taskCount = 0;
        loadTasksFromFile();
    }

    private void printDivider(){
        System.out.println(DIVIDER);
    }

    private void setIntroduction(){
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();
    }

    private void sayGoodbye(){
        printDivider();
        System.out.println(GOODBYE);
        printDivider();
    }

    private void addToList(String input){
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

    private void addResponse(){
        System.out.println("Understood buddy! I've added it to your Task list");
        System.out.println("    "+ taskList[taskCount-1].toString());
        System.out.println("You know what to do with " + taskCount + " tasks!");
    }

    private void addTodo(String input) throws EmptyTaskDescriptionException{
        if (input.trim().equals("todo")){
            throw new EmptyTaskDescriptionException("todo");
        }
        String task = input.substring(5);
        taskList[taskCount]= new Todo(task);
        taskCount++;
        addResponse();
    }

    private void addDeadline(String input) throws EmptyTaskDescriptionException, InvalidDeadlineFormatException {
        if (input.trim().equals("deadline")) {
            throw new EmptyTaskDescriptionException("deadline");
        }
        String deadlineDetails[] = input.substring(9).split("/");
        if (deadlineDetails.length < 2) {
            throw new InvalidDeadlineFormatException();
        }
        String taskName = deadlineDetails[0];
        String taskDeadline = deadlineDetails[1];
        if (taskDeadline.startsWith("by")) {
            taskDeadline = taskDeadline.substring(3);
        }
        taskList[taskCount] = new Deadline(taskName, taskDeadline);
        taskCount++;
        addResponse();
    }

    private void addEvent(String input) throws EmptyTaskDescriptionException, InvalidEventFormatException {
        if (input.trim().equals("event")){
            throw new EmptyTaskDescriptionException("event");
        }
        String eventDetails[] = input.substring(6).split("/");
        if (eventDetails.length < 3) {
            throw new InvalidEventFormatException();
        }
        String taskName = eventDetails[0];
        String taskStart = eventDetails[1];
        if (taskStart.startsWith("from")){
            taskStart = taskStart.substring(5);
        }
        String taskEnd = eventDetails[2];
        if (taskEnd.startsWith("to")){
            taskEnd = taskEnd.substring(3);
        }
        taskList[taskCount]= new Event(taskName,taskStart,taskEnd);
        taskCount++;
        addResponse();
    }

    private void listTasks(){
        printDivider();
        for (int i = 0; i < taskCount; i++){
            int index = i+1;
            Task task = taskList[i];
            System.out.println(index + ". " + task.toString());
        }
        printDivider();
    }


    private void markTaskAsDone(String input){
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new InvalidTaskIndexException();
            }
            Task task = taskList[index];
            task.markAsDone();
            saveTasksToFile();
            System.out.println("There you go! Good job finishing that task");
            System.out.println(task);
        }catch (InvalidTaskIndexException e){
            System.out.println(e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Whoa! I need a valid task number. Try something like: mark 2");
        }
    }

    private void unMarkTaskAsDone(String input){
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new InvalidTaskIndexException();
            }
            Task task = taskList[index];
            task.markAsNotDone();
            saveTasksToFile();
            System.out.println("I've marked this task as not done. Go for it and complete it!");
            System.out.println(task);
        } catch (InvalidTaskIndexException e){
            System.out.println(e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Whoa! I need a valid task number. Try something like: unmark 2");
        }
    }

    private void saveTasksToFile(){
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
                fw.write(taskList[i].toFileFormat() + System.lineSeparator());
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
                    System.out.println("Warning: Skipping corrupted line - " + line);
                    continue;
                }

                Task task;
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                if (type.equals("T")) {
                    task = new Todo(description);
                } else if (type.equals("D")) {
                    if (parts.length < 4) {
                        System.out.println("Warning: Skipping corrupted deadline line - " + line);
                        continue;
                    }
                    String by = parts[3];
                    task = new Deadline(description, by);
                } else if (type.equals("E")) {
                    if (parts.length < 5) {
                        System.out.println("Warning: Skipping corrupted event line - " + line);
                        continue;
                    }
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                } else {
                    System.out.println("Warning: Skipping unknown task type - " + line);
                    continue;
                }

                // ✅ Mark task as done if needed
                if (isDone) {
                    task.markAsDone();
                }

                taskList[taskCount++] = task;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found.");
        }
    }


    public void start() {
        setIntroduction();
        String input = scanner.nextLine();
        while (input!=null) {
            if (input.equals("bye")) {
                sayGoodbye();
                break;
            } else if (input.equals("list")) {
                listTasks();
            } else if (input.startsWith("mark ")) {
                markTaskAsDone(input);
            } else if (input.startsWith("unmark ")) {
                unMarkTaskAsDone(input);
            } else {
                addToList(input);
            }
            input = scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.start();
    }
}
