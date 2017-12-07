package se.contribe.bookstore.backend.api;

import java.math.BigDecimal;

public interface Basket {


    /**
     * Adds a book the the basket
     *
     * @param book new book in basket
     */
    void addBook(Book book);


    /**
     * Removes book from the basket
     *
     * @param book book to be removed from basket
     * @return true when book could be removed
     */
    boolean removeBook(Book book);

    /**
     * Get all books in the basket
     *
     * @return array of current books in the basket
     */
    Book[] getBooks();


    /**
     * Computes the current value of the basket
     *
     * @return total price of books in the basket
     */
    BigDecimal getTotalPrice();
}
