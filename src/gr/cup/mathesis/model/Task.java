
package gr.cup.mathesis.model;

import java.time.LocalDate;

public class Task {
private String desc,comments;
private int prio,daysBefore,id;
private boolean alert,completed;
private LocalDate dueDate;

    public Task(String desc, String comments, int prio, int daysBefore, boolean alert, boolean completed, LocalDate dueDate,int id) {
        this.desc = desc;
        this.comments = comments;
        this.prio = prio;
        this.daysBefore = daysBefore;
        this.alert = alert;
        this.completed = completed;
        this.dueDate = dueDate;
       
    }

    public Task(String desc, int prio, LocalDate dueDate, boolean alert, int daysBefore, String comments, boolean completed) {
    this.alert=alert;
    this.comments=comments;
    this.completed=completed;
    this.daysBefore=daysBefore;
    this.desc=desc;
    this.dueDate=dueDate;
    this.prio=prio;
    }


    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getPriority() {
        return prio;
    }

    public void setPriority(int prio) {
        this.prio = prio;
    }

    public int getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int isLate(){
        return 0; //NEEDS WORK DONE 
    }
    public boolean getAlert(){
        return alert;
    }
    
}
