package se.contribe.bookstore.backend.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.contribe.bookstore.backend.api.Book;
import se.contribe.bookstore.backend.api.BookList;
import se.contribe.bookstore.backend.api.Status;

import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class BookListImpl implements BookList {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookListImpl.class);
    private static final Object BOOK_LIST_LOCK = new Object();

    private Map<Book, Integer> bookInventory = new HashMap<>();

    public Book[] list(String searchString) {
        if (isEmpty(searchString)) {
            return bookInventory.keySet().toArray(new Book[0]);
        }
        return bookInventory.keySet().stream()
                .filter(book -> book.getTitle().contains(searchString) || book.getAuthor().contains(searchString))
                .sorted(comparing(Book::getAuthor).thenComparing(Book::getTitle).thenComparing(Book::getPrice))
                .toArray(Book[]::new);
    }

    public boolean add(Book book, int quantity) {
        if (book == null) {
            throw new IllegalArgumentException("book must not be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must not be negative");
        }
        bookInventory.put(book, quantity);
        return true;
    }

    public int[] buy(Book... books) {
        if (books == null) {
            throw new IllegalArgumentException("books must not be null");
        }
        int[] state = new int[books.length];
        for (int i = 0; i < books.length; i++) {
            synchronized (BOOK_LIST_LOCK) {
                if (books[i] == null) {
                    LOGGER.warn("null book at position " + i);
                    state[i] = Status.DOES_NOT_EXIST.getId();
                    continue;
                }
                Integer availableQuantity = this.bookInventory.get(books[i]);
                if (availableQuantity == null) {
                    // book does not exist
                    state[i] = Status.DOES_NOT_EXIST.getId();
                    continue;
                }
                if (availableQuantity == 0) {
                    // book is out of stock
                    state[i] = Status.NOT_IN_STOCK.getId();
                    continue;
                }
                // buy it!
                this.bookInventory.put(books[i], availableQuantity - 1);
                state[i] = Status.OK.getId();
            }
        }
        return state;
    }
}
