import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;


    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     */
    public List<BookEntry> parseFileContent() {
        List<BookEntry> bookEntries = new ArrayList<>();

        if (contentLoaded()) {
            for (int i = 1; i < fileContent.size(); i++){
                bookEntries.add(splitContents(fileContent.get(i)));
            }

        } else{
            System.err.println("ERROR: No content loaded before parsing.");
            return bookEntries;

        }
        return bookEntries;
    }

    /**
     * Splits the contents of an entry from a csv file into title, author, rating, ISBN and pages.
     * @param entry a line from the csv file read in during the Add cmd
     * @return BookEntry: All data for a book fully split up into title, author, rating, ISBN and pages.
     */
    public static BookEntry splitContents(String entry) {
        Objects.requireNonNull(entry, "Must not be null");

        String[] entryArray = entry.split(",");
        String title = entryArray[0];
        String[] authors = entryArray[1].split("-");
        float rating = Float.parseFloat(entryArray[2]);
        String ISBN = entryArray[3];
        int pages = Integer.parseInt(entryArray[4]);

        return new BookEntry(title, authors, rating, ISBN, pages);
    }

}
