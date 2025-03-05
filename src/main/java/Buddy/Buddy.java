package Buddy;

import Buddy.parser.Parser;
import Buddy.ui.Ui;
import Buddy.tasks.TaskList;
import Buddy.storage.Storage;
import java.util.Scanner;

public class Buddy {

    private final Scanner scanner;
    private final Ui ui;
    private final Parser parser;

    public Buddy() {
        this.ui = new Ui();
        Storage storage = new Storage();
        TaskList tasklist = new TaskList(ui, storage);
        this.parser = new Parser(ui, tasklist);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        ui.setIntroduction();
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            parser.parseCommand(input);
            input = scanner.nextLine();
        }

        ui.sayGoodbye();
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.start();
    }
}
