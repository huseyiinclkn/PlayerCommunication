package playercommunication;

public class Main {
    public static void main(String[] args) { // This is the main method, which serves as the entry point for the Java application. It is static, meaning it belongs to the class itself rather than an instance of the class. 'String[] args' is an array of strings that can be used to pass command-line arguments to the application.

        Player player1 = new Player("Player1", true); // This line creates a new instance of the Player class named 'player1'. The constructor of the Player class takes two parameters: a string for the player's name ("Player1") and a boolean indicating if the player is active (true). The 'player1' object is now initialized with these values.

        Player player2 = new Player("Player2", false); // Similarly, this line creates another instance of the Player class named 'player2'. The constructor is called with "Player2" as the name and false as the active status. This initializes the 'player2' object.

        player1.setPartner(player2); // This method call sets 'player2' as the partner of 'player1'. It assumes that 'player1' has a method 'setPartner' which establishes a relationship or connection between 'player1' and 'player2'.

        player2.setPartner(player1); // This line sets 'player1' as the partner of 'player2', completing the bidirectional relationship. It ensures that both players recognize each other as partners.

        Thread thread1 = new Thread(player1); // This line creates a new Thread instance named 'thread1'. The thread is assigned the 'player1' object as its target. The Thread class is used to run code concurrently; here, it will execute the 'run' method of the 'player1' object, which should be defined in the Player class.

        Thread thread2 = new Thread(player2); // Similarly, this line creates another Thread instance named 'thread2', with 'player2' as its target. It will run the 'run' method of the 'player2' object concurrently.

        thread1.start(); // This starts the execution of 'thread1'. The 'start' method invokes the 'run' method of the 'player1' object in a new thread, allowing it to execute concurrently with other threads.

        thread2.start(); // This starts the execution of 'thread2', running the 'run' method of the 'player2' object concurrently with 'thread1'.

        try { // This begins a try block to handle exceptions that might occur during thread execution. Specifically, it handles 'InterruptedException', which can occur if a thread is interrupted while waiting.

            thread1.join(); // This call makes the current thread (main thread) wait until 'thread1' has finished executing. It ensures that the main thread does not proceed until 'thread1' completes.

            thread2.join(); // Similarly, this call makes the main thread wait until 'thread2' has finished executing. Both threads must complete before the program continues.

        } catch (InterruptedException e) { // This block catches 'InterruptedException' if it occurs during the join operation. This exception indicates that the thread was interrupted while waiting.

            e.getMessage(); // This line retrieves the error message from the exception. Note that this method call does not print or log the message, which might be necessary for debugging. Typically, you would use 'e.printStackTrace()' or 'System.out.println(e.getMessage())' to display the error.

        }

        System.out.println("Communication finished."); // This prints a message to the console indicating that the communication process involving the threads has completed. This statement is executed after both threads have finished their execution.
    }
}
