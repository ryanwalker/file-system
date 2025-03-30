package com.endpoint;

import java.util.Set;
import java.util.TreeSet;

public class Directory implements Comparable<Directory> {

    private final boolean isRoot;
    private final String name;
    private Directory parentDirectory;
    private final Set<Directory> subDirectories = new TreeSet<>();

    // Private constructor, only used for root directory
    private Directory() {
        this.name = "/";
        this.isRoot = true;
        this.parentDirectory = null;
    }

    public Directory(String name, Directory parentDirectory) {
        this.name = name;
        this.parentDirectory = parentDirectory;
        this.isRoot = false;
    }

    public static Directory root() {
        return new Directory();
    }

    public String getName() {
        return name;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public Set<Directory> getSubDirectories() {
        return subDirectories;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public static boolean isValidDirectoryName(String name) {
        return StringUtils.isAlphanumeric(name);
    }

    @Override
    public int compareTo(Directory directory) {
        return this.name.compareTo(directory.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
