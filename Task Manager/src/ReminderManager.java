import javax.swing.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderManager {
    private static final int NOTIFY_BEFORE_MINUTES = 10;
    // DS #3: Queue to manage notified tasks in a FIFO (First-In, First-Out) manner.
    private final Queue<Task> notifiedTasks;
    private ScheduledExecutorService scheduler;
    private TaskFunctions taskFunctionsRef;

    public ReminderManager(TaskFunctions taskFunctions) {
        this.taskFunctionsRef = taskFunctions;
        // A LinkedList can be used as a Queue.
        this.notifiedTasks = new LinkedList<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkTaskNotifications, 0, 1, TimeUnit.MINUTES);
    }

    private void checkTaskNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> currentTasks = taskFunctionsRef.getTasks();

        // Check for new tasks that need a notification.
        for (Task task : currentTasks) {
            if (!notifiedTasks.contains(task) && !task.isCompleted()) {
                LocalDateTime notifyTime = task.getDueDate().minusMinutes(NOTIFY_BEFORE_MINUTES);
                if (!now.isBefore(notifyTime) && now.isBefore(task.getDueDate())) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                            "Reminder: Task \"" + task.getName() + "\" is due.",
                            "Task Due Soon",
                            JOptionPane.WARNING_MESSAGE));
                    notifiedTasks.offer(task); // Add to the back of the queue.
                }
            }
        }

        // Clean up the queue: remove tasks that are past their due date.
        while (notifiedTasks.peek() != null && now.isAfter(notifiedTasks.peek().getDueDate())) {
            notifiedTasks.poll(); // Remove from the front of the queue.
        }
    }

    // stop() and taskDeleted() are unchanged from the array version.
    public void stop() { /* ... same as before ... */ }
    public void taskDeleted(Task task) { notifiedTasks.remove(task); }
}