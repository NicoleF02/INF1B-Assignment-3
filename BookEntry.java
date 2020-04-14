import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {

    /** String title of the book*/
    private final String title;

    /** String Array authors of the book*/
    private final String[] authors;

    /** Float rating of the book*/
    private final float rating;

    /** String ISBN number of the book*/
    private final String ISBN;

    /** Integer Number of pages within a book*/
    private final int pages;

    /**
     * Intialises all fields.
     * Bookdata for a single book entry, checks if it is valid, if so then returns all info.
     * @param title String title of the book.
     * @param authors String Array authors of the book.
     * @param rating Float rating of the book.
     * @param ISBN String ISBN number of the book.
     * @param pages Integer Number of pages within a book.
     * @throws NullPointerException if given title, authors or ISBN is null.
     * @throws IllegalArgumentException if rating or pages are invalid.
     */
    public BookEntry (String title, String[] authors, float rating, String ISBN, int pages) {
        validBookEntry(title, authors, rating, ISBN, pages);

        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /**
     * @return String title of the book.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return String Array, author(s) of the book.
     */
    public String[] getAuthors() {
        return this.authors;
    }

    /**
     * @return Float rating of the book
     */
    public float getRating() {
        return this.rating;
    }

    /**
     * @return String ISBN number of the book
     */
    public String getISBN() {
        return this.ISBN;
    }

    /**
     * @return Integer Number of pages within a book
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * All exceptions that can be thrown for BookEntry
     * @param title String title of the book
     * @param authors String Array authors of the book
     * @param rating Float rating of the book
     * @param ISBN String ISBN number of the book
     * @param pages Integer Number of pages within a book
     * @throws NullPointerException if given title, authors or ISBN is null.
     * @throws IllegalArgumentException if rating or pages are invalid.
     */
    public static void validBookEntry (String title, String[] authors, float rating, String ISBN, int pages) {
        if (title == null){
            throw new NullPointerException("Title cannot be null");
        } else if (authors == null){
            throw new NullPointerException("Authors cannot be null");
        } else if (rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        } else if (ISBN == null){
            throw new NullPointerException("ISBN cannot be null");
        } else if (pages < 0){
            throw new IllegalArgumentException("You cannot have negative pages");
        }
    }

    /**
     * A String representation of the book data
     * @return A string of with all book data formatted
     */
    @Override
    public String toString(){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(title);
        strBuilder.append("\nby ");

        for (int i = 0; i < authors.length; i++) {
            String author = authors[i];
            strBuilder.append(author);

            //Checks if is not the last author, is so then it will not print a comma to separate them
            if (author != authors[authors.length - 1]) {
                strBuilder.append(", ");
            }
        }

        strBuilder.append("\n");
        strBuilder.append("Rating: ").append(String.format("%.2f", rating)).append("\n");
        strBuilder.append("ISBN: ").append(ISBN).append("\n");
        strBuilder.append(pages).append(" pages");

        return strBuilder.toString();
    }

    /**
     *Indicates whether some other object is "equal to" this one.
     * @param input the book entry with which to compare.
     * @return boolean, true if all aspects of object are the same as the other, e.g. title is same,
     *         authors are same, etc.
     */
    @Override
    public boolean equals(Object input) {
        if (this == input) {
            return true;
        }

        if (input == null || getClass() != input.getClass()) {
            return false;
        }

        BookEntry bookEntry = (BookEntry) input;

        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                title.equals(bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                ISBN.equals(bookEntry.ISBN);
    }

    /**
     * Returns a hash code value for the object.
     * @return result, the hashcode value for the object
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }
}
