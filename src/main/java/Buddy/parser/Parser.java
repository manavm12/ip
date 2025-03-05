package Buddy.parser;

import Buddy.ui.Ui;
import Buddy.tasks.TaskList;

public class Parser {

    private final Ui ui;
    private final TaskList tasklist;

    public Parser(Ui ui, TaskList tasklist) {
        this.ui = ui;
        this.tasklist = tasklist;
    }

    private String getCommand(String input) {
        return input.split(" ")[0];
    }

    public void parseCommand(String input) {
        String command = getCommand(input);
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
        default:
            tasklist.addToList(input);
            break;
        }
    }
}
