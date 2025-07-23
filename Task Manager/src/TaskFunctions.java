import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TaskFunctions {
    // DS #1: LinkedList for efficient dynamic storage of tasks.
    private final List<Task> tasks;
    // DS #2: Stack to manage the "Undo Delete" history.
    private final Stack<Task> undoStack;

    public TaskFunctions() {
        this.tasks = new LinkedList<>();
        this.undoStack = new Stack<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean isUndoAvailable() {
        return !undoStack.isEmpty();
    }

    public boolean addTask(String name, String dueStr) {
        try {
            LocalDateTime dueDate = LocalDateTime.parse(dueStr.trim(), Task.formatter);
            Task task = new Task(name, dueDate);
            tasks.add(task); // Simple and efficient add operation.
            return true;
        } catch (DateTimeParseException ex) {
            System.err.println("Invalid date/time format: " + ex.getMessage());
            return false;
        }
    }

    public void deleteTask(Task task) {
        if (task != null && tasks.remove(task)) {
            undoStack.push(task); // Push the deleted task onto the undo stack.
        }
    }

    public void undoDelete() {
        if (!undoStack.isEmpty()) {
            Task taskToRestore = undoStack.pop(); // Pop the last deleted task.
            tasks.add(taskToRestore); // Add it back to the main list.
        }
    }

    public void toggleComplete(Task task) {
        if (task != null) {
            // No structural change, just updating the object's state.
            task.setCompleted(!task.isCompleted());
        }
    }
}