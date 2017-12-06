package se.contribe.bookstore.frontend.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.contribe.bookstore.backend.api.Book;
import se.contribe.bookstore.frontend.api.BookStoreElement;
import se.contribe.bookstore.frontend.api.BookStoreLoader;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static se.contribe.bookstore.frontend.core.BigDecimalUtils.parseBigDecimal;

public class BookStoreLoaderImpl implements BookStoreLoader {
    private static Logger LOGGER = LoggerFactory.getLogger(BookStoreLoaderImpl.class);

    public List<BookStoreElement> load(Reader reader) {
        List<BookStoreElement> bookStoreElements = new ArrayList<>();
        try {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));
            for (CSVRecord record : parser) {
                BookStoreElement bookStoreElement = parseRecord(record);
                if (bookStoreElement != null) {
                    bookStoreElements.add(bookStoreElement);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse book store file", e);
        }
        return bookStoreElements;
    }

    private BookStoreElement parseRecord(CSVRecord record) {
        String title = record.get(0);
        String author = record.get(1);
        String priceString = record.get(2);
        String quantityString = record.get(3);
        BigDecimal price;
        try {
            price = parseBigDecimal(priceString);
        } catch (ParseException e) {
            LOGGER.warn("Invalid price in row", e);
            return null;
        }
        int quantity;
        try {
            quantity = parseInt(quantityString);
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid quantity in row", e);
            return null;
        }
        Book book = new Book(title, author, price);
        LOGGER.info("Parsed book from CSV: {}", book);
        return new BookStoreElement(book, quantity);
    }
}
