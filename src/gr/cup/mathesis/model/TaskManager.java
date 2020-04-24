package gr.cup.mathesis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

//looks like a singleton class
public final class TaskManager implements TaskManagerInterface {
    List <Task> tasks=new ArrayList();
    
    
    List <Task> pack;
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
    public List<Task> listAllTasks(final boolean priorityOrDate)//false==Date true==priority
    {
        if(priorityOrDate) {
           Comparator<Task> comp=Comparator.comparing(Task::getPriority);
           Collections.sort(tasks,comp);
        }else{
            Comparator<Task> comp1=Comparator.comparing(Task::getDaysUntil);
            Collections.sort(tasks,comp1);
         }
        //tasks.sort(tasks,comp);  i dont understand why it doesnt work outside
        return Collections.unmodifiableList(tasks);
    }

    @Override
    public List<Task> listTasksWithAlert() {
        pack=new ArrayList();
        for(Task task:tasks) {
           if(task.getAlert()) pack.add(task);
        }
        return pack;
    }
    
    @Override
    public List<Task> listCompletedTasks() {
        pack=new ArrayList();
        for(Task task:tasks) {
           if(task.isCompleted()) pack.add(task);
        }
        return pack;
    }
    
    @Override
    public void addTask(final Task task) {
        if (validate(task)) {
            tasks.add(task);
            task.setId(tasks.size());
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
        findTask(id).setCompleted(completed);
        
    }

    @Override
    public void removeTask(final int id) {
        // TODO 
        tasks.remove(id-1);
    }

    private boolean validate(final Task task) {
        return !task.getDescription().isEmpty();
    }

    @Override
    public Task findTask(final int id) {
        // TODO 
        return tasks.get(id-1);
        
    }
    //ηθελα να την χρησιμοποιησω στην main για searchtask(String desc)
    public List<Task> findTaskByDesc(String desc){
        pack=new ArrayList();
        for(Task task:tasks) 
            if(task.getDescription().equals(desc))  pack.add(task);
        return pack;
    }
             
}
