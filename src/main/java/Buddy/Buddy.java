package Buddy;

import Buddy.exceptions.BuddyException;
import Buddy.parser.Parser;
import Buddy.ui.Ui;
import Buddy.tasks.TaskList;
import Buddy.storage.Storage;
import java.util.Scanner;

public class Buddy {

    private final Scanner scanner;
    private final Ui ui;
    private final Parser parser;
    private final TaskList tasklist;

    public Buddy() {
        this.ui = new Ui();
        Storage storage = new Storage();
        this.parser = new Parser();
        this.tasklist = new TaskList(ui, storage, parser);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the chatbot and continuously processes user input.
     * <p>
     * This method displays an introduction, then enters a loop to parse and execute user commands.
     * It identifies the command type using {@link Parser} and performs the corresponding action,
     * such as listing tasks, marking/unmarking tasks, deleting tasks, or finding tasks.
     * If the command is unrecognized, it is treated as a task addition.
     * The chatbot runs until the user enters "bye", after which a goodbye message is displayed.
     *
     */

    public void start() {
        ui.setIntroduction();
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                String command = parser.getCommand(input);
                switch (command) {
                case "list":
                    ui.listTasks(tasklist.getTaskList());
                    break;
                case "mark":
                    tasklist.markTaskAsDone(input);
                    break;
                case "unmark":
                    tasklist.unMarkTaskAsDone(input);
                    break;
                case "delete":
                    tasklist.deleteTasks(input);
                    break;
                case "find":
                    tasklist.findTask(input);
                    break;
                default:
                    tasklist.addToList(input);
                    break;
                }
            } catch (BuddyException e) {
                System.out.println(e.getMessage());
                ui.printDivider();
            }
            input = scanner.nextLine();
        }

        ui.sayGoodbye();
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.start();
    }
}
