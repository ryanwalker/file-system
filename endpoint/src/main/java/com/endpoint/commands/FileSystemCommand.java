package com.endpoint.commands;

import com.endpoint.Directory;
import com.endpoint.FileSystemCommandResult;

public interface FileSystemCommand {

    FileSystemCommandResult execute(Directory dir, Object... args);

}
