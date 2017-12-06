package se.contribe.bookstore.backend.core;

import org.springframework.stereotype.Component;
import se.contribe.bookstore.backend.api.Basket;
import se.contribe.bookstore.backend.api.Book;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Component
public class BasketImpl implements Basket {
    private List<Book> booksInBasket = new LinkedList<>();

    @Override
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }
        booksInBasket.add(book);
    }

    @Override
    public boolean removeBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }
        return booksInBasket.remove(book);
    }

    @Override
    public Book[] getBooks() {
        return booksInBasket.toArray(new Book[0]);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return booksInBasket.stream().map(Book::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
