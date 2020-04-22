
package gr.cup.mathesis.model;

import java.time.LocalDate;
import java.time.Period;

public class Task {
private String desc,comments;
private int prio,daysBefore,id,daysUntil;//daywbefore alert message
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
    public int getDaysUntil(){
        return daysBeetweenDates(LocalDate.now(),dueDate);
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
    
    public boolean isLate(){
        return true; //NEEDS WORK DONE 
    }
    public boolean getAlert(){
        return alert;
    }
    public boolean hasAlert(){
        return alert;
    }
    @Override
    public boolean equals(Object object){
        if(object==this)return true;
        if(object==null||object.getClass()==this.getClass())return false;
        Task task=(Task)object;
        if(task.toString().equals(this.toString()))return true;
        else return false;
    }
    @Override
    public int hashCode(){
        return this.id;
    }
    public String toShortString(){
        String str=("ID:"+getId()+"\nDescription: "+getDescription()+"\nPriority: "+getPriority());
        if(dueDate!=null)  str+="\nDueDate: "+getDueDate();
        if (hasAlert()) str+="\nAlert?: "+hasAlert()+"\ndaysBefore: "+LocalDate.now().until(dueDate.minusDays(daysBefore));
        return str;
    
    }
    @Override
    public String toString(){
        return(toShortString()+"\ncomments: "+getComments()+"\nCompleted: "+isCompleted());
     
    }
    private String timeBefore(Period period){
        if(period.getMonths()!=0)return ("Months "+period.getMonths()+" days "+period.getDays());
        return ("Days "+period.getDays());
    }
    public int daysBeetweenDates(LocalDate a,LocalDate b){//i will use that method in duedate comparator i think a better way exists
        int days=a.getDayOfYear()-b.getDayOfYear();           //couldnt find better way or method to count days beetween two LocalDates 
        if(days<0)return -days;
        else return days;
    }

   
}
