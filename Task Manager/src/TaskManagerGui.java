// Importing necessary Swing GUI and layout packages
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;

// Main GUI class for Task Manager
public class TaskManagerGui {

    // ------------ Attributes (Variables inside a class) ------------
    // Color constants used throughout the UI (These are final = constant values)
    private static final Color BACKGROUND_COLOR = new Color(255, 239, 245); // Light pink background
    private static final Color PANEL_COLOR = new Color(224, 255, 255); // Light blue for panels
    private static final Color BUTTON_BG = new Color(255, 224, 204); // Not used but ready for other buttons
    private static final Color ADD_BUTTON = new Color(204, 255, 204); // Green for Add button
    private static final Color DELETE_BUTTON = new Color(255, 204, 204); // Red for Delete button
    private static final Color TOGGLE_BUTTON = new Color(255, 255, 204); // Yellow for toggle complete/incomplete
    private static final Color UNDO_BUTTON_COLOR = new Color(204, 224, 255); // Blue-ish for Undo
    private static final Color LIST_BG = new Color(255, 255, 240); // Background for task list
    private static final Color COMPLETED_COLOR = new Color(204, 204, 255); // Highlight for completed task
    private static final Color HEADER_COLOR = new Color(204, 204, 255); // Header background color
    private static final Color TITLE_COLOR = new Color(80, 60, 120); // Title text color
    private static final Color SELECTION_COLOR = new Color(180, 200, 255); // Selected task highlight

    // GUI components
    private JFrame frame; // Window frame that holds all GUI components
    private JList<Task> taskJList; // JList shows the list of tasks (uses ListModel internally — a data structure)
    private JButton addTaskButton, deleteTaskButton, toggleCompleteButton, undoButton; // Four buttons
    private JButton Bleh;
    // References to other classes (objects)
    private TaskFunctions taskFunctionsRef; // Logic handler for tasks (add, delete, etc.)
    private ReminderManager reminderManagerRef; // Handles reminders

    // Constructor to set up everything when the object is created
    public TaskManagerGui(TaskFunctions taskFunctions, ReminderManager reminderManager) {
        this.taskFunctionsRef = taskFunctions; // Store passed object (dependency injection)
        this.reminderManagerRef = reminderManager;

        //methods
        initComponents(); // Create and style the GUI
        setupActionListeners(); // Add functionality to buttons
        showWelcomeMessage(); // Show popup message at launch
    }

