package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public class ListCommand implements FileSystemCommand {

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        // Validate the arguments
        if (args.length > 1 || (args.length == 1 && !(args[0] instanceof Integer))) {
            // This would be a bug so throwing an exception instead of returning an error
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        // Parse the arguments
        int depth = 0;
        if (args.length == 1 && args[0] instanceof Integer)   {
            depth = (int) args[0];
        }

        // Print indentation based on depth
        for (int i = 1; i < depth; i++) {
            System.out.print("  ");
        }
        // Print the current directory if it is not the root
        if (depth > 0) {
            System.out.println(dir.getName());
        }

        // Print sub directories
        for (Directory subDir : dir.getSubDirectories()) {
            execute(subDir, depth + 1);
        }
        return FileSystemCommandResult.empty();
    }
}
