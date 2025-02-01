import java.util.Arrays;
import java.util.Scanner;

public class Buddy {
    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";
    private static final int maxListLength = 100;
    private Scanner scanner;
    private String[] toDoList;
    private int taskCount;


    public Buddy() {
        this.scanner = new Scanner(System.in);
        this.toDoList = new String[maxListLength];
        this.taskCount = 0;
    }

    private void printDivider(){
        System.out.println(DIVIDER);
    }

    private void addToList(String input){
        toDoList[taskCount]=input;
        taskCount++;
        printDivider();
        System.out.println("Added: "+input);
        printDivider();
    }

    private void listTasks(){
        printDivider();
        for (int i = 0; i < taskCount; i++){
            int index = i+1;
            System.out.println(index + ": " + toDoList[i]);
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

    public void start() {
        setIntroduction();
        String input = scanner.nextLine();
        while (input!=null) {
            if (input.equals("bye")) {
                sayGoodbye();
                break;
            } else if (input.equals("list")) {
                listTasks();
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
