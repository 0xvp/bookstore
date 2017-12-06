package se.contribe.bookstore.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.contribe.bookstore.BookStoreApp;
import se.contribe.bookstore.api.BookList;
import se.contribe.bookstore.api.BookStore;

@Component
public class BookStoreImpl implements BookStore {
    private static Logger LOGGER = LoggerFactory.getLogger(BookStoreApp.class);
    private final BookList bookList;

    @Autowired
    public BookStoreImpl(BookList bookList) {
        this.bookList = bookList;
    }

    public void run(String[] args) {
        LOGGER.info("We are running");
    }
}
