package playercommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable { // Defines the `Player` class and implements the `Runnable` interface. Implementing `Runnable` allows instances of `Player` to be executed in separate threads, enabling concurrent execution.

    private String name; // Private field to store the player's name. This name identifies the player in communication.
    private Player partner; // Private field to hold a reference to the player's communication partner. This allows two players to exchange messages.
    private int messageCounter = 0; // Private field to count the number of messages sent. This helps in tracking the message sequence.
    private boolean isInitiator; // Private field to indicate if the player is the initiator of the communication. This can be used to determine if the player starts the conversation.
    private Socket socket; // Private field to represent the network socket used for communication. This is used to establish a connection with another player.
    private BufferedReader in; // Private field to read text data from the socket's input stream. This is used to receive messages from the partner.
    private PrintWriter out; // Private field to write text data to the socket's output stream. This is used to send messages to the partner.

    // Constructor for same process communication
    public Player(String name, boolean isInitiator) {
        this.name = name; // Initializes the player's name.
        this.isInitiator = isInitiator; // Sets whether the player is the initiator of the communication.
    }

    // Constructor for separate process communication
    public Player(String name, boolean isInitiator, String host, int port) throws IOException {
        this.name = name; // Initializes the player's name.
        this.isInitiator = isInitiator; // Sets whether the player is the initiator of the communication.
        this.socket = new Socket(host, port); // Establishes a new socket connection to the specified host and port, allowing network communication.
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Initializes `BufferedReader` to read data from the socket's input stream, converting bytes to characters.
        this.out = new PrintWriter(socket.getOutputStream(), true); // Initializes `PrintWriter` to write data to the socket's output stream. The `true` argument enables auto-flushing of the output buffer.
    }

    public void setPartner(Player partner) {
        this.partner = partner; // Sets the partner player for communication. This establishes a direct link for message exchange between the two players.
    }

    public void sendMessage(String message) {
        messageCounter++; // Increments the message counter to keep track of the number of messages sent.
        String fullMessage = message + " [" + messageCounter + "]"; // Creates a formatted message that includes the message content and the current message counter.
        System.out.println(name + " sends: " + fullMessage); // Logs the message being sent to the console for visibility.
        if (partner != null) {
            partner.receiveMessage(fullMessage); // If a partner is set, forwards the message to the partner's `receiveMessage` method for further processing.
        } else {
            out.println(fullMessage); // If no partner is set, sends the message over the network through the socket output stream.
        }
    }

    public void receiveMessage(String message) {
        System.out.println(name + " received: " + message); // Logs the received message to the console for visibility.
        if (messageCounter < 10) {
            sendMessage(message); // If the message counter is less than 10, sends the received message back. This creates a loop where each player echoes the received message.
        }
    }

    public void start() throws IOException {
        if (isInitiator) {
            sendMessage("Hi"); // If the player is the initiator, sends a starting message ("Hi") to begin the communication.
        }
        while (messageCounter < 10) {
            receiveMessage(in.readLine()); // Continuously reads incoming messages from the input stream and processes them until 10 messages have been sent.
        }
        socket.close(); // Closes the socket connection after the communication process is complete.
    }

    @Override
    public void run() {
        if (isInitiator) {
            sendMessage("Hello"); // If the player is the initiator, sends a starting message ("Hello") when the thread starts.
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 4) { // Checks if the number of command-line arguments is exactly 4. If not, prints usage instructions.
            System.out.println("Usage: java Player <name> <isInitiator> <host> <port>");
            return; // Exits the method if the argument count is incorrect.
        }
        String name = args[0]; // Retrieves the player's name from the command-line arguments.
        boolean isInitiator = Boolean.parseBoolean(args[1]); // Parses the second argument as a boolean to determine if the player is the initiator.
        String host = args[2]; // Retrieves the host address from the command-line arguments for network communication.
        int port = Integer.parseInt(args[3]); // Parses the port number from the command-line arguments for network communication.

        Player player = new Player(name, isInitiator, host, port); // Creates a new `Player` instance using the command-line arguments.
        player.start(); // Starts the player, initiating communication and processing messages.
    }
}
