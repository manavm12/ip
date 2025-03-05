package Buddy.ui;

import Buddy.tasks.Task;
import java.util.ArrayList;

public class Ui {
    private static final String INTRODUCTION = "Yo! I'm Buddy! Your friendly neighbourhood chatbot.\nWhat can I help you with today?";
    private static final String GOODBYE = "Alright then! See you later. You know where to find me.";
    private static final String DIVIDER = "--------------------------";

    public void printDivider() {
        System.out.println(DIVIDER);
    }

    public void setIntroduction() {
        printDivider();
        System.out.println(INTRODUCTION);
        printDivider();
    }

    public void sayGoodbye() {
        printDivider();
        System.out.println(GOODBYE);
        printDivider();
    }

    public void addToListResponse(ArrayList<Task> taskList) {
        System.out.println("Understood buddy! I've added it to your task list");

        String lastTask = taskList.get(taskList.size() - 1).toString();
        System.out.println("    " + lastTask);

        System.out.println("You know what to do with " + taskList.size() + " tasks!");
    }

    public void markTaskResponse(Task task) {
        System.out.println("There you go! Good job finishing that task");
        System.out.println(task);
        printDivider();
    }

    public void unmarkTaskResponse(Task task){
        System.out.println("I've marked this task as not done. Go for it and complete it!");
        System.out.println(task);
        printDivider();
    }

    public void deleteTaskResponse(Task task, ArrayList<Task> taskList) {
        System.out.println("Done. I have deleted this task");
        System.out.println("    " + task);
        System.out.println("Now you have " + taskList.size() + " tasks remaining");
        printDivider();
    }

    public void listTasks(ArrayList<Task> taskList) {
        printDivider();
        for (int i = 0; i < taskList.size(); i++){
            int index = i+1;
            Task task = taskList.get(i);
            System.out.println(index + ". " + task.toString());
        }
        printDivider();
    }

    public void markTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: mark 2");
    }

    public void unmarkTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: unmark 2");
    }

    public void deleteTaskWarning() {
        System.out.println("Whoa! I need a valid task number. Try something like: delete 1");
    }

}
