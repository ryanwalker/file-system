package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public class ExitCommand implements FileSystemCommand {

    @Override
    public FileSystemCommandResult execute(Directory dir, Object... args) {
        return FileSystemCommandResult.empty();
    }
}
