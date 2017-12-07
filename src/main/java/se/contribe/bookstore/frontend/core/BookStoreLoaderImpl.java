package se.contribe.bookstore.frontend.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.contribe.bookstore.backend.api.Book;
import se.contribe.bookstore.frontend.api.BookStoreElement;
import se.contribe.bookstore.frontend.api.BookStoreLoader;
import se.contribe.bookstore.frontend.api.ParseBookException;

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
                try {
                    BookStoreElement bookStoreElement = parseRecord(record);
                    bookStoreElements.add(bookStoreElement);
                } catch (ParseBookException e) {
                    LOGGER.error("Unable to parse line in book store file", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to parse book store file", e);
        }
        return bookStoreElements;
    }

    private BookStoreElement parseRecord(CSVRecord record) {
        String title;
        String author;
        String priceString;
        String quantityString;
        BigDecimal price;
        try {
            title = record.get(0);
            author = record.get(1);
            priceString = record.get(2);
            quantityString = record.get(3);
            price = parseBigDecimal(priceString);
        } catch (ParseException e) {
            LOGGER.warn("Invalid price in row", e);
            throw new ParseBookException("Invalid price", e);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.warn("Invalid book data", e);
            throw new ParseBookException("Missing column, four columns are expected", e);
        }
        int quantity;
        try {
            quantity = parseInt(quantityString);
        } catch (NumberFormatException e) {
            LOGGER.warn("Invalid quantity in row", e);
            throw new ParseBookException("Invalid quantity", e);
        }
        Book book = new Book(title, author, price);
        LOGGER.info("Parsed book from CSV: {}", book);
        return new BookStoreElement(book, quantity);
    }
}
