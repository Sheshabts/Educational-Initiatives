import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

// Observer Pattern
interface ScheduleObserver {
    void notify(String message);
}

class ConsoleNotifier implements ScheduleObserver {
    public void notify(String message) {
        System.out.println(message);
    }
}

// Logger Singleton
class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
    public void log(String msg) {
        System.out.println("[LOG] " + msg);
    }
}

// Task class
class Task {
    private String description;
    private LocalTime startTime, endTime;
    private String priority;
    private boolean completed;
    public Task(String description, LocalTime start, LocalTime end, String priority) {
        this.description = description;
        this.startTime = start;
        this.endTime = end;
        this.priority = priority;
        this.completed = false;
    }
    public String getDescription() { return description; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean c) { completed = c; }
    public void setTimes(LocalTime start, LocalTime end) { startTime = start; endTime = end; }
    public void setPriority(String p) { priority = p; }
    public void setDescription(String d) { description = d; }
    @Override
    public String toString() {
        String status = completed ? "[Completed]" : "";
        return String.format("%s - %s: %s [%s] %s", startTime, endTime, description, priority, status);
    }
}

// Factory Pattern
class TaskFactory {
    public static Task createTask(String description, String start, String end, String priority) throws Exception {
        try {
            LocalTime st = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime et = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"));
            if (et.isBefore(st) || et.equals(st)) throw new Exception("End time must be after start time.");
            return new Task(description, st, et, priority);
        } catch (Exception e) {
            throw new Exception("Invalid time format. Use HH:mm.");
        }
    }
}

// Singleton Pattern
class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks = new ArrayList<>();
    private List<ScheduleObserver> observers = new ArrayList<>();
    private ScheduleManager() {}
    public static ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }
    public void addObserver(ScheduleObserver obs) { observers.add(obs); }
    private void notifyObservers(String msg) { observers.forEach(o -> o.notify(msg)); }
    public void addTask(Task t) {
        for (Task existing : tasks) {
            if (overlaps(existing, t)) {
                notifyObservers("Error: Task conflicts with existing task \"" + existing.getDescription() + "\".");
                Logger.getInstance().log("Conflict detected for task: " + t.getDescription());
                return;
            }
        }
        tasks.add(t);
        Logger.getInstance().log("Task added: " + t.getDescription());
        notifyObservers("Task added successfully. No conflicts.");
    }
    public void removeTask(String description) {
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getDescription().equalsIgnoreCase(description)) {
                it.remove();
                Logger.getInstance().log("Task removed: " + description);
                notifyObservers("Task removed successfully.");
                return;
            }
        }
        notifyObservers("Error: Task not found.");
    }
    public void viewTasks() {
        if (tasks.isEmpty()) {
            notifyObservers("No tasks scheduled for the day.");
            return;
        }
        tasks.stream()
            .sorted(Comparator.comparing(Task::getStartTime))
            .forEach(t -> System.out.println(t));
    }
    public void editTask(String description, String newDesc, String newStart, String newEnd, String newPriority) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                try {
                    LocalTime st = LocalTime.parse(newStart, DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime et = LocalTime.parse(newEnd, DateTimeFormatter.ofPattern("HH:mm"));
                    if (et.isBefore(st) || et.equals(st)) throw new Exception();
                    t.setDescription(newDesc);
                    t.setTimes(st, et);
                    t.setPriority(newPriority);
                    Logger.getInstance().log("Task edited: " + newDesc);
                    notifyObservers("Task edited successfully.");
                } catch (Exception e) {
                    notifyObservers("Error: Invalid time format.");
                }
                return;
            }
        }
        notifyObservers("Error: Task not found.");
    }
    public void markCompleted(String description) {
        for (Task t : tasks) {
            if (t.getDescription().equalsIgnoreCase(description)) {
                t.setCompleted(true);
                Logger.getInstance().log("Task marked completed: " + description);
                notifyObservers("Task marked as completed.");
                return;
            }
        }
        notifyObservers("Error: Task not found.");
    }
    public void viewByPriority(String priority) {
        List<Task> filtered = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriority().equalsIgnoreCase(priority)) filtered.add(t);
        }
        if (filtered.isEmpty()) {
            notifyObservers("No tasks with priority " + priority + ".");
            return;
        }
        filtered.stream().sorted(Comparator.comparing(Task::getStartTime)).forEach(t -> System.out.println(t));
    }
    private boolean overlaps(Task t1, Task t2) {
        return !(t1.getEndTime().isBefore(t2.getStartTime()) || t2.getEndTime().isBefore(t1.getStartTime()));
    }
}

// Main Console Application
public class AstronautScheduleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ScheduleManager manager = ScheduleManager.getInstance();
        manager.addObserver(new ConsoleNotifier());
        Logger logger = Logger.getInstance();
        System.out.println("Astronaut Daily Schedule Organizer");
        while (true) {
            System.out.println("\nChoose operation: add, remove, view, edit, complete, filter, exit");
            String op = sc.nextLine().trim().toLowerCase();
            try {
                switch (op) {
                    case "add":
                        System.out.print("Description: "); String desc = sc.nextLine();
                        System.out.print("Start Time (HH:mm): "); String st = sc.nextLine();
                        System.out.print("End Time (HH:mm): "); String et = sc.nextLine();
                        System.out.print("Priority (High/Medium/Low): "); String pr = sc.nextLine();
                        try {
                            Task t = TaskFactory.createTask(desc, st, et, pr);
                            manager.addTask(t);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            logger.log("Failed to add task: " + desc);
                        }
                        break;
                    case "remove":
                        System.out.print("Description to remove: "); String rdesc = sc.nextLine();
                        manager.removeTask(rdesc);
                        break;
                    case "view":
                        manager.viewTasks();
                        break;
                    case "edit":
                        System.out.print("Description to edit: "); String edesc = sc.nextLine();
                        System.out.print("New Description: "); String ndesc = sc.nextLine();
                        System.out.print("New Start Time (HH:mm): "); String nst = sc.nextLine();
                        System.out.print("New End Time (HH:mm): "); String net = sc.nextLine();
                        System.out.print("New Priority (High/Medium/Low): "); String npr = sc.nextLine();
                        manager.editTask(edesc, ndesc, nst, net, npr);
                        break;
                    case "complete":
                        System.out.print("Description to mark complete: "); String cdesc = sc.nextLine();
                        manager.markCompleted(cdesc);
                        break;
                    case "filter":
                        System.out.print("Priority to filter (High/Medium/Low): "); String fpr = sc.nextLine();
                        manager.viewByPriority(fpr);
                        break;
                    case "exit":
                        System.out.println("Exiting. Goodbye!");
                        return;
                    default:
                        System.out.println("Unknown operation. Try again.");
                }
            } catch (Exception e) {
                logger.log("Exception: " + e.getMessage());
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
