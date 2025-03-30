package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public class FindCommand implements FileSystemCommand {

    private final Directory rootDirectory;

    public FindCommand(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        // Validate the arguments
        if (args.length != 1 || !(args[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        // Parse the arguments
        String path = ((String) args[0]).trim();
        String[] pathParts = path.split("/");
        for (String pathPart : pathParts) {
            if (!Directory.isValidDirectoryName(pathPart)) {
                return new FileSystemCommandResult("Directory names must be alphanumeric");
            }
        }

        if (path.equals("/")) {
            return new FileSystemCommandResult(rootDirectory);
        } else if (pathParts.length == 1) {
            for (Directory subDir : dir.getSubDirectories()) {
                if (subDir.getName().equals(path)) {
                    return new FileSystemCommandResult(subDir);
                }
            }
        } else {
            String nextSubDir = pathParts[0];
            for (Directory subDir : dir.getSubDirectories()) {
                if (subDir.getName().equals(nextSubDir)) {
                    return execute(subDir, path.substring(nextSubDir.length() + 1));
                }
            }
        }

        // We didn't find it
        return FileSystemCommandResult.empty();
    }
}
