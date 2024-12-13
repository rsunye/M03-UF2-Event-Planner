import java.time.LocalDate;
import java.util.ArrayList;

class SunyeRobertoEvent {
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    private String title;
    private LocalDate date;
    private Priority priority;
    private ArrayList<SunyeRobertoEventTask> tasks;

    public SunyeRobertoEvent(String title, LocalDate date, Priority priority) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.tasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void addTask(String taskDescription) {
        tasks.add(new SunyeRobertoEventTask(taskDescription));
    }

    public void toggleTaskCompletion(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).toggleCompleted();
        } else {
            System.out.println("\u001B[31mIndice de tarea no valido.\u001B[0m");
        }
    }

    public ArrayList<SunyeRobertoEventTask> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        long completedTasks = tasks.stream().filter(SunyeRobertoEventTask::isCompleted).count();
        return "Evento: " + title + " | Fecha: " + date + " | Prioridad: " + priority +
                " | Tareas Completadas: " + completedTasks + "/" + tasks.size();
    }
}