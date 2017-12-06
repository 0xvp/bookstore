package se.contribe.bookstore.backend.core;

import org.junit.Before;
import org.junit.Test;
import se.contribe.bookstore.backend.api.Book;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BasketImplTest {

    private BasketImpl basket;

    @Before
    public void setUp() throws Exception {
        basket = new BasketImpl();
        basket.addBook(new Book("Title1", "Author1", BigDecimal.TEN));
        basket.addBook(new Book("Title2", "Author2", BigDecimal.ONE));
    }

    @Test
    public void testAddBook() {
        Book book = new Book("Title3", "Author3", BigDecimal.ZERO);
        basket.addBook(book);
        assertThat(basket.getBooks().length, is(3));
        assertThat(basket.getBooks()[2], is(book));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullBook() {
        basket.addBook(null);
    }

    @Test
    public void testRemoveExistingBook() {
        boolean result = basket.removeBook(new Book("Title1", "Author1", BigDecimal.TEN));
        assertThat(result, is(true));
        assertThat(basket.getBooks().length, is(1));
    }

    @Test
    public void testRemoveNonExistentBook() {
        boolean result = basket.removeBook(new Book("Foo", "Nobody", BigDecimal.TEN));
        assertThat(result, is(false));
        assertThat(basket.getBooks().length, is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullBook() {
        basket.removeBook(null);
    }

    @Test
    public void testGetBooks() {
        Book[] books = basket.getBooks();
        assertThat(books.length, is(2));
        assertThat(books[0].getTitle(), is("Title1"));
        assertThat(books[0].getAuthor(), is("Author1"));
        assertThat(books[0].getPrice(), is(BigDecimal.TEN));
        assertThat(books[1].getTitle(), is("Title2"));
    }

    @Test
    public void testGetTotalPrice() {
        assertThat(basket.getTotalPrice(), is(BigDecimal.valueOf(11)));
    }
}