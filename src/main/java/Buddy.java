import java.util.Scanner;

public class Buddy {
    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";
    private Scanner scanner;

    public Buddy() {
        this.scanner = new Scanner(System.in);
    }

    private void printDivider(){
        System.out.println(DIVIDER);
    }

    public void start() {
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();

        String input = scanner.nextLine();
        while(!input.equals("bye")){
            printDivider();
            System.out.println("    " + input);
            printDivider();
            input = scanner.nextLine();
        }

        printDivider();
        System.out.println(GOODBYE);
        printDivider();
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.start();
    }
}
