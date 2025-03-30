package com.endpoint.commands.factory;

import com.endpoint.Directory;
import com.endpoint.commands.CreateCommand;
import com.endpoint.commands.DeleteCommand;
import com.endpoint.commands.ExitCommand;
import com.endpoint.commands.FileSystemCommand;
import com.endpoint.commands.FindCommand;
import com.endpoint.commands.ListCommand;
import com.endpoint.commands.MoveCommand;
import com.endpoint.commands.UnknownCommand;

public class CommandFactory {

    public static final String FIND_COMMAND_NAME = "FIND";
    public static final String CREATE_COMMAND_NAME = "CREATE";

    private static final FileSystemCommand EXIT_COMMAND = new ExitCommand();
    private static final FileSystemCommand DELETE_COMMAND = new DeleteCommand();
    private static final FileSystemCommand MOVE_COMMAND = new MoveCommand();
    private static final FileSystemCommand LIST_COMMAND = new ListCommand();
    private static final FileSystemCommand UNKNOWN_COMMAND = new UnknownCommand();
    private static FileSystemCommand FIND_COMMAND;
    private static FileSystemCommand CREATE_COMMAND;

    private static boolean initialized = false;

    public CommandFactory(Directory rootDirectory) {
        FIND_COMMAND = new FindCommand(rootDirectory);
        CREATE_COMMAND = new CreateCommand(rootDirectory);
    }

    public static void init(Directory rootDirectory) {
        FIND_COMMAND = new FindCommand(rootDirectory);
        CREATE_COMMAND = new CreateCommand(rootDirectory);
        initialized = true;
    }

    public static FileSystemCommand getCommand(String command) {
        if (!initialized) {
            throw new IllegalStateException("CommandFactory not initialized");
        }

        // I included mkdir, rm, mv, ls just for my convenience while testing
        return switch (command.toUpperCase()) {
            case "EXIT" -> EXIT_COMMAND;
            case "CREATE", "MKDIR" -> CREATE_COMMAND;
            case "DELETE", "RM" -> DELETE_COMMAND;
            case "MOVE", "MV" -> MOVE_COMMAND;
            case "LIST", "LS" -> LIST_COMMAND;
            case "FIND" -> FIND_COMMAND;
            default -> UNKNOWN_COMMAND;
        };
    }


}
