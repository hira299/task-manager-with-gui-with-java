# Task Manager

A simple and intuitive Java Swing-based task management application with reminder functionality. This project demonstrates object-oriented programming concepts, data structures, and GUI development using Java.

## 🚀 Features

- **Task Management**: Add, delete, and mark tasks as complete/incomplete
- **Due Date Tracking**: Set specific due dates and times for tasks
- **Smart Reminders**: Automatic notifications 10 minutes before task deadlines
- **Undo Functionality**: Restore recently deleted tasks
- **User-Friendly GUI**: Clean, colorful interface with intuitive controls
- **Real-time Updates**: Dynamic task list updates and button state management

## 📋 Requirements

- Java 8 or higher
- No external dependencies required (uses only Java standard library)

## 🛠️ Installation

1. **Clone the repository**:
   ```bash
   git clone <your-repository-url>
   cd Task-Manager
   ```

2. **Compile the project**:
   ```bash
   javac -d bin src/*.java
   ```

3. **Run the application**:
   ```bash
   java -cp bin Main
   ```

## 🎯 Usage

### Adding a Task
1. Click the **"Add Task"** button
2. Enter the task name
3. Enter the due date and time in format: `YYYY-MM-DD HH:MM` (e.g., `2024-12-25 14:30`)
4. Click **"Add"** to save the task

### Managing Tasks
- **Select a task** from the list to enable management buttons
- **Delete Task**: Remove the selected task (can be undone)
- **Mark Complete**: Toggle the completion status of the selected task
- **Undo Delete**: Restore the most recently deleted task

### Reminders
- The application automatically checks for upcoming tasks every minute
- You'll receive a popup notification 10 minutes before any task is due
- Only incomplete tasks trigger reminders

## 🏗️ Project Structure

```
Task Manager/
├── src/
│   ├── Main.java              # Application entry point
│   ├── Task.java              # Task data model
│   ├── TaskFunctions.java     # Core business logic
│   ├── TaskManagerGui.java    # GUI implementation
│   └── ReminderManager.java   # Reminder system
└── Task Manager.iml           # IntelliJ IDEA module file
```

## 🔧 Technical Details

### Data Structures Used
- **LinkedList**: Efficient dynamic storage of tasks
- **Stack**: Manages undo functionality for deleted tasks
- **Queue**: Handles reminder notifications in FIFO order

### Key Classes
- **`Task`**: Data model representing individual tasks with name, due date, and completion status
- **`TaskFunctions`**: Core business logic for task operations (add, delete, toggle, undo)
- **`TaskManagerGui`**: Swing-based user interface with custom styling
- **`ReminderManager`**: Background service for task notifications using scheduled executor

### Design Patterns
- **MVC Pattern**: Separation of data (Task), logic (TaskFunctions), and presentation (TaskManagerGui)
- **Observer Pattern**: GUI updates when task data changes
- **Dependency Injection**: Components receive their dependencies through constructors

## 🎨 GUI Features

- **Color-coded Interface**: Different colors for different actions and states
- **Responsive Design**: Buttons enable/disable based on selection state
- **Custom Rendering**: Tasks display with completion status and due dates
- **Scrollable List**: Handles large numbers of tasks efficiently

## 🚨 Reminder System

The reminder system runs in the background and:
- Checks for upcoming tasks every minute
- Shows popup notifications 10 minutes before due time
- Only notifies for incomplete tasks
- Automatically cleans up expired notifications

## 📝 Code Quality

- **Comprehensive Comments**: Well-documented code with educational comments
- **Error Handling**: Input validation and exception handling
- **Clean Architecture**: Modular design with clear separation of concerns
- **Resource Management**: Proper shutdown hooks and thread management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

Created as a Java programming project demonstrating:
- Object-oriented programming concepts
- GUI development with Swing
- Data structure implementation
- Multi-threading and background services
- Event-driven programming

---

**Note**: This is a learning project that showcases fundamental Java programming concepts and GUI development. Feel free to use it as a reference or starting point for your own projects! 
