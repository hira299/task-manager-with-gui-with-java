import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    //Formats the date/time for tasks (like 2025-06-12 18:30)
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //attribute defines(Stores the task name, due date, and whether it's completed)
    private String name;
    private LocalDateTime dueDate;
    private boolean completed;
    //Constructor
    public Task(String name, LocalDateTime dueDate) {
        this.name = name.trim();
        this.dueDate = dueDate;
        this.completed = false;
    }
    //Getters and Setters for name, dueDate, completed
//Helps other classes access or change task details safely
    public String getName() {
        return name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        // 1. Start with the base information: name and due date.
        String baseInfo = name + " (Due: " + dueDate.format(formatter) + ")";

        // 2. Check if the task is completed.
        if (completed) {
            // 3. If it is, add the "[Completed]" text and return.
            return baseInfo + " [Completed]";
        } else {
            // 4. If not, just return the base information.
            return baseInfo;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return completed == task.completed &&
                Objects.equals(name, task.name) &&
                Objects.equals(dueDate, task.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dueDate, completed);
    }
}