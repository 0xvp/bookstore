package se.contribe.bookstore.core;

import org.springframework.stereotype.Component;
import se.contribe.bookstore.api.Book;
import se.contribe.bookstore.api.BookList;

@Component
public class BookListImpl implements BookList {
    public Book[] list(String searchString) {
        return new Book[0];
    }

    public boolean add(Book book, int quantity) {
        return false;
    }

    public int[] buy(Book... books) {
        return new int[0];
    }
}
