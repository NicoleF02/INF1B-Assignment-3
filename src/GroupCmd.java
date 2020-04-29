import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GroupCmd extends LibraryCommand {

    /**
     * Creates a Group Command.
     * @param inputArgument Expected either to be "AUTHOR" or "TITLE"/
     * @throws IllegalArgumentException if given arguments are invalid.
     * @throws NullPointerException if the given argumentInput is null.
     */
    public GroupCmd(String inputArgument) {
        super(CommandType.GROUP, inputArgument);
    }

    /**
     * String user input expected to be either "AUTHOR" or "TITLE".
     */
    private String groupBy;

    /**
     * Constant made up of the alphabet and one non alphabet character.
     */
    private final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0".toCharArray();

    /**
     * Checks whether a given argument is valid or not, either equal to "AUTHOR" or "TITLE".
     * @param argumentInput argument input for this command. Expected either "AUTHOR" or "TITLE".
     * @return boolean value, true if argumentInput is either "AUTHOR" or "TITLE" otherwise false.
     * @throws NullPointerException if given argumentInput is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        super.parseArguments(argumentInput);

        Objects.requireNonNull(argumentInput, "ERROR: INPUT CANNOT BE NULL");

        if (argumentInput.equals("AUTHOR") || argumentInput.equals("TITLE")) {
            groupBy = argumentInput;
            return true;
        } else {
            return false;
        }
    }

    /***
     * Executes the Group command. This sorts data by title or author.
     * @param data book data to be considered for command execution.
     * @throws NullPointerException if given data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "ERROR: INPUT CANNOT BE NULL");

        List<BookEntry> bookData = data.getBookData();

        if (!bookData.isEmpty()) {
            System.out.println("Grouped data by " + groupBy);

            if (groupBy.equals("AUTHOR")) {
                authorPrinting(authorGrouping(bookData), data);

            } else if (groupBy.equals("TITLE")) {
                titlePrinting(titleGrouping(bookData));
            }

        } else {
            System.out.println("The library has no book entries.");
        }
    }

    /**
     * Initialises the HashMap with key using ALPHABET constant and values of empty list.
     * @return HashMap with the key being all characters of the alphabet and one non alphabet character ("0")
     *         and the values being an empty list
     */
    private HashMap<Character, List<String>> initialiseHashMap() {
        HashMap<Character, List<String>> dataBookSorted = new HashMap<Character, List<String>>();

        for (char character : ALPHABET) {
            List<String> values = new ArrayList<>();
            dataBookSorted.put(character, values);
        }

        return dataBookSorted;
    }

    /**
     * Adds all titles to the appropriate key of HashMap if the title begins with same first letter.
     * @param bookData book data from library.
     * @return HashMap of book titles sorted alphabetically and alphabet and one non alphabetical character as key.
     */
    private HashMap<Character, List<String>> titleGrouping(List<BookEntry> bookData) {
        Objects.requireNonNull(bookData, "ERROR: BOOK DATA CANNOT BE NULL");

        HashMap<Character, List<String>> dataBookSorted = initialiseHashMap();

        for (BookEntry book : bookData) {
            List<String> values;
            char firstLetter = book.getTitle().toUpperCase().charAt(0);

            if (Character.isLetter(firstLetter)) {
                values = dataBookSorted.get(firstLetter);
                values.add(book.getTitle());
                dataBookSorted.replace(firstLetter, values);
            } else {
                List<String> temp = dataBookSorted.get(ALPHABET[26]);
                temp.add(book.getTitle());
                dataBookSorted.replace(ALPHABET[26], temp);
            }
        }

        return dataBookSorted;
    }

    /**
     * Prints the title of the books alphabetically with headers.
     * @param dataBookSorted HashMap with book title as values, ALPHABET constant as key
     */
    private void titlePrinting(HashMap<Character, List<String>> dataBookSorted){
        for (char key: dataBookSorted.keySet()) {
            List<String> value = dataBookSorted.get(key);

            Objects.requireNonNull(value, "ERROR CANNOT BE NULL");

            if (key == ALPHABET[26] && !value.isEmpty()) {
                System.out.println("## [0-9]");
                for (String titles: value) {
                    System.out.println("   " + titles);
                }
            } else if (Character.isLetter(key) && !value.isEmpty()) {
                System.out.println("## " + key);
                for (String titles: value) {
                    System.out.println("   " + titles);
                }
            }
        }
    }

    /**
     * Add authors to values of bookDataSorted, only if first letter of author is equal to key. No repeated authors.
     * @param bookData All book data from library
     * @return HashMap dataBookSorted, key is ALPHABET constant and values are authors
     */
    private HashMap<Character, List<String>> authorGrouping(List<BookEntry> bookData){
        HashMap<Character, List<String>> dataBookSorted = initialiseHashMap();

        for (BookEntry book : bookData) {

            String[] authors = book.getAuthors();
            List<String> values;
            for (String author : authors) {
                boolean authorAlreadyThere = false;

                char firstLetter = author.toUpperCase().charAt(0);
                values = dataBookSorted.get(firstLetter);
                for (String value : values) {
                    if (value.equals(author)) {
                        authorAlreadyThere = true;
                    }
                }
                if (!authorAlreadyThere) {
                    values.add(author);
                    dataBookSorted.replace(firstLetter, values);
                }
            }
        }
        return dataBookSorted;
    }

    /**
     * Prints authors as headers and then checks what books each author has written, if the author wrote them then
     * the book title is printed
     * @param dataBookSorted HashMap with alphabet as key and authors as values.
     * @param data Book data that has all book entries
     */
    private void authorPrinting(HashMap<Character, List<String>> dataBookSorted, LibraryData data){
        List<BookEntry> bookData = data.getBookData();

        for (char key : dataBookSorted.keySet()) {
            List<String> value = dataBookSorted.get(key);
            Objects.requireNonNull(value, "ERROR CANNOT BE NULL");

            if (!value.isEmpty()) {
                for (String author : value) {
                    System.out.println("## " + author);
                    for (BookEntry book : bookData) {
                        List allAuthors = Arrays.asList(book.getAuthors());
                        if (allAuthors.contains(author)) {
                            System.out.println("   " + book.getTitle());

                        }
                    }
                }
            }
        }
    }
}






