package com.endpoint;

import com.endpoint.commands.ExitCommand;
import com.endpoint.commands.FileSystemCommand;
import com.endpoint.commands.UnknownCommand;
import com.endpoint.commands.factory.CommandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileSystemDriver {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Set up the file system
        FileSystem fileSystem = new FileSystem();
        CommandFactory.init(fileSystem.getRoot());

        while (true) {
            String input = scanner.nextLine(); // Read user input

            String[] commandParts = input.split("\\s+");

            FileSystemCommand command = CommandFactory.getCommand(commandParts[0]);

            if (command instanceof UnknownCommand) {
                System.out.println("Unknown command: " + commandParts[0]);
            } else if (command instanceof ExitCommand) {
                System.out.println("Exiting...");
                break;
            } else {
                List<Object> commandLinArguments = new ArrayList<>();
                for (int i = 1; i < commandParts.length; i++) {
                    commandLinArguments.add(commandParts[i]);
                }
                fileSystem.executeCommand(command, commandLinArguments.toArray());
            }
        }

        scanner.close();
    }
}
