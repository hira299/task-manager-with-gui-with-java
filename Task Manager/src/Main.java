import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        TaskFunctions taskFunctions = new TaskFunctions();
        // Pass the taskFunctions object itself, not a model from it.
        ReminderManager reminderManager = new ReminderManager(taskFunctions);
        TaskManagerGui taskManagerGui = new TaskManagerGui(taskFunctions, reminderManager);

        reminderManager.start();

        SwingUtilities.invokeLater(taskManagerGui::display);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application...");
            if (reminderManager != null) {
                reminderManager.stop();
                System.out.println("Reminder service stopped.");
            }
        }));
    }
}