package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;
import com.endpoint.commands.factory.CommandFactory;

import static com.endpoint.commands.factory.CommandFactory.CREATE_COMMAND_NAME;
import static com.endpoint.commands.factory.CommandFactory.FIND_COMMAND_NAME;

public class MoveCommand implements FileSystemCommand {

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        // validate and parse the argument
        if (args.length != 2 || !(args[0] instanceof String sourcePath) || !(args[1] instanceof String targetPath)) {
            return new FileSystemCommandResult("MOVE requires exactly 2 argument");
        }

        // validate the source directory names
        String[] sourcePathParts = sourcePath.split("/");
        for (String sourcePathPart : sourcePathParts) {
            if (!Directory.isValidDirectoryName(sourcePathPart)) {
                return new FileSystemCommandResult("Directory names must be alphanumeric");
            }
        }

        // validate the target directory names
        String[] targetPathParts = targetPath.split("/");
        if (!targetPath.equals("/")) { // If the target path is "/", I picked that to be root, even though it wasn't a requirement
            for (String targetPathPart : targetPathParts) {
                if (!Directory.isValidDirectoryName(targetPathPart)) {
                    return new FileSystemCommandResult("Directory names must be alphanumeric");
                }
            }
        }

        // Find the source directory
        FileSystemCommandResult sourceDirectoryResult = CommandFactory.getCommand(FIND_COMMAND_NAME).execute(dir, sourcePath);
        if (sourceDirectoryResult.getDirectory().isEmpty()) {
            return new FileSystemCommandResult("Directory not found: " + sourcePath);
        }

        // Create or find the target directory
        FileSystemCommandResult targetCreateResult = CommandFactory.getCommand(CREATE_COMMAND_NAME).execute(dir, targetPath);
        if (targetCreateResult.getDirectory().isEmpty()) {
            return new FileSystemCommandResult("Failed to create target directory: " + targetPath);
        }

        // Move the source directory to UNDERNEATH the target directory by changing pointers
        Directory sourceDirectory = sourceDirectoryResult.getDirectory().get();
        Directory targetDirectory = targetCreateResult.getDirectory().get();

        sourceDirectory.getParentDirectory().getSubDirectories().remove(sourceDirectory);
        sourceDirectory.setParentDirectory(targetDirectory);
        targetDirectory.getSubDirectories().add(sourceDirectory);

        return FileSystemCommandResult.empty();
    }
}
