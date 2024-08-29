#!/bin/bash

# Compile the project
mvn clean compile

# Start the server
gnome-terminal -- bash -c "java -cp target/classes Server"

# Start Player1
gnome-terminal -- bash -c "java -cp target/classes Player Player1 true localhost 12345"

# Start Player2
gnome-terminal -- bash -c "java -cp target/classes Player Player2 false localhost 12345"
