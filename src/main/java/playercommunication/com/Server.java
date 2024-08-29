package playercommunication.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server { // Defines the `Server` class. This class is responsible for accepting connections from clients (players) and facilitating communication between them.

    public static void main(String[] args) throws IOException { // The main method serves as the entry point for the server application. It throws `IOException` to handle potential input/output errors.

        ServerSocket serverSocket = new ServerSocket(12345); // Creates a new `ServerSocket` that listens on port 12345 for incoming connection requests from clients. The port number must be available and not in use by other applications.

        System.out.println("Server started. Waiting for players..."); // Prints a message to the console indicating that the server has started and is waiting for clients to connect.

        Socket player1Socket = serverSocket.accept(); // Waits for a client to connect and accepts the connection request. This returns a `Socket` object representing the connection with Player 1.
        System.out.println("Player 1 connected."); // Prints a message to the console confirming that Player 1 has connected.

        Socket player2Socket = serverSocket.accept(); // Waits for a second client to connect and accepts the connection request. This returns a `Socket` object representing the connection with Player 2.
        System.out.println("Player 2 connected."); // Prints a message to the console confirming that Player 2 has connected.

        BufferedReader in1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream())); // Creates a `BufferedReader` to read text data from Player 1's input stream. It uses an `InputStreamReader` to convert bytes into characters.
        PrintWriter out1 = new PrintWriter(player1Socket.getOutputStream(), true); // Creates a `PrintWriter` to write text data to Player 1's output stream. The `true` argument enables auto-flushing of the output buffer.

        BufferedReader in2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream())); // Creates a `BufferedReader` to read text data from Player 2's input stream, similar to `in1`.
        PrintWriter out2 = new PrintWriter(player2Socket.getOutputStream(), true); // Creates a `PrintWriter` to write text data to Player 2's output stream, similar to `out1`.

        new Thread(() -> { // Starts a new thread to handle communication from Player 1 to Player 2.
            try {
                String message; // Declares a variable to hold incoming messages.
                while ((message = in1.readLine()) != null) { // Continuously reads lines of text from Player 1's input stream. The loop continues until the end of the stream is reached.
                    out2.println(message); // Forwards each received message from Player 1 to Player 2's output stream.
                }
            } catch (IOException e) { // Catches and handles `IOException` if an error occurs during reading or writing data.
                e.printStackTrace(); // Prints the stack trace of the exception to help with debugging.
            }
        }).start(); // Starts the thread for handling Player 1's messages. The `start` method initiates the thread's execution.

        new Thread(() -> { // Starts another new thread to handle communication from Player 2 to Player 1.
            try {
                String message; // Declares a variable to hold incoming messages.
                while ((message = in2.readLine()) != null) { // Continuously reads lines of text from Player 2's input stream. The loop continues until the end of the stream is reached.
                    out1.println(message); // Forwards each received message from Player 2 to Player 1's output stream.
                }
            } catch (IOException e) { // Catches and handles `IOException` if an error occurs during reading or writing data.
                e.getMessage(); // Retrieves and logs the exception's message. Note: Ideally, you should use `e.printStackTrace()` to get a full stack trace for debugging.
            }
        }).start(); // Starts the thread for handling Player 2's messages. The `start` method initiates the thread's execution.
    }
}
