package gr.cup.mathesis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 2. 
 * 
 * @author mathesis
 */
//looks like a singleton class
public final class TaskManager implements TaskManagerInterface {
    List <Task> tasks=new ArrayList();
    private static TaskManager INSTANCE;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManager();
        }
        return INSTANCE;
    }

    @Override
    public List<Task> listAllTasks(final boolean priorityOrDate) {
        // TODO 
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public List<Task> listTasksWithAlert() {
        // TODO 
        return null;
    }
    
    @Override
    public List<Task> listCompletedTasks() {
        // TODO 
        return null;
    }

    @Override
    public void addTask(final Task task) {
        if (validate(task)) {
            // TODO
        } else {
            Logger.getLogger(TaskManager.class.getName()).log(Level.WARNING, "Task validation failed.");
        }
    }

    @Override
    public void updateTask(final Task task) {
        if (validate(task)) {
            // TODO
        } else {
            Logger.getLogger(TaskManager.class.getName()).log(Level.WARNING, "Task validation failed.");
        }
    }

    @Override
    public void markAsCompleted(final int id, final boolean completed) {
        // TODO 
    }

    @Override
    public void removeTask(final int id) {
        // TODO 
    }

    private boolean validate(final Task task) {
        return !task.getDescription().isEmpty();
    }

    @Override
    public Task findTask(final int id) {
        // TODO 
        return null;
    }
}
