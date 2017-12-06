package se.contribe.bookstore.backend.api;

import java.math.BigDecimal;

public interface Basket {

    void addBook(Book book);

    boolean removeBook(Book book);

    Book[] getBooks();

    BigDecimal getTotalPrice();
}
