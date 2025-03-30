package com.endpoint;

import java.util.Optional;

public class FileSystemCommandResult {

    private final Directory directory;
    private final String error;

    private static final FileSystemCommandResult EMPTY_RESULT = new FileSystemCommandResult();

    public FileSystemCommandResult(Directory directory, String error) {
        this.directory = directory;
        this.error = error;
    }

    public FileSystemCommandResult(String error) {
        this(null, error);
    }

    public FileSystemCommandResult(Directory subDir) {
        this(subDir, null);
    }

    public FileSystemCommandResult() {
        this(null, null);
    }

    public static FileSystemCommandResult empty() {
        return EMPTY_RESULT;
    }

    public Optional<Directory> getDirectory() {
        return directory == null ? Optional.empty() : Optional.of(directory);
    }

    public Optional<String> getError() {
        return error == null ? Optional.empty() : Optional.of(error);
    }
}
