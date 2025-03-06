package Buddy.ui;

import Buddy.tasks.Task;
import java.util.ArrayList;

public class Ui {
    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";

    /**
     * Prints a divider line.
     */
    public void printDivider() {
        System.out.println(DIVIDER);
    }

    /**
     * Prints the introduction message.
     */
    public void setIntroduction() {
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();
    }

    /**
     * Prints the goodbye message.
     */
    public void sayGoodbye() {
        printDivider();
        System.out.println(GOODBYE);
        printDivider();
    }

    /**
     * Prints a message indicating that a task has been added to the task list.
     * Also prints the last task and the total number of tasks in the list.
     *
     * @param taskList the list of tasks
     */
    public void addToListResponse(ArrayList<Task> taskList) {
        System.out.println("Understood buddy! I've added it to your task list");

        String lastTask = taskList.get(taskList.size() - 1).toString();
        System.out.println("    " + lastTask);

        System.out.println("You know what to do with " + taskList.size() + " tasks!");
    }

    /**
     * Prints a message indicating that a task has been marked as done.
     *
     * @param task the task that was marked as done
     */
    public void markTaskResponse(Task task) {
        System.out.println("There you go! Good job finishing that task");
        System.out.println(task);
        printDivider();
    }

    /**
     * Prints a message indicating that a task has been marked as not done.
     * Also prints the task details and a divider line.
     *
     * @param task the task that was marked as not done
     */
    public void unmarkTaskResponse(Task task){
        System.out.println("I've marked this task as not done. Go for it and complete it!");
        System.out.println(task);
        printDivider();
    }

    /**
     * Prints a message indicating that a task has been deleted from the task list.
     * Also prints the task details and the remaining number of tasks in the list.
     *
     * @param task the task that was deleted
     * @param taskList the list of tasks
     */
    public void deleteTaskResponse(Task task, ArrayList<Task> taskList) {
        System.out.println("Done. I have deleted this task");
        System.out.println("    " + task);
        System.out.println("Now you have " + taskList.size() + " tasks remaining");
        printDivider();
    }

    /**
     * Prints all tasks in the task list.
     *
     * @param taskList the list of tasks,
     */
    public void listTasks(ArrayList<Task> taskList) {
        printDivider();
        for (int i = 0; i < taskList.size(); i++){
            int index = i+1;
            Task task = taskList.get(i);
            System.out.println(index + ". " + task.toString());
        }
        printDivider();
    }

    /**
     * Prints a warning message indicating that a valid task number is required for marking a task.
     */
    public void markTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: mark 2");
    }

    /**
     * Prints a warning message indicating that a valid task number is required for unmarking a task.
     */
    public void unmarkTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: unmark 2");
    }

    /**
     * Prints a warning message indicating that a valid task number is required for deleting a task.
     */
    public void deleteTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: delete 1");
    }

    public void findTaskMessage() {
        System.out.println("Here are the matching tasks in your list: ");
    }

    public void noTaskFoundMessage() {
        System.out.println("I'm so sorry, but there is no matching task");
    }

}
