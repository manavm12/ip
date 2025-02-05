import java.util.Scanner;

public class Buddy {
    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";
    private static final int maxListLength = 100;
    private final Scanner scanner;
    private final Task[] toDoList;
    private int taskCount;


    public Buddy() {
        this.scanner = new Scanner(System.in);
        this.toDoList = new Task[maxListLength];
        this.taskCount = 0;
    }

    private void printDivider(){
        System.out.println(DIVIDER);
    }

    private void addToList(String input){
        if(input.startsWith("todo")){
            addTodo(input);
        } else if (input.startsWith("deadline")){
            addDeadline(input);
        } else if (input.startsWith("event")){
            addEvent(input);
        }else {
            System.out.println("It would be cool if you could segregate your task into todo, deadline or event");
        }
        printDivider();
        printDivider();
    }

    private void addTodo(String input){
        String task = input.substring(5);
        toDoList[taskCount]= new Todo(task);
        taskCount++;
        System.out.println("Understood buddy! I've added it to your Task list");
        System.out.println("    "+ toDoList[taskCount-1].toString());
        System.out.println("You know what to do with " + taskCount + " tasks!");
    }

    private void addDeadline(String input){
        String taskName = input.substring(9).split("/")[0];
        String taskDeadline = input.substring(9).split("/")[1];
        if (taskDeadline.startsWith("by")){
            taskDeadline = taskDeadline.substring(3);
        }
        toDoList[taskCount]= new Deadline(taskName,taskDeadline);
        taskCount++;
        System.out.println("Understood buddy! I've added it to your Task list");
        System.out.println("    "+ toDoList[taskCount-1].toString());
        System.out.println("You know what to do with " + taskCount + " tasks!");
    }

    private void addEvent(String input){
        String taskName = input.substring(6).split("/")[0];
        String taskStart = input.substring(6).split("/")[1];
        if (taskStart.startsWith("from")){
            taskStart = taskStart.substring(5);
        }
        String taskEnd = input.substring(6).split("/")[2];
        if (taskEnd.startsWith("to")){
            taskEnd = taskEnd.substring(3);
        }
        toDoList[taskCount]= new Event(taskName,taskStart,taskEnd);
        taskCount++;
        System.out.println("Understood buddy! I've added it to your Task list");
        System.out.println("    "+ toDoList[taskCount-1].toString());
        System.out.println("You know what to do with " + taskCount + " tasks!");
    }

    private void listTasks(){
        printDivider();
        for (int i = 0; i < taskCount; i++){
            int index = i+1;
            Task task = toDoList[i];
            System.out.println(index + ". " + task.toString());
        }
        printDivider();
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

    private void markTaskAsDone(String input){
        int index = Integer.parseInt(input.split(" ")[1])-1;
        if (index >= 0 && index < taskCount){
            Task task = toDoList[index];
            task.markAsDone();
            System.out.println("There you go! Good job finishing that task");
            System.out.println(task.toString());
        } else {
            System.out.println("I'm sorry, but you need to enter a valid index");
        }
    }

    private void unMarkTaskAsDone(String input){
        int index = Integer.parseInt(input.split(" ")[1])-1;
        if (index >= 0 && index < taskCount){
            Task task = toDoList[index];
            task.markAsNotDone();
            System.out.println("I've marked this task as not done. Go for it and complete it!");
            System.out.println(task.toString());
        } else {
            System.out.println("I'm sorry, but you need to enter a valid index");
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
