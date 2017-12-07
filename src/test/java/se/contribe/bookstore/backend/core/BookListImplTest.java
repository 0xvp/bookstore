package se.contribe.bookstore.backend.core;

import org.junit.Before;
import org.junit.Test;
import se.contribe.bookstore.backend.api.Book;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BookListImplTest {

    private Book[] books;
    private BookListImpl bookList;
    private Book book;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;



    @Before
    public void setUp() throws Exception {

        books = new Book[5];
        bookList = new BookListImpl();

        book = new Book("Title", "Author", BigDecimal.ZERO);
        book2 = new Book("Eyes on the Highway", "RW", BigDecimal.TEN);
        book3 = new Book("Satellites", "RW", BigDecimal.TEN);
        book4 = new Book("How far I'll go", "Alessia Cara", BigDecimal.TEN);
        book5 = new Book("I'm a super long and very useless title and I'm proud of it", "Tornado", BigDecimal.TEN);

        bookList.add(book, 0);
        bookList.add(book2, 1);
        bookList.add(book3, 1);
        bookList.add(book4, 1);
        bookList.add(book5, 10);

        books[0]=book;
        books[1]=book2;
        books[2]=book3;
        books[3]=book4;
        books[4]=book5;
    }

    @Test
    public void testList() {
        Book[] resultSet = bookList.list(null);

        assertThat(resultSet.length, is(5));
    }

    @Test
    public void testSearchStringList() {
        String searchString = "RW";
        Book[] resultSet = bookList.list(searchString);

        assertThat(resultSet.length, is(2));
    }

    @Test
    public void add() {
        bookList.add(new Book("Bambi", "The Forest", BigDecimal.TEN), 2);

        assertThat(bookList.list(null).length, is(6));
    }

    @Test (expected = IllegalArgumentException.class)
    public void addNull() {
        bookList.add(null, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addNegQuantity() {
        bookList.add(new Book("Bambi", "The Forest", BigDecimal.TEN), -1);
    }

    @Test
    public void buy() {
        int[] states = bookList.buy(books);

        assertThat(states.length, is(5));
        assertThat(states[0], is(1));
        assertThat(states[1], is(0));
    }

    @Test
    public void buyNullBook() {
        books[0]=null;
        int[] states = bookList.buy(books);

        assertThat(states.length, is(5));
        assertThat(states[0], is(2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void buyNull() {
        bookList.buy(null);
    }

}
