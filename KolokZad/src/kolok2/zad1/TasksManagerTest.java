package kolok2.zad1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

class DeadlineNotValidException extends Exception{
    public DeadlineNotValidException(String message) {
        super(message);
    }
}

interface iTask{
    Integer getPriority();
    LocalDateTime getDedelineDate();
}

class Task implements iTask {
    String category;
    String name;
    String description;

    public Task(){
    }

    public Task(String category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public LocalDateTime getDedelineDate() {
        return LocalDateTime.MAX;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                "}\n";
    }
}

class PriorityTask extends Task implements iTask{

    int priority;

    public PriorityTask(String category, String name, String description, int priority) {
        super(category, name, description);
        this.priority = priority;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    @Override
    public LocalDateTime getDedelineDate() {
        return LocalDateTime.MAX;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                "priority=" + priority +
                "}\n";
    }
}

class DeadlineTask extends Task implements iTask{

    LocalDateTime deadline;

    public DeadlineTask(String category, String name, String description, LocalDateTime deadline) {
        super(category, name, description);
        this.deadline = deadline;
    }

    @Override
    public Integer getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public LocalDateTime getDedelineDate() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\''+
                 ", deadline=" + deadline +
                "}\n";
    }
}

class FullTask extends Task implements iTask{

    int priority;
    LocalDateTime date;

    public FullTask(String category, String name, String description, LocalDateTime deadline,int priority) {
        super(category, name, description);
        this.priority = priority;
        this.date = deadline;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    @Override
    public LocalDateTime getDedelineDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + date +
                ", priority=" + priority +
                "}\n";
    }
}

class TaskCreator{
    //[категорија][име_на_задача],[oпис],[рок_за_задачата],[приоритет]
    //School,NP,lab 1 po NP,2020-06-23T23:59:59.000,1
    public static Task createTask(String line) throws DeadlineNotValidException {
        String[] parts = line.split(",");
        String category = parts[0];
        String name = parts[1];
        String description = parts[2];
        if (parts.length == 4){
            if (parts[3].length() == 1){
                int priority = Integer.parseInt(parts[3]);
                return new PriorityTask(category, name,description,priority);
            }
            LocalDateTime date = LocalDateTime.parse(parts[3]);
            if (validate(date)){
                return new DeadlineTask(category, name, description, date);
            }else {
                throw new DeadlineNotValidException("");
            }
        } else if (parts.length == 5) {
            LocalDateTime date = LocalDateTime.parse(parts[3]);
            if (validate(date)){
                int priority = Integer.parseInt(parts[4]);
                return new FullTask(category, name, description, date, priority);
            }else {
                throw new DeadlineNotValidException("");
            }
        }else
            return new Task(category, name, description);
    }

    public static boolean validate(LocalDateTime deadline){
        return deadline.isBefore(LocalDateTime.now());
    }
}

class TaskManager{

    HashMap<String, List<Task>> tasksByCategory;

    public TaskManager() {
        tasksByCategory = new HashMap<>();
    }

    public void readTasks(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        bf.lines().forEach(line -> {
            Task task = new Task();
            try {
                task = TaskCreator.createTask(line);
            } catch (DeadlineNotValidException e) {
                return;
            }
            if (tasksByCategory.get(task.category) == null){
                tasksByCategory.put(task.category, new ArrayList<>());
            }
            tasksByCategory.get(task.category).add(task);
        });
    }

    public void printTasks(PrintStream out, boolean includeP, boolean includeC) {
        if (includeC && includeP){
            Comparator comparator = Comparator.comparing(Task::getCategory).thenComparing(iTask::getPriority).thenComparing(Task::getDedelineDate);
            tasksByCategory.entrySet().forEach(entry ->{
                System.out.println(entry.getKey().toUpperCase());
                entry.getValue().stream().sorted(comparator).forEach(System.out::print);
            });
        } else if (includeC && !includeP) {
            Comparator comparator = Comparator.comparing(Task::getCategory);
            tasksByCategory.entrySet().forEach(entry ->{
                System.out.println(entry.getKey().toUpperCase());
                entry.getValue().stream().sorted(comparator).forEach(System.out::print);
            });
        } else if (!includeC && includeP) {
            Comparator comparator = Comparator.comparing(Task::getPriority);
            tasksByCategory.entrySet().forEach(entry ->{
                entry.getValue().stream().sorted(comparator).forEach(System.out::print);
            });
        }else {
            tasksByCategory.entrySet().forEach(entry ->{
                entry.getValue().forEach(System.out::print);
            });
        }
    }
}

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
