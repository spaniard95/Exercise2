
package gr.cup.mathesis.model;

import java.time.LocalDate;
import java.time.Period;

public class Task {
private String desc,comments;
private int prio,daysBefore,id;
private boolean alert,completed;
private LocalDate dueDate;

//has id contructor
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
    @Override
    public boolean equals(Object object){
        if(object==this)return true;
        if(object==null||object.getClass()==this.getClass())return false;
        Task task=(Task)object;
        if(task.toString()==this.toString())return true;
        else return false;
    }
    @Override
    public int hashCode(){
        return this.id;
    }
    public String toShortString(){
        return ("Description:"+getDescription()+"\nPriority:"+getPriority()+"\nDueDate:"+getDueDate()+"\nAlert?:"+getAlert()+"\ndaysBefore:"+3+"");
//todo
    }
    public String toString(){
        return(toShortString()+"\nComments:"+getComments()+"\nCompleted:"+isCompleted());
    }
    private String daysBefore(Period period){
        return ("Months "+period.getMonths()+" days "+period.getDays());
    }
}
