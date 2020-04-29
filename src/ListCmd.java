import java.util.List;
import java.util.Objects;

public class ListCmd extends LibraryCommand {

    /**
     * Creates List Command.
     * @param argumentInput argumentInput expected to be either "long" or "short" or blank.
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * User input of either "long" or "short" or blank.
     */
    private String printSL;

    /**
     * Executes the List Command, if long then all information about the books are printed, if short or blank
     * then only title of books are printed.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     * @throws IllegalArgumentException if given data is invalid.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "ERROR: INPUT CANNOT BE NULL");

        List<BookEntry> bookData = data.getBookData();

        if (bookData.isEmpty()) {
            System.err.println("The library has no book entries.");

        }else {
            System.out.println(bookData.size() + " books in library:");

            if (printSL.equals("long")) {
                printLong(bookData);
            }else if ((printSL.equals("short")) || (printSL.isBlank())) {
                printShort(bookData);
            }else {
            System.err.println("ERROR: Invalid Command.");
            }
        }
    }

    /**
     * Checks whether a given argument is valid or not either "long" or "short" or "".
     * @param argumentInput argument input for this command. Expected to be either "long" or "short" or "".
     * @return True if either "long" or "short" or "". Otherwise false.
     * @throws NullPointerException if given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);

        if (argumentInput.equals("long") || argumentInput.equals("short") || argumentInput.equals("")) {
            printSL = argumentInput;
            return true;
        } else {
                System.err.println("ERROR: ILLEGAL INPUT");
            return false;
        }
    }

    /**
     * Print all information in the format specified for BookEntries toString method.
     * @param bookData book entries in library.
     */
    private void printLong(List<BookEntry> bookData) {
        for (BookEntry book : bookData) {
            System.out.println(book.toString() + "\n");
        }
    }

    /**
     * Prints each books title.
     * @param bookData book entries in library.
     */
    private void printShort(List<BookEntry> bookData) {
        for (BookEntry book : bookData) {
            System.out.println(book.getTitle());
        }
    }

}
