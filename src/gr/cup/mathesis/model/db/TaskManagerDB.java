package gr.cup.mathesis.model.db;

import gr.cup.mathesis.model.Task;
import gr.cup.mathesis.model.TaskManagerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.util.Properties;

/**
 * 3. Υλοποιήστε αυτή την κλάση
 *
 * @author Mathesis
 */
public final class TaskManagerDB implements TaskManagerInterface {

    private Connection con;
    private static TaskManagerDB INSTANCE;
    private final Properties properties;

    private TaskManagerDB() {
        properties = readProperties("db.properties");
        connect();
    }

    private static Properties readProperties(String propertiesFile) {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            Logger.getLogger(TaskManagerDB.class.getName()).log(Level.SEVERE, null, e);
        }
        return prop;
    }

    public static TaskManagerDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManagerDB();
        }
        return INSTANCE;
    }

    private String getDatabase() {
        return System.getProperty("user.home") + File.separator + properties.getProperty("defaultDatabase");
    }

    private String getJdbcUrl() {
        return properties.getProperty("defaultUrl") + getDatabase() + properties.getProperty("defaultOptions");
    }

    private void connect() {
        try {
            Class.forName(properties.getProperty("jdbcDriver"));
            con = DriverManager.getConnection(getJdbcUrl(),
                    properties.getProperty("dbAdmin"),
                    properties.getProperty("dbPassword"));
            if (!checkTables()) {
                createTables();
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(TaskManagerDB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private boolean checkTables() {
        // TODO. Χρήση της sql.count
        return false;
    }

    private void createTables() {
        // TODO
    }

    public void disconnect() {
        try {
            if (con != null) {
                con.close();
            }
            con = null;
        } catch (SQLException e) {
            // ignores the exception
        }
    }

    private void cleanUp(Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        } catch (SQLException e) {
            // ignores the exception
        }
    }

    /**
     * Update task with the given {@code id}
     *
     * @param sql update/delete sql query
     * @param id task id
     */
    private void update(String sql, int id) {
        // TODO
    }

    private List<Task> query(String where, String orderBy) {
        List<Task> result = new ArrayList<>();
        // TODO
        return result;
    }

    private void modify(String sql, Task task, boolean isUpdate) {
        // TODO
    }

    @Override
    public List<Task> listAllTasks(boolean priorityOrDate) {
        return query(null, priorityOrDate
                ? properties.getProperty("sql.alertsOrderByPriority")
                : properties.getProperty("sql.alertsOrderByDueDate"));
    }

    @Override
    public List<Task> listTasksWithAlert() {
        return query(properties.getProperty("sql.alerts"),
                properties.getProperty("sql.alertsOrderByDueDate"));
    }

    @Override
    public List<Task> listCompletedTasks() {
        return query("completed = true", null);
    }

    @Override
    public void addTask(Task task) {
        if (validate(task)) {
            modify(properties.getProperty("sql.insert"), task, false);
        } else {
            Logger.getLogger(TaskManagerDB.class.getName()).log(Level.WARNING, "Validation failed. DB not updated.");
        }

    }

    @Override
    public void updateTask(Task task) {
        if (validate(task)) {
            modify(properties.getProperty("sql.update"), task, true);
        } else {
            Logger.getLogger(TaskManagerDB.class.getName()).log(Level.WARNING, "Validation failed. DB not updated.");
        }
    }

    @Override
    public void markAsCompleted(int id, boolean completed) {
        // TODO
    }

    @Override
    public void removeTask(int id) {
        update(properties.getProperty("sql.delete"), id);
    }

    @Override
    public Task findTask(final int id) {
        List<Task> tasks = query("id = " + id, null);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    private boolean validate(Task task) {
        return !task.getDescription().isEmpty();
    }

}
