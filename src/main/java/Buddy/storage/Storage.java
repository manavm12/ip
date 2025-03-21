package Buddy.storage;

import Buddy.tasks.Deadline;
import Buddy.tasks.Event;
import Buddy.tasks.Task;
import Buddy.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "data/buddy.txt";
    private static final String DIRECTORY_PATH = "data";

    private static final String WARNING = "Warning: Skipping corrupted deadline line - ";

    /**
     * Saves the current list of tasks to a file to maintain continuity.
     * <p>
     * This method writes all tasks from the given list to a file so that they
     * can be retrieved when the chatbot is restarted. If the storage directory
     * does not exist, it attempts to create one. If an I/O error occurs, an
     * error message is printed.
     *
     * @param taskList The list of tasks to be saved to the file.
     */
    public void saveTasksToFile(ArrayList<Task> taskList) {
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
            for (Task task : taskList) {
                fw.write(task.toFileFormat() + System.lineSeparator());
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from a file and returns them as a list.
     * <p>
     * Reads stored tasks from the file and reconstructs them into a list.
     * If the file does not exist, returns an empty list. Prints a warning
     * for invalid or corrupted entries.
     *
     * @return A list of tasks loaded from the file.
     */
    public ArrayList<Task> loadTasksFromFile() {
        File file = new File(FILE_PATH);
        ArrayList<Task> tasklist = new ArrayList<>();

        if (!file.exists()) {
            return tasklist;
        }
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" \\| ");

                if (parts.length < 3) {
                    System.out.println(WARNING + line);
                    continue;
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task;

                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        System.out.println(WARNING + line);
                        continue;
                    }
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    if (parts.length < 5) {
                        System.out.println(WARNING + line);
                        continue;
                    }
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                default:
                    System.out.println(WARNING + line);
                    continue;
                }

                if (isDone) {
                    task.markAsDone();
                }

                tasklist.add(task);
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("No previous tasks found.");
        }

        return tasklist;
    }

}
