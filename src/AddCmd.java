import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class AddCmd extends LibraryCommand {

    /**
     * Creates an add command.
     * @param argumentInput argument input is expected to a .csv file
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if the given argumentInput is null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }

    /**
     * Path of the file in add command
     */
    private Path filePath;

    /***
     * Checks whether a given argument is valid or not, true if last four letters ends in ".csv" returns false otherwise
     * @param argumentInput argument input for this command.
     * @return true or false boolean if input is valid/
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "ERROR: No content loaded before parsing.");

        try {
            if (argumentInput.endsWith(".csv")) {
                filePath = Paths.get(argumentInput);
                return true;
            } else {
                System.err.println("Invalid argument for ADD command: " + argumentInput);
                return false;
            }
        }catch(IllegalArgumentException e) {
            System.err.println("ERROR: Given command input is invalid: " + argumentInput);
            return false;
        }
    }

    /***
     * Executes the add command. This adds book entries to the library.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if the given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given argument input must not be null.");

        data.loadData(filePath);
    }

}
