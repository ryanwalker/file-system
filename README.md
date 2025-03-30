# File System Coding Fun

## Running the code
You need Java 21 installed to run this code.

Then run the following commands to start the application:


```shell
./gradlew clean build # this will download gradle and build the project

java -jar endpoint/build/libs/endpoint.jar
```

## Description
* `FileSystemDriver.java` is the entry point of the application. It polls for user input until the user types `exit`.
* Input is parsed and the appropriate `FileSystemCommand` object is retrieved and executed with any command line args.
* If the command encounters an error it will be printed to the console.
* The `FileSystem` class holds a reference to the root `Directory` and has an `executeCommand()` method.
* Each `FileSystemCommand` has an `execute()` method that receives any command line arguments and operates on the directory passed in.
* The Directory structure is a Tree structure modeled by the `Directory.java` class holding a reference to a parent directory and a list of sub directories.
* Recursion is used to traverse the directory structure.
