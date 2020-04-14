import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class RemoveCmd extends LibraryCommand {

    /**
     * Creates Remove command.
     * @param removeTerm if author or title is being removed then what is being removed.
     * @throws NullPointerException if given removeTerm is null.
     * @throws IllegalArgumentException if given removeTerm is invalid.
     */
    public RemoveCmd(String removeTerm) {
        super(CommandType.REMOVE, removeTerm);
    }

    /**
     * The user input for what is being removed, e.g. the book title or the author.
     */
    private String removedArgument;

    /**
     * AUTHOR or TITLE, specifies if author or title is being removed.
     */
    private String removerType;

    /**
     * Checks whether argumentInput is valid or not, first word must be either "AUTHOR" or "TITLE" and second
     * must be a single word.
     * @param argumentInput argument input for this command.
     * @return true or false, if given argument is valid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);
        Objects.requireNonNull(argumentInput,"ERROR: CANNOT BE NULL");

        //Separates the user input into if its AUTHOR or TITLE and then the thing wanting removed
        String userInput[] = argumentInput.split(" ");
        removerType =  userInput[0];

        // The length of userInput must be greater than one for it not to be null or empty
        if (((removerType.equals("AUTHOR")) || (removerType.equals("TITLE"))) && (userInput.length > 1)) {

        /*This gets rid of the AUTHOR or TITLE in the input, leaving what we are searching for
        We use length + 1 as it accounts for the spaces
         */
        removedArgument = argumentInput.substring(removerType.length()+1);

        return true;

    } else {
        return false;
    }
    }

    /**
     * Executes the remove command, which removes either an author or title.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     * @throws IllegalArgumentException if given data is invalid.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "ERROR: CANNOT BE NULL");

        List<BookEntry> bookData = data.getBookData();
        Iterator<BookEntry> bookIter = bookData.iterator();

        if (removerType.equals("AUTHOR")) {
            removeAuthor(bookData, bookIter);
        } else if (removerType.equals("TITLE")) {
            removeTitle(bookData, bookIter);
        }
    }

    /**
     * Removes any books by the author inputted.
     * @param bookData book entries in library.
     * @param bookIter used within iterator.
     */
    private void removeAuthor(List<BookEntry> bookData, Iterator<BookEntry> bookIter){
        int booksRemoved = 0;

        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();
            List allAuthors = Arrays.asList(book.getAuthors());

            if (allAuthors.contains(removedArgument)) {
                booksRemoved += 1;
                bookIter.remove();
            }
        }

        System.out.println(booksRemoved + " books removed for author: " + removedArgument);
    }

    /**
     * Removes any books by the title given.
     * @param bookData book entries in library.
     * @param bookIter used within iterator.
     */
    private void removeTitle(List<BookEntry> bookData, Iterator<BookEntry> bookIter){
        int booksRemoved = 0;

        while (bookIter.hasNext()) {
            BookEntry book = bookIter.next();

            if (removedArgument.equals(book.getTitle())) {
                System.out.println( removedArgument + ": removed successfully.");
                booksRemoved += 1;
                bookIter.remove();
                break;
            }
        }

        if (booksRemoved == 0) {
            System.out.println(removedArgument + ": not found.");
        }
    }

}