    // Initialize the GUI components and layout
    private void initComponents() {
        frame = new JFrame("Simple Task Manager"); // Main window title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program on exit
        frame.setSize(650, 450); // Size of window
        frame.setLocationRelativeTo(null); // Center window on screen
        frame.setLayout(new BorderLayout(15, 15)); // Layout manager to place components around a border
        frame.getContentPane().setBackground(BACKGROUND_COLOR); // Set background color

        // Create header/title
        JPanel headerPanel = new JPanel(); // Panel at the top
        headerPanel.setBackground(HEADER_COLOR); // Set background
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Add padding around the title

        JLabel title = new JLabel("Task Manager"); // Label as title
        title.setFont(new Font("Segoe UI", Font.BOLD, 24)); // Font styling
        title.setForeground(TITLE_COLOR); // Title text color
        headerPanel.add(title); // Add label to panel
        frame.add(headerPanel, BorderLayout.NORTH); // Place header panel at top

        // --------------------- DATA STRUCTURE USED HERE -------------------------
        // Create task list from array of tasks
        // getTasks() likely returns a LinkedList<Task> → convert it to Task[] array using toArray
        taskJList = new JList<>(taskFunctionsRef.getTasks().toArray(new Task[0]));
        // LinkedList<Task> → Task[] → passed to JList (JList uses ListModel internally)

        taskJList.setBackground(LIST_BG); // Set background of task list
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one task can be selected at a time
        taskJList.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font size and style
        taskJList.setCellRenderer(new TaskCellRenderer()); // Apply custom style for each item

        JScrollPane scrollPane = new JScrollPane(taskJList); // Scrollable container for list
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1)
        ));
        frame.add(scrollPane, BorderLayout.CENTER); // Add scrollable task list to center

        // ---------------- Buttons -------------------
        JPanel buttonPanel = new JPanel(); // Bottom panel with buttons
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        buttonPanel.setLayout(new GridLayout(1, 4, 15, 0)); // 1 row, 4 columns, spacing between buttons

        // Create and style buttons
        addTaskButton = createStyledButton("Add Task", ADD_BUTTON);
        deleteTaskButton = createStyledButton("Delete Task", DELETE_BUTTON);
        toggleCompleteButton = createStyledButton("Mark Complete", TOGGLE_BUTTON);
        undoButton = createStyledButton("Undo Delete", UNDO_BUTTON_COLOR);

        // Disable buttons initially (until task is selected)
        deleteTaskButton.setEnabled(false);
        toggleCompleteButton.setEnabled(false);
        undoButton.setEnabled(false);

        // Add buttons to button panel and then add panel to the frame
        buttonPanel.add(addTaskButton);
        buttonPanel.add(deleteTaskButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(toggleCompleteButton);
        frame.add(Bleh);
        frame.add(buttonPanel, BorderLayout.SOUTH); // Place at bottom
    }

    // Creates a button with given style
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor); // Set button color
        button.setForeground(new Color(60, 60, 80)); // Text color
        button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Font
        button.setFocusPainted(false); // Don't show focus outline
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1), // Border
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Padding
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor changes on hover
        return button;
    }

    // Add click functionality to all buttons
    private void setupActionListeners() {
        // When "Add Task" is clicked, open dialog
        addTaskButton.addActionListener(e -> showAddTaskDialog());

        // When "Delete Task" is clicked, remove selected task
        deleteTaskButton.addActionListener(e -> deleteSelectedTask());

        // Toggle complete/incomplete
        toggleCompleteButton.addActionListener(e -> toggleCompleteSelectedTask());

        // Undo last deleted task
        undoButton.addActionListener(e -> {
            taskFunctionsRef.undoDelete(); // Restore deleted task
            refreshTaskList(); // Update task list
        });

        // Enable/disable buttons when selection changes
        taskJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonsState();
            }
        });
    }

    // Show message when app starts
    private void showWelcomeMessage() {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame,
                "Add new tasks with due dates and use the 'Undo Delete' feature!\n" +
                        "Due date format: yyyy-MM-dd HH:mm (e.g., " + LocalDateTime.now().format(Task.formatter) + ")\n" +
                        "You will be notified 10 minutes before a task is due.",
                "Welcome to Simple Task Manager",
                JOptionPane.INFORMATION_MESSAGE));
    }

    // Show the GUI window
    public void display() {
        frame.setVisible(true); // Make frame visible
    }

    // Refresh task list view (reloads task data from LinkedList to JList)
    private void refreshTaskList() {
        // ------------------- DATA STRUCTURE AGAIN ----------------------
        // Convert the LinkedList<Task> into Task[] again
        taskJList.setListData(taskFunctionsRef.getTasks().toArray(new Task[0]));
        updateButtonsState(); // Refresh button state
    }

    // Enable/disable buttons based on whether a task is selected or not
    private void updateButtonsState() {
        boolean taskSelected = taskJList.getSelectedIndex() != -1;

        deleteTaskButton.setEnabled(taskSelected);
        toggleCompleteButton.setEnabled(taskSelected);
        undoButton.setEnabled(taskFunctionsRef.isUndoAvailable()); // Enable if undo possible

        if (taskSelected) {
            Task selectedTask = taskJList.getSelectedValue();
            if (selectedTask != null) {
                toggleCompleteButton.setText(
                        selectedTask.isCompleted() ? "Mark Incomplete" : "Mark Complete"
                );
            }
        } else {
            toggleCompleteButton.setText("Mark Complete / Incomplete");
        }
    }

    // Popup input boxes to add a new task
    private void showAddTaskDialog() {
        String name = JOptionPane.showInputDialog(frame, "Enter Task Name:", "Add Task", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;

        String dueStr = JOptionPane.showInputDialog(frame, "Enter Due Date (yyyy-MM-dd HH:mm):", "Add Task", JOptionPane.PLAIN_MESSAGE);
        if (dueStr == null || dueStr.trim().isEmpty()) return;

        if (taskFunctionsRef.addTask(name, dueStr)) {
            refreshTaskList(); // Add successful → refresh display
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please use yyyy-MM-dd HH:mm", "Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete the selected task after confirmation
    private void deleteSelectedTask() {
        Task selectedTask = taskJList.getSelectedValue();
        if (selectedTask != null) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this task?\n" + selectedTask.toString(),
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                taskFunctionsRef.deleteTask(selectedTask); // Delete from LinkedList
                reminderManagerRef.taskDeleted(selectedTask); // Cancel reminder
                refreshTaskList(); // Refresh UI
            }
        }
    }

    // Toggle task's completion status (done or not)
    private void toggleCompleteSelectedTask() {
        Task selectedTask = taskJList.getSelectedValue();
        if (selectedTask != null) {
            taskFunctionsRef.toggleComplete(selectedTask); // Flip boolean flag
            refreshTaskList();
        }
    }

    // Custom list renderer to show completed tasks with strikethrough
    private static class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setOpaque(true); // Background visible
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

            if (value instanceof Task) {
                Task task = (Task) value;
                // If task is marked complete → strikethrough text and change colors
                if (task.isCompleted()) {
                    label.setText("<html><s>" + task.toString() + "</s></html>");
                    label.setForeground(new Color(120, 120, 140));
                    label.setBackground(COMPLETED_COLOR);
                } else {
                    label.setText(task.toString());
                    label.setForeground(new Color(60, 70, 100));
                    label.setBackground(LIST_BG);
                }
            }

            // Highlight selected task
            if (isSelected) {
                label.setBackground(SELECTION_COLOR);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 140, 220), 1),
                        BorderFactory.createEmptyBorder(4, 9, 4, 9)
                ));
            }
            return label;
        }
    }
}
