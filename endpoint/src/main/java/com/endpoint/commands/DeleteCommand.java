package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public class DeleteCommand implements FileSystemCommand {

    private static final String NON_EXISTENT_DIRECTORY_MESSAGE_TEMPLATE = "Cannot delete %s - %s does not exist";

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        // validate the arguments
        if (args.length != 1 && !(args[0] instanceof String)) {
            return new FileSystemCommandResult("DELETE requires exactly 1 argument");
        }

        // parse the arguments
        String path = ((String) args[0]).trim();
        String[] pathParts = path.split("/");
        for (String pathPart : pathParts) {
            if (!Directory.isValidDirectoryName(pathPart)) {
                return new FileSystemCommandResult("Directory names must be alphanumeric");
            }
        }

        String nextSubDir = pathParts[0];

        FileSystemCommandResult result = FileSystemCommandResult.empty();

        if (pathParts.length == 1) {
            // Means we've reached the end of the path
            Directory dirToDelete = null;
            for (Directory subDirectory : dir.getSubDirectories()) {
                if (subDirectory.getName().equals(nextSubDir)) {
                    dirToDelete = subDirectory;
                    break;
                }
            }
            if (dirToDelete == null) {
                return new FileSystemCommandResult(
                        NON_EXISTENT_DIRECTORY_MESSAGE_TEMPLATE.formatted(path, path));
            } else {
                dir.getSubDirectories().remove(dirToDelete);
                return FileSystemCommandResult.empty();
            }
        } else {
            // recurse
            boolean found = false;
            for (Directory subDirectory : dir.getSubDirectories()) {
                if (subDirectory.getName().equals(nextSubDir)) {
                    result = execute(subDirectory, path.substring(nextSubDir.length() + 1));
                    found = true;
                }
            }
            if (!found) {
                result = new FileSystemCommandResult(nextSubDir);
            }

        }
        if (result.getError().isPresent() && dir.isRoot()) {
            result = new FileSystemCommandResult(
                    NON_EXISTENT_DIRECTORY_MESSAGE_TEMPLATE.formatted(path, result.getError().get()));
        }
        return result;
    }
}
