package com.endpoint;

import com.endpoint.commands.FileSystemCommand;

public class FileSystem {

    private final Directory root;

    public FileSystem() {
        this.root = Directory.root();;
    }

    public void executeCommand(FileSystemCommand command, Object... args) {
        FileSystemCommandResult result = command.execute(root, args);
        if (result.getError().isPresent()) {
            System.out.println(result.getError().get());
        }
    }

    public Directory getRoot() {
        return root;
    }
}
