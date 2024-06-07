import java.util.Scanner;

// Task class to store task details
class Task 
{
    String name;
    int day, month, year;
    boolean isUrgent;
    boolean isCompleted = false;
    String category;

    // Constructor to initialize task details
    public Task(String name, int day, int month, int year, boolean isUrgent, String category) 
    {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.isUrgent = isUrgent;
        this.category = category;
    }

    // Override toString() method to display task details
    @Override
    public String toString() 
    {
        return "Task: " + name + ", Due: " + day + "/" + month + "/" + year + ", Urgency: " + 
               (isUrgent ? "Urgent" : "Normal") + ", Category: " + category + ", Completed: " + 
               (isCompleted ? "Yes" : "No");
    }
}

// Node class for the doubly linked list
class Node 
{
    Task task;
    Node next;
    Node prev;

    // Constructor to initialize node with task
    public Node(Task task) 
    {
        this.task = task;
        this.next = null;
        this.prev = null;
    }
}

// Doubly linked list class for managing tasks
class DoublyLinkedList 
{
    Node head;
    Node tail;

    // Add a new task to the end of the list
    // Time complexity: O(1)
    public void add(Task task) 
    {
        Node newNode = new Node(task);
        if (head == null) 
        {
            head = tail = newNode;
        } 
        else 
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Remove the front task from the list (used for dequeue)
    // Time complexity: O(1)
    public Task removeFront() 
    {
        if (head == null) 
        {
            return null;
        }
        Task task = head.task;
        head = head.next;
        if (head == null) 
        {
            tail = null;
        } 
        else 
        {
            head.prev = null;
        }
        return task;
    }

    // Remove the last task from the list (used for pop)
    // Time complexity: O(1)
    public Task removeLast() 
    {
        if (tail == null) 
        {
            return null;
        }
        Task task = tail.task;
        tail = tail.prev;
        if (tail == null) 
        {
            head = null;
        } 
        else 
        {
            tail.next = null;
        }
        return task;
    }

    // Get a task by index
    // Time complexity: O(n)
    public Task get(int index) 
    {
        Node current = head;
        int count = 0;
        while (current != null) 
        {
            if (count == index) 
            {
                return current.task;
            }
            count++;
            current = current.next;
        }
        return null;
    }

    // Check if the list is empty
    // Time complexity: O(1)
    public boolean isEmpty() 
    {
        return head == null;
    }

    // Remove a node from the list
    // Time complexity: O(1)
    public void remove(Node node) 
    {
        if (node == head) 
        {
            head = head.next;
            if (head != null) 
            {
                head.prev = null;
            }
        } 
        else if (node == tail) 
        {
            tail = tail.prev;
            if (tail != null) 
            {
                tail.next = null;
            }
        } 
        else 
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        if (head == null) 
        {
            tail = null;
        }
    }

    // Sort the list of tasks by their due dates
    // Time complexity: O(n^2)
    public void sort() 
    {
        if (head == null || head.next == null) 
        {
            return;
        }

        Node sorted = null;
        Node current = head;

        while (current != null) 
        {
            Node next = current.next;
            current.prev = current.next = null;
            sorted = sortedInsert(sorted, current);
            current = next;
        }
        head = sorted;
        tail = head;

        while (tail.next != null) 
        {
            tail = tail.next;
        }
    }

    // Insert a node in a sorted list
    // Time complexity: O(n)
    private Node sortedInsert(Node sorted, Node newNode) 
    {
        // If the sorted list is empty or the new node should be inserted at the beginning
        if (sorted == null || compareTasks(newNode.task, sorted.task) <= 0) 
        {
            newNode.next = sorted;  // The new node's next points to the current head of the sorted list
            if (sorted != null) 
            {
                sorted.prev = newNode;  // The current head's previous points to the new node
            }
            return newNode;  // The new node becomes the new head of the sorted list
        }

        Node current = sorted;  // Start from the head of the sorted list

        // Traverse the sorted list to find the correct insertion point
        while (current.next != null && compareTasks(newNode.task, current.next.task) > 0) 
        {
            current = current.next;
        }

        // Insert the new node after 'current' and before 'current.next'
        newNode.next = current.next;  // New node's next points to current's next
        if (current.next != null) 
        {
            current.next.prev = newNode;  // Current's next's previous points to the new node
        }
        current.next = newNode;  // Current's next points to the new node
        newNode.prev = current;  // New node's previous points to current

        return sorted;  // Return the head of the sorted list
    }

    // Compare two tasks by their due dates
    // Time complexity: O(1)
    private int compareTasks(Task t1, Task t2) 
    {
        if (t1.year != t2.year) 
        {
            return t1.year - t2.year;
        }
        if (t1.month != t2.month) 
        {
            return t1.month - t2.month;
        }
        return t1.day - t2.day;
    }

    // Get a node by task reference
    // Time complexity: O(n)
    public Node getNodeByTask(Task task) 
    {
        Node current = head;

        while (current != null) 
        {
            if (current.task == task) 
            {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}

// Queue class to store completed tasks
class Queue 
{
    private DoublyLinkedList queue = new DoublyLinkedList();

    // Enqueue a task and mark it as completed
    // Time complexity: O(1)
    public void enqueue(Task task) 
    {
        task.isCompleted = true;
        queue.add(task);
    }

    // Dequeue a completed task
    // Time complexity: O(1)
    public Task dequeue() 
    {
        return queue.removeFront();
    }

    // Check if the queue is empty
    // Time complexity: O(1)
    public boolean isEmpty() 
    {
        return queue.isEmpty();
    }

    // Print the completed tasks
    // Time complexity: O(n)
    public void printCompletedTasks() 
    {
        System.out.println("Completed tasks:");
        if (queue.isEmpty()) 
        {
            System.out.println("No completed tasks.");
        } 
        else 
        {
            Node current = queue.head;
            while (current != null) 
            {
                System.out.println(current.task);
                current = current.next;
            }
        }
    }
}

// Stack class to store urgent tasks
class Stack 
{
    private DoublyLinkedList stack = new DoublyLinkedList();

    // Push an urgent task onto the stack
    // Time complexity: O(1)
    public void push(Task task) 
    {
        if (task.isUrgent && !task.isCompleted) 
        {
            stack.add(task);
        }
    }

    // Pop an urgent task from the stack
    // Time complexity: O(1)
    public Task pop() 
    {
        return stack.removeLast();
    }

    // Check if the stack is empty
    // Time complexity: O(1)
    public boolean isEmpty() 
    {
        return stack.isEmpty();
    }

    // Print the urgent tasks
    // Time complexity: O(n)
    public void printUrgentTasks() 
    {
        System.out.println("Urgent tasks:");
        if (stack.isEmpty()) 
        {
            System.out.println("No urgent tasks.");
        } 
        else 
        {
            Node current = stack.head;
            while (current != null) 
            {
                if (!current.task.isCompleted) 
                {
                    System.out.println(current.task);
                }
                current = current.next;
            }
        }
    }
}

// TaskList class to manage tasks
class TaskList 
{
    DoublyLinkedList tasks = new DoublyLinkedList();

    // Add a task to the list
    // Time complexity: O(1)
    public void addTask(Task task) 
    {
        tasks.add(task);
    }

    // Get a task by user input
    // Time complexity: O(n)
    public Task getTaskByUserInput(int index) 
    {
        Node current = tasks.head;
        int count = 1;

        while (current != null) 
        {
            if (!current.task.isCompleted && count == index) 
            {
                return current.task;
            }
            if (!current.task.isCompleted) 
            {
                count++;
            }
            current = current.next;
        }
        return null;
    }

    // Sort tasks by due date
    // Time complexity: O(n^2)
    public void sortTasksByDate() 
    {
        tasks.sort();
        printTasks("Available tasks sorted by due date:");
    }

    // Print tasks by category
    // Time complexity: O(n)
    public void printTasksByCategory() 
    {
        String[] categories = {"study", "work", "personal"};
        for (String category : categories) 
        {
            boolean found = false;
            System.out.println("Category: " + category);
            Node current = tasks.head;

            while (current != null) 
            {
                if (current.task.category.equalsIgnoreCase(category) && !current.task.isCompleted) 
                {
                    System.out.println(current.task);
                    found = true;
                }
                current = current.next;
            }
            if (!found) 
            {
                System.out.println("No task in this category.");
            }
        }
    }

    // Print all tasks
    // Time complexity: O(n)
    private void printTasks(String message) 
    {
        System.out.println(message);
        boolean anyAvailable = false;
        Node current = tasks.head;

        while (current != null) 
        {
            if (!current.task.isCompleted) 
            {
                System.out.println(current.task);
                anyAvailable = true;
            }
            current = current.next;
        }
        if (!anyAvailable) 
        {
            System.out.println("No available tasks to display.");
        }
    }
}

// TaskManagerSystem class to manage user interaction
public class TaskManagerSystem 
{
    private static Scanner scanner = new Scanner(System.in);
    private static TaskList taskList = new TaskList();
    private static Queue completedTasks = new Queue();
    private static Stack urgentTasks = new Stack();

    public static void main(String[] args) 
    {
        int option;

        do 
        {
            displayMenu();
            option = getOption();
            processOption(option);
        } while (option != 7);
    }

    // Display the main menu
    // Time complexity: O(1)
    private static void displayMenu() 
    {
        System.out.println("\nTask Management System");
        System.out.println("1. Add Task");
        System.out.println("2. Show All Tasks by Due Date");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. Show Completed Tasks");
        System.out.println("5. Show Urgent Tasks");
        System.out.println("6. Show Tasks by Category");
        System.out.println("7. Exit");
        System.out.print("Enter option: ");
    }

    // Get user option
    // Time complexity: O(1)
    private static int getOption() 
    {
        int option;
        try 
        {
            option = scanner.nextInt();
            scanner.nextLine();
        } 
        catch (Exception e) 
        {
            System.out.println("Invalid input, please enter a number.");
            scanner.next();
            option = 0;
        }
        return option;
    }

    // Process user option
    // Time complexity: O(n)
    private static void processOption(int option) 
    {
        switch (option) 
        {
            case 1 -> addTask();
            case 2 -> taskList.sortTasksByDate();
            case 3 -> markTaskAsCompleted();
            case 4 -> completedTasks.printCompletedTasks();
            case 5 -> urgentTasks.printUrgentTasks();
            case 6 -> taskList.printTasksByCategory();
            case 7 -> System.out.println("Exiting system.");
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    // Add a new task
    // Time complexity: O(1)
    private static void addTask() 
    {
        String name = getStringInput("Enter task name: ");
        int day = getInput("Enter day (1-31): ", 1, 31);
        int month = getInput("Enter month (1-12): ", 1, 12);
        int year = getInput("Enter year (1-9999): ", 1, 9999);

        if (!isValidDate(day, month, year)) 
        {
            System.out.println("Invalid date entered. Please try adding the task again.");
            return;
        }
        boolean isUrgent = getInput("Select priority (1 for Urgent, 2 for Normal): ", 1, 2) == 1;
        String[] categories = {"study", "work", "personal"};
        int categoryIndex = getInput("Select category (1 for Study, 2 for Work, 3 for Personal): ", 1, 3) - 1;
        String category = categories[categoryIndex];

        Task task = new Task(name, day, month, year, isUrgent, category);
        taskList.addTask(task);
        if (isUrgent) 
        {
            urgentTasks.push(task);
        }

        System.out.println("Task added successfully.");
    }

    // Mark a task as completed
    // Time complexity: O(n)
    private static void markTaskAsCompleted() 
    {
        if (taskList.tasks.isEmpty()) 
        {
            System.out.println("Thanks for being productive for today. There is no task to complete.");
            return;
        }
        taskList.sortTasksByDate();
        System.out.println("Select a task to mark as completed:");
        Node current = taskList.tasks.head;
        int index = 1;

        while (current != null) 
        {
            if (!current.task.isCompleted) 
            {
                System.out.printf("%d. %s\n", index, current.task);
                index++;
            }
            current = current.next;
        }

        int userInput = getInput("Enter the number of the task (starting from 1): ", 1, index - 1);
        Task task = taskList.getTaskByUserInput(userInput);

        if (task != null) 
        {
            completedTasks.enqueue(task);
            Node taskNode = taskList.tasks.getNodeByTask(task);

            if (taskNode != null) 
            {
                taskList.tasks.remove(taskNode); // Remove the completed task from the task list
            }
            System.out.println("Task marked as completed.");
        } 
        else 
        {
            System.out.println("Invalid task number or task already completed.");
        }
    }

    // Get input within a range
    // Time complexity: O(1)
    private static int getInput(String message, int min, int max) 
    {
        int input;
        do 
        {
            input = getInput(message);

            if (input < min || input > max) 
            {
                System.out.println("Input out of range. Please try again.");
            }
        } while (input < min || input > max);
        return input;
    }

    // Get input
    // Time complexity: O(1)
    private static int getInput(String message) 
    {
        System.out.print(message);

        while (!scanner.hasNextInt()) 
        {
            scanner.next();
            System.out.println("Invalid input, please enter a number.");
            System.out.print(message);
        }
        return scanner.nextInt();
    }

    // Get string input
    // Time complexity: O(1)
    private static String getStringInput(String message) 
    {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Validate date
    // Time complexity: O(1)
    private static boolean isValidDate(int day, int month, int year) 
    {
        if (month < 1 || month > 12 || day < 1) 
        {
            return false;
        }

        return day <= daysInMonth(month, year);
    }

    // Get days in a month
    // Time complexity: O(1)
    private static int daysInMonth(int month, int year) 
    {
        if (month == 2 && isLeapYear(year)) 
        {
            return 29;
        }

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return daysInMonth[month - 1];
    }

    // Check if a year is a leap year
    // Time complexity: O(1)
    private static boolean isLeapYear(int year) 
    {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}