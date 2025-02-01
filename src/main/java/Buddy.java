import java.util.Arrays;
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
        toDoList[taskCount]= new Task(input);
        taskCount++;
        printDivider();
        System.out.println("Added: "+input);
        printDivider();
    }

    private void listTasks(){
        printDivider();
        for (int i = 0; i < taskCount; i++){
            int index = i+1;
            Task task = toDoList[i];
            System.out.println(index + ".["+ task.getStatusIcon() + "] " + task.description);
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
            System.out.println("    ["+ task.getStatusIcon() + "] " + task.description);
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
            System.out.println("    ["+ task.getStatusIcon() + "] " + task.description);
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
