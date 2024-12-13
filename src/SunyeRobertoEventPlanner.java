import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class SunyeRobertoEventPlanner {
    private static ArrayList<SunyeRobertoEvent> events = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;
        do {
            displayMenu();
            option = getValidInput("Seleccione una opción (1-5): ", 1, 5);
            processOption(option);
        } while (option != 5);
    }

    private static void displayMenu() {
        System.out.println("\n--- LSINNOVA Events Event Planner ---");
        System.out.println("1. Añadir evento");
        System.out.println("2. Borrar evento");
        System.out.println("3. Listar eventos");
        System.out.println("4. Marcar/desmarcar tarea de un evento");
        System.out.println("5. Salir");
    }

    private static void processOption(int option) {
        switch (option) {
            case 1:
                addEvent();
                break;
            case 2:
                deleteEvent();
                break;
            case 3:
                listEvents();
                break;
            case 4:
                toggleTaskCompletion();
                break;
            case 5:
                System.out.println("\u001B[32mSaliendo del programa.\u001B[0m");
                break;
            default:
                System.out.println("\u001B[31mOpción no válida.\u001B[0m");
        }
    }

    private static void addEvent() {
        System.out.print("Ingrese el titulo del evento: ");
        String title = scanner.nextLine();

        LocalDate date = getValidDate("Ingrese la fecha del evento (formato: yyyy-mm-dd): ");
        if (date == null) return;

        System.out.print("Ingrese la prioridad (HIGH, MEDIUM, LOW): ");
        String priorityInput = scanner.nextLine().toUpperCase();
        SunyeRobertoEvent.Priority priority = getValidPriority(priorityInput);
        if (priority == null) return;

        SunyeRobertoEvent event = new SunyeRobertoEvent(title, date, priority);
        String addTasks = getValidYesNoInput("¿Desea agregar tareas al evento? (si/no): ");
        if (addTasks.equalsIgnoreCase("si")) {
            int taskCount = getValidInput("¿Cuántas tareas desea agregar?: ", 1, Integer.MAX_VALUE);
            for (int i = 0; i < taskCount; i++) {
                System.out.print("Descripción de la tarea " + (i + 1) + ": ");
                event.addTask(scanner.nextLine());
            }
        }

        events.add(event);
        System.out.println("\u001B[32mEvento añadido con exito.\u001B[0m");
    }

    private static SunyeRobertoEvent.Priority getValidPriority(String priorityInput) {
        while (true) {
            try {
                return SunyeRobertoEvent.Priority.valueOf(priorityInput);
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31mPrioridad no válida. Use HIGH, MEDIUM o LOW.\u001B[0m");
                System.out.print("Ingrese la prioridad (HIGH, MEDIUM, LOW): ");
                priorityInput = scanner.nextLine().toUpperCase();
            }
        }
    }

    private static void deleteEvent() {
        System.out.print("Ingrese el titulo del evento a borrar: ");
        String title = scanner.nextLine();

        boolean removed = events.removeIf(event -> event.getTitle().equals(title));
        if (removed) {
            System.out.println("\u001B[32mEvento borrado con exito.\u001B[0m");
        } else {
            System.out.println("\u001B[31mNo se encontró un evento con ese titulo.\u001B[0m");
        }
    }

    private static void listEvents() {
        if (events.isEmpty()) {
            System.out.println("\u001B[33mNo hay eventos registrados.\u001B[0m");
            return;
        }
        events.forEach(System.out::println);
    }

    private static void toggleTaskCompletion() {
        System.out.print("Ingrese el titulo del evento: ");
        String title = scanner.nextLine();

        SunyeRobertoEvent event = events.stream()
                .filter(e -> e.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (event == null) {
            System.out.println("\u001B[31mEvento no encontrado.\u001B[0m");
            return;
        }

        ArrayList<SunyeRobertoEventTask> tasks = event.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("\u001B[33mNo hay tareas en este evento.\u001B[0m");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        int taskIndex = getValidInput("Seleccione el número de la tarea a marcar/desmarcar: ", 1, tasks.size()) - 1;
        event.toggleTaskCompletion(taskIndex);
        System.out.println("\u001B[32mTarea actualizada.\u001B[0m");
    }

    private static int getValidInput(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("\u001B[31mPor favor, ingrese un número entre " + min + " y " + max + ".\u001B[0m");
                }
            } else {
                System.out.println("\u001B[31mEntrada no válida. Por favor, ingrese un número.\u001B[0m");
                scanner.nextLine();
            }
        }
    }

    private static LocalDate getValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateString = scanner.nextLine();
            try {
                return LocalDate.parse(dateString);
            } catch (DateTimeException e) {
                System.out.println("\u001B[31mFecha no válida. Use el formato yyyy-MM-dd.\u001B[0m");
            }
        }
    }

    private static String getValidYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("si") || input.equals("no")) {
                return input;
            } else {
                System.out.println("\u001B[31mEntrada no válida. Por favor, ingrese 'si' o 'no'.\u001B[0m");
            }
        }
    }
}