import java.util.List;
import java.util.Objects;

public class SearchCmd extends LibraryCommand {

    /**
     * Creates Search Command
     * @param searchTerm what the user is searching for.
     * @throws IllegalArgumentException if given searchTerm is null.
     * @throws NullPointerException if given searchTerm is null.
     */
    public SearchCmd(String searchTerm) {
        super(CommandType.SEARCH, searchTerm);
    }

    /**
     * The word used that the user is searching for.
     */
    private String searchWord;

    /**
     * Executes the search command, checks if a book title contains a the searchWord as a substring of title.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     * @throws IllegalArgumentException if given data is invalid.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "ERROR: INPUT CANNOT BE NULL");

        boolean searchFound = false;

        List<BookEntry> bookData = data.getBookData();

        for (BookEntry book : bookData) {
            if (book.getTitle().toLowerCase().contains(searchWord.toLowerCase())) {
                searchFound = true;
                System.out.println(book.getTitle());
            }
        }

        if (!searchFound) {
            System.out.println("No hits found for search term: " + searchWord);
        }
    }

    /**
     * Checks whether an input is valid or not, expected to be made of one word and not empty.
     * @param argumentInput argument input expected to be one non-null word.
     * @return boolean, true if argument is not empty and if is only made of one word. Otherwise false.
     * @throws NullPointerException if given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        Objects.requireNonNull(argumentInput, "ERROR: INPUT CANNOT BE NULL");

        if ((isOneWord(argumentInput)) && (!argumentInput.equals(""))) {
            searchWord = argumentInput;
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks if argument is entered is made of one word or more by checking if a space is present.
     * @param argumentInput argument input expected to be one word.
     * @return boolean value true if no space is present. Otherwise false.
     */
    private boolean isOneWord(String argumentInput) {
        for (int i = 0; i < argumentInput.length(); i++) {
            char argumentInputChar = argumentInput.charAt(i);

            if (argumentInputChar == ' ') {
                return false;
            }
        }
        return true;
    }

}
