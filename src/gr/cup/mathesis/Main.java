package gr.cup.mathesis;

import gr.cup.mathesis.model.Task;
import gr.cup.mathesis.model.TaskManager;
import gr.cup.mathesis.model.TaskManagerInterface;
import gr.cup.mathesis.model.db.TaskManagerDB;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;

/**
 * 4. 
 *
 * @author Mathesis
 */
public class Main {

    private static final TaskManagerInterface TASK_MANAGER = TaskManager.getInstance();
  //  private static final TaskManagerInterface TASK_MANAGER = TaskManagerDB.getInstance();
    private static final String PROMPT = "-> ";
    private static final String TASK_ID_PROMPT = "Task id (or 0 for main menu): ";
    // scanner to allow to press ENTER
    private static final Scanner IN = new Scanner(System.in).useDelimiter("(\\b|\\B)");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Usage: java todo.Main <*.script file>");
            System.exit(1);
        }

        mainMenu();
    }

    /**
     * Main menu. Displays the choices.
     */
    private static void mainMenu() {
        displayAlerts();
        int answer = -1;
        do {
            System.out.println("");
            System.out.println("=========== MENU ==============");
            System.out.println("== 1. List tasks by due date ==");
            System.out.println("== 2. List tasks by priority ==");
            System.out.println("== 3. Search for Task(s) ======");
            System.out.println("== 4. Task details ============");
            System.out.println("== 5. Add new task ============");
            System.out.println("== 6. Edit task ===============");
            System.out.println("== 7. Delete task =============");
            System.out.println("== 8. Mark task as completed ==");
            System.out.println("== 9. Show completed tasks ====");
            System.out.println("== 10. Show alerts ============");
            System.out.println("== 11. Export as XML ==========");
            System.out.println("== 0. Exit ====================");
            System.out.println("===============================");
            do {
                System.out.print(PROMPT);
                try {
                    answer = Integer.parseInt(IN.nextLine());
                } catch (NumberFormatException e) {
                    answer = -1;
                }
            } while (answer < 0 || answer > 11);
            switch (answer) {
                case 1: // list tasks by due date
                    System.out.println("===== Tasks by due date =======");
                    displayTasks(TASK_MANAGER.listAllTasks(false));
                    break;
                case 2: // list tasks by priority
                    System.out.println("======= Tasks priority ========");
                    displayTasks(TASK_MANAGER.listAllTasks(true));
                    break;
                case 3: // search for task(s)
                    System.out.println("====== Search for task(s) =====");
                    String desc = "";
                    do {
                        System.out.print("Provide a text to search for in description or '-' to cancel: ");
                        desc = IN.nextLine();
                    } while (desc.isEmpty()); // validate file path
                    if (!desc.equals("-")) {
                        List<Task> tasks = searchForTasks(desc);
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks where found");
                        } else {
                            System.out.println(tasks.size() + " task(s) found:");
                            for (Task task : tasks) {
                                displayTask(task, true);
                            }
                        }
                    }
                    break;
                case 4: // task details
                    final int id = inputId(TASK_ID_PROMPT);
                    if (id != 0 && exists(id)) {
                        displayTask(TASK_MANAGER.findTask(id), true);
                    }
                    break;
                case 5: // Add new task
                    createNewTask();
                    break;
                case 6: // Edit task
                    editTask(inputId(TASK_ID_PROMPT));
                    break;
                case 7: // Delete task
                    deleteTask(inputId(TASK_ID_PROMPT));
                    break;
                case 8: // Mark a task as completed
                    markTaskAsCompleted(inputId(TASK_ID_PROMPT), true);
                    break;
                case 9: // Display completed task
                    displayCompletedTasks();
                    break;
                case 10: // Display alerts
                    displayAlerts();
                    break;
                case 11: // Export as XML
                    System.out.println("======= Export as XML ======");
                    String path = "";
                    do {
                        System.out.println("Valid absolute file path with extension .xml? ");
                        path = IN.nextLine();
                    } while (!isValid(path)); // validate file path
                    exportAsXml(path, validateAnswer("By priority (Y/y) or by Due Date (N/n)? "));
                    System.out.println(path + " saved successfully.");
                    break;
                case 0: // Exit
                    System.out.println("Bye!");
                    IN.close();
                    if (TASK_MANAGER instanceof TaskManagerDB) {
                        ((TaskManagerDB)TASK_MANAGER).disconnect();
                    }
                    break;
                default:
                    break;
            }
        } while (answer != 0);
    }

    /**
     * 1,2. Show tasks.
     *
     * @param tasks list of tasks
     */
    private static void displayTasks(List<Task> tasks) {
        // TODO
        for(Task task:tasks) System.out.println(""+task);   //overidden toString? or toshortstring
       
    }

    /**
     * 3. Search for task(s) by description
     *
     * @param desc text to search in description
     * @return list of tasks that contain {@code desc} in their description
     */
    private static List<Task> searchForTasks(String desc) {
        // TODO
     //  return TASK_MANAGER.findTaskByDesc(desc); ηθελα να χρεισιμοποιησω αυτην την μεθοδο αλλα δεν μπορω να την ενταξω στην TaskManagerInterface
     List <Task> pack=new ArrayList();
     for(Task task:TASK_MANAGER.listAllTasks(true)) if(task.getDescription().equals(desc))  pack.add(task);
     return pack;
     
     
    }

    /**
     * 4. Show task details
     *
     * @param task task to display
     * @param detail if {@code true} then show details
     */
    private static void displayTask(Task task, boolean detail) {
        if (detail) {
            System.out.println("-------- Task details ---------");
            System.out.println(task.toString());
        } else {
            System.out.println("------------ Task  ------------");
            System.out.println(task.toShortString());                             //SOMETHING MISSING
        }
    }

    /**
     * 5. Add new task
     */
    private static void createNewTask() {
        System.out.println("========== New Task ===========");
        String desc = "";
        int prio = -1;
        LocalDate dueDate = null;
        boolean alert = false;
        int daysBefore = 0;
        String comments = "";
        boolean completed = false;
        System.out.print("Description: ");
        do {
            desc = IN.nextLine();
        } while (desc.isEmpty());
        System.out.print("Priority [1-10] or ENTER for default (10): ");
        prio = inputIntValue(0, 10);
        prio = prio == -1 ? 10 : prio;
        System.out.print("Due date (dd/MM/yyyy) [ENTER to leave empty]: ");
        final String date = IN.nextLine();
        if (!date.isEmpty()) {
            try {
                dueDate = LocalDate.parse(date, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
            }
            if (dueDate != null) {
                System.out.print("Show Alert (Y/y/N/n) [Default: N/n]? ");
                alert = IN.nextLine().equalsIgnoreCase("y");
                if (alert) {
                    System.out.print("Days Before [0-365]: ");
                    daysBefore = inputIntValue(0, 365);
                    daysBefore = daysBefore == -1 ? 0 : daysBefore;
                }
            }
        }
        System.out.print("Comments: ");
        comments = IN.nextLine();
        System.out.print("Completed (Y/y/N/n) [Default: N/n]? ");
        completed = IN.nextLine().equalsIgnoreCase("y");
        TASK_MANAGER.addTask(new Task(desc, prio,
                dueDate, alert, daysBefore, comments, completed));
    }

    /**
     * 6. Edit task
     *
     * @param id task id
     */
    private static void editTask(int id) {
        if (id != 0 && exists(id)) {
            System.out.println("======= Edit Task ==========");
            Task task = TASK_MANAGER.findTask(id);
            System.out.println("Description: " + task.getDescription());
            System.out.print("New Description or ENTER to pass: ");
            String desc = IN.nextLine();
            if (!desc.isEmpty() && !desc.equalsIgnoreCase("\n")) {
                task.setDescription(desc);
            }
            System.out.println("Priority: " + task.getPriority());
            System.out.print("New Priority [1-10] or ENTER to pass: ");
            int prio = inputIntValue(0, 10);
            if (prio > 0 && prio <= 10) {
                task.setPriority(prio);
            }
            if (task.getDueDate() != null) {
                System.out.println("Due date: " + task.getDueDate().format(DATE_FORMATTER));
            } else {
                System.out.println("No Due date.");
            }
            System.out.print("New Due date (dd/MM/yyy) or '-' to delete or ENTER to pass: ");
            String s = IN.nextLine();
            if (!s.isEmpty() && !s.equalsIgnoreCase("\n") && !s.equals("-")) {
                LocalDate dueDate = null;
                try {
                    dueDate = LocalDate.parse(s, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    dueDate = null;
                }
                task.setDueDate(dueDate);
            } else if (s.equals("-")) {
                task.setDueDate(null);
            }
            if (task.getDueDate() != null) {
                System.out.println("Alert (Y/y/N/n)? " + task.getAlert());
                System.out.print("New Alert or ENTER to pass: ");
                s = IN.nextLine();
                if (!s.isEmpty()) {
                    task.setAlert(s.equals("y"));
                }
                if (task.getAlert()) {
                    System.out.println("Days Before: " + task.getDaysBefore());
                    System.out.print("New Days Before or ENTER to pass: ");
                    int daysBefore = inputIntValue(0, 365);
                    if (daysBefore >= 0 && daysBefore <= 365) {
                        task.setDaysBefore(daysBefore);
                    }
                }
            }
            System.out.println("Comments: " + task.getComments());
            System.out.print("Comments or ENTER to pass: ");
            s = IN.nextLine();
            if (!s.equalsIgnoreCase("\n")) {
                task.setComments(s);
            }
            System.out.println("Completed (Y/y/N/n)? " + task.isCompleted());
            System.out.print("Completed or ENTER to pass: ");
            s = IN.nextLine();
            if (!s.equalsIgnoreCase("\n")) {
                task.setCompleted(s.equals("y"));
            }
            TASK_MANAGER.updateTask(task);
        }
    }

    /**
     * 7. Delete task
     *
     * @param id task id
     */
    private static void deleteTask(int id) {
        // TODO
        TASK_MANAGER.removeTask(id);
    }

    /**
     * 8. Mark Task as completed
     *
     * @param id task id
     * @param completed if {@code true} then task is completed
     */
    private static void markTaskAsCompleted(int id, boolean completed) {
        // TODO
        TASK_MANAGER.markAsCompleted(id, completed);
    }

    /**
     * 9. Show completed tasks
     */
    private static void displayCompletedTasks() {
        System.out.println("======= Completed Tasks =======");
        // TODO
        for(Task task:TASK_MANAGER.listCompletedTasks()) System.out.println(task);
    }

    /**
     * 10. Show alerts TODO: daysBefore
     */
    private static void displayAlerts() {
        // TODO
        for (Task task:TASK_MANAGER.listTasksWithAlert())
            if(!task.isCompleted()&&task.isLate())System.out.println("This task is overdue by "+(-LocalDate.now().getDayOfYear()-task.getDueDate().getDayOfYear())+" days:\n "+task.getDescription());
    }

    /**
     * 11. Export as XML
     *
     * {@code <tasks>
     * <task id="1">
     * <description>description</description>
     * <priority>10</priority>
     * <dueDate>2019-01-01</dueDate>
     * <alert>yes</alert>
     * <daysBefore>1</daysBefore>
     * <comments></comments>
     * <completed>false</completed>
     * </task>
     * </tasks>}
     *
     * @param filePath path of the XML file to export to
     * @param byPriority if {@code true} sort by priority, otherwise by due date
     */
    private static void exportAsXml(String filePath, boolean byPriority) {
        // TODO
        try{
         DocumentBuilderFactory dbFactory =DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         
         Element rootElement = doc.createElement("Tasks");
         doc.appendChild(rootElement);
         for(Task task:TASK_MANAGER.listAllTasks(byPriority)){
         Element taskElement=doc.createElement("Task");
         rootElement.appendChild(taskElement);
         createElementWithAC(doc,taskElement,"id",""+task.getId());
         createElementWithAC(doc,taskElement,"description",task.getDescription());
         createElementWithAC(doc,taskElement,"priority",""+task.getPriority());
         createElementWithAC(doc,taskElement,"dueDate",""+task.getDueDate());
         if(task.hasAlert()){
             createElementWithAC(doc,taskElement,"alert","yes");
             createElementWithAC(doc,taskElement,"daysBefore",""+task.getDaysBefore());
         }else{
             createElementWithAC(doc,taskElement,"alert","no");
             createElementWithAC(doc,taskElement,"daysBefore","");
         }
         createElementWithAC(doc,taskElement,"comments",task.getComments());
         
         }
         writeToFile(doc,filePath);
         
          
          
       }catch(Exception e){
     e.printStackTrace();
    }
    }
    private static void createElementWithAC(Document doc,Element apend,String str1,String str2){
        Element element =doc.createElement(str1);
        element.appendChild(doc.createTextNode(str2));
        apend.appendChild(element);
    }

    /**
     * Write document to file.
     *
     * @param doc DOM XML document
     * @param file file path
     */
    private static void writeToFile(Document doc, String file) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(file)));
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prompts the user for a task id. {@code 0} means back to main menu
     *
     * @param prompt prompt to use
     * @return the task id input by the user; {@code 0} means back to main menu
     */
    private static int inputId(String prompt) {
        int id = -1;
        do {
            System.out.print(prompt);
            try {
                id = Integer.parseInt(IN.nextLine());
            } catch (NumberFormatException e) {
                id = -1;
            }
        } while (id < 0 || id >= Integer.MAX_VALUE);
        return id;
    }

    /**
     * Helper method to input an int value
     *
     * @param min minimum value
     * @param max maximum value
     * @return the int value input by the user
     */
    private static int inputIntValue(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("" + min + " must be less than " + max);
        }
        int val = min - 1;
        String sVal = "";
        do {
            sVal = IN.nextLine();
            if (!sVal.isEmpty() && !sVal.equalsIgnoreCase("\n")) {
                try {
                    val = Integer.parseInt(sVal);
                } catch (NumberFormatException e) {
                    val = min - 1;
                }
            } else {
                break;
            }
        } while (val < min || val > max);
        return val;
    }

    /**
     * Validate user's answer.
     *
     * @return {@code true} or {@code false} based on {@code prompt}.
     */
    private static boolean validateAnswer(String prompt) {
        String answer = "n";
        do {
            System.out.print(prompt);
            answer = IN.nextLine();
        } while (!(answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("y")));
        return answer.equalsIgnoreCase("y");
    }

    /**
     * Checks if {@code id} is valid
     *
     * @param id to check for validity
     * @return {@code true} if {@code id} represents a task
     */
    private static boolean exists(int id) {
        boolean found = false;
        List<Task> tasks = TASK_MANAGER.listAllTasks(true);
        for (Task task : tasks) {
            if (task.getId() == id) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Validate file path. Also creates the file.
     *
     * @param filePath absolute file path
     * @return {@code true} if path is valid
     */
    private static boolean isValid(String filePath) {
        Path path = Paths.get(filePath).normalize();
        if (Files.notExists(path) || !Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                return false;
            }
        }
        return Files.exists(path);
    }
}
