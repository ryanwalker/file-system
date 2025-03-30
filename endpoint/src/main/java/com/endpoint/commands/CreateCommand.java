package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public class CreateCommand implements FileSystemCommand {

    private final Directory rootDirectory;

    public CreateCommand(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        // validate the arguments
        if (args.length != 1 && !(args[0] instanceof String)) {
            return new FileSystemCommandResult("CREATE requires exactly 1 argument");
        }

        // parse the arguments
        String dirToCreate = ((String) args[0]).trim();
        String[] pathParts = dirToCreate.split("/");

        for (String pathPart : pathParts) {
            if (!Directory.isValidDirectoryName(pathPart)) {
                return new FileSystemCommandResult("Directory names must be alphanumeric");
            }
        }

        if (dirToCreate.equals("/")) {
            return new FileSystemCommandResult(rootDirectory);
        } else
        if (pathParts.length == 1) {
            // Means we've reached the end of the path
            // If dir exists, return it, otherwise create it
            for (Directory subDirectory : dir.getSubDirectories()) {
                if (subDirectory.getName().equals(dirToCreate)) {
                    return new FileSystemCommandResult(subDirectory);
                }
            }
            Directory newDirectory = new Directory(dirToCreate, dir);
            dir.getSubDirectories().add(newDirectory);
            return new FileSystemCommandResult(newDirectory);
        } else {
            // Means we'll need to recurse down more
            // Check if the subdir exists
            String nextDir = pathParts[0];
            String subPath = dirToCreate.substring(nextDir.length() + 1);
            for (Directory subDirectory : dir.getSubDirectories()) {
                if (subDirectory.getName().equals(nextDir)) {
                    // Recurse down
                    return execute(subDirectory, subPath);
                }
            }
            // If we got  here, the subdir doesn't exist, so create it
            Directory newDirectory = new Directory(nextDir, dir);
            dir.getSubDirectories().add(newDirectory);
            return execute(newDirectory, subPath);
        }
    }
}
