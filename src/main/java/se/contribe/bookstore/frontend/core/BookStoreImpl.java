package se.contribe.bookstore.frontend.core;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.contribe.bookstore.backend.api.BookList;
import se.contribe.bookstore.frontend.api.BookStore;
import se.contribe.bookstore.frontend.api.BookStoreElement;
import se.contribe.bookstore.frontend.api.BookStoreLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Component
public class BookStoreImpl implements BookStore {
    private static Logger LOGGER = LoggerFactory.getLogger(BookStoreImpl.class);
    private final BookList bookList;
    private final BookStoreLoader bookStoreLoader;

    @Autowired
    public BookStoreImpl(BookList bookList, BookStoreLoader bookStoreLoader) {
        this.bookList = bookList;
        this.bookStoreLoader = bookStoreLoader;
    }

    public void run(String[] args) {
        LOGGER.info("We are running");
        // load initial book list
        // owner can add book
        // customer can buy books
        // customer can look up books


        URL url = null;
        try {
            url = new URL("https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt");
        } catch (MalformedURLException e) {
            LOGGER.info("Malformed hardcoded URL :P", e);
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
            reader.close();
        } catch (IOException e) {
            LOGGER.error("Unable to read initial book store file", e);
        }
        List<BookStoreElement> bookStoreElements = bookStoreLoader.load(reader);
        for (BookStoreElement element : bookStoreElements) {
            bookList.add(element.getBook(), element.getQuantity());
        }
    }
}
