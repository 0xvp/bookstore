package se.contribe.bookstore.backend.api;


/**
 * Handels the actions towards the bookstore
 */
public interface BookList {


    /**
     * Returns a list of the books in the store
     *
     * @param searchString - if null or empty return all books in the store, else return search result
     * @return array of books matching the searchString
     */
    Book[] list(String searchString);


    /**
     * Adds a book to the book store. Since no upload is intended, this is only persistent within the session
     *
     * @param book new book
     * @param quantity quantity
     * @return true if book was added
     */
    boolean add(Book book, int quantity);


    /**
     * Buys books from the book store and provides information about the availability of books
     *
     * @param books books to buy
     * @return array with Status codes providing information about existence and in stock
     */
    int[] buy(Book... books);

}
