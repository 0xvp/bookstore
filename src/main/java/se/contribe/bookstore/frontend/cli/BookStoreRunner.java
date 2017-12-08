package se.contribe.bookstore.frontend.cli;

import org.apache.commons.io.input.BOMInputStream;
import se.contribe.bookstore.backend.api.Basket;
import se.contribe.bookstore.backend.api.Book;
import se.contribe.bookstore.backend.api.BookList;
import se.contribe.bookstore.backend.api.Status;
import se.contribe.bookstore.backend.core.BasketImpl;
import se.contribe.bookstore.backend.core.BookListImpl;
import se.contribe.bookstore.frontend.api.BookStoreElement;
import se.contribe.bookstore.frontend.api.BookStoreLoader;
import se.contribe.bookstore.frontend.core.BookStoreLoaderImpl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.trim;


/**
 * CLI for interacting with the BookStore for testing purposes
 */
public class BookStoreRunner {
    public static void main(String[] args) throws Exception {

        BookList bookList = new BookListImpl();
        Basket basket = new BasketImpl();

        //We still perform an initial load in this test CLI to provide data without needing the url command
        // To provide full offline usage the inputFile was previously downloaded
        URL url = BookStoreRunner.class.getResource("/bookstoredata.csv");
        loadInitialBookList(bookList, url);
        printHelp();

        Book[] lastBookListResult = null;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String input = scanner.next();

            //quitting the Store and terminating the session
            if (input.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            }

            //accessing the list function to retrieve the books stored in the book store
            if (input.equals("list")) {
                String searchString = trim(scanner.nextLine());
                Book[] books = bookList.list(searchString);
                lastBookListResult = books;
                printBooks(books);
            }

            //loading data from given url
            if (input.equals("url")) {
                String searchString = trim(scanner.nextLine());

                if (!(searchString==null || searchString.isEmpty())) {
                    try {
                        loadInitialBookList(bookList, new URL(searchString));
                    }
                    catch (MalformedURLException e){
                        System.out.println("no valid URL provided");
                    }
                }

            }

            //adds a new book to the book store inventory. this only persists during the session
            if (input.equals("add")) {
                String inputLine = trim(scanner.nextLine());
                List<BookStoreElement> bookStoreElements = new BookStoreLoaderImpl().load(new StringReader(inputLine));
                if (!bookStoreElements.isEmpty()) {
                    System.out.println("Added Book: ");
                    System.out.println(format("| %-25s | %-25s | %-10s | %-10s |", "Title", "Author", "Price", "Quantity"));
                    for (BookStoreElement element : bookStoreElements) {
                        bookList.add(element.getBook(), element.getQuantity());
                        System.out.println(format("| %-25s | %-25s | %-10s | %-10s |",
                                element.getBook().getTitle(), element.getBook().getAuthor(),
                                element.getBook().getPrice(), element.getQuantity()));
                    }
                }
                else {
                    // a later expansion could get the text from the ParseBookException
                    System.out.println("Unable to parse book");
                }
            }

            //Functions handling the basket
            if (input.equals("basket")) {
                String command = scanner.next();

                if (command.equals("add")) {
                    int id = parseInt(scanner.next());
                    if (lastBookListResult==null){
                        System.out.println("Please use the list command to receive a list of available books");
                    }
                    else {
                        Book book = lastBookListResult[id];
                        basket.addBook(book);
                    }
                }

                if (command.equals("list")) {
                    Book[] books = basket.getBooks();
                    printBooks(books);
                }

                if (command.equals("remove")) {
                    int id = parseInt(scanner.next());
                    basket.removeBook(basket.getBooks()[id]); // ugly
                }

                if (command.equals("total")) {
                    BigDecimal total = basket.getTotalPrice();
                    System.out.println("Total price of your basket: " + total);
                }

                if (command.equals("buy")) {
                    Book[] books = basket.getBooks();
                    int[] states = bookList.buy(books);
                    BigDecimal total = BigDecimal.ZERO;
                    System.out.println(format("| %5s | %-25s | %-25s | %-10s | %-25s |", "ID", "Title", "Author", "Price", "Status"));
                    System.out.println("+-------+---------------------------+---------------------------+------------+---------------------------+");
                    for (int i= 0; i< states.length; i++) {
                        if (states[i] == Status.OK.getId()) {
                            total = total.add(books[i].getPrice());
                            printBookState(books[i], "Available", i);
                        }
                        else if (states[i] == Status.NOT_IN_STOCK.getId()) {
                            printBookState(books[i], "Not in stock", i);
                        }
                        else if (states[i] == Status.DOES_NOT_EXIST.getId()) {
                            printBookState(books[i], "Does not exist", i);
                        }
                        else {
                            throw new IllegalStateException("Unknown Status value");
                        }
                        basket.removeBook(books[i]);
                    }
                    System.out.println("Total price of your shopping: " + total);

                }
            }
        }
    }

    private static void loadInitialBookList(BookList bookList, URL url) throws IOException {
        BookStoreLoader loader = new BookStoreLoaderImpl();

        Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
        List<BookStoreElement> bookStoreElements = loader.load(reader);
        for (BookStoreElement element : bookStoreElements) {
            bookList.add(element.getBook(), element.getQuantity());
        }
    }

    /**
     * Introduction to the book store
     * Since this is only a CLI for testing purpose methods like create are accessible for everyone
     */
    private static void printHelp() {
        System.out.println("Welcome to the BookStore!");
        System.out.println("Please choose one of the following commands:");
        System.out.println("    list <string>      - list all book in the book store, optionally filtered");
        System.out.println("    url <string>       - url to load additional data from given url");
        System.out.println("    add <book csv>     - add a new book to the book store - format like in CSV file");
        System.out.println("    basket add <id>    - add a book to the basket");
        System.out.println("    basket remove <id> - remove a book from the basket");
        System.out.println("    basket list        - list all books in the basket");
        System.out.println("    basket total       - get the current total price of the basket");
        System.out.println("    basket buy         - buy the contest of the basket");
        System.out.println("    quit               - quit the book store");
        System.out.println("");
    }

    private static void printBooks(Book[] books) {
        System.out.println(format("| %5s | %-25s | %-25s | %-10s |", "ID", "Title", "Author", "Price"));
        System.out.println("+-------+---------------------------+---------------------------+------------+");
        for (int i = 0; i < books.length; i++) {
            System.out.println(format("| %5s | %-25s | %-25s | %-10s |", i, books[i].getTitle(), books[i].getAuthor(), books[i].getPrice()));
        }
    }

    private static void printBookState(Book book, String state, int id) {
        System.out.println(format("| %5s | %-25s | %-25s | %-10s | %-25s |", id, book.getTitle(), book.getAuthor(), book.getPrice(), state));

    }
}
