package se.contribe.bookstore.cli;

import org.junit.Before;
import org.junit.Test;
import se.contribe.bookstore.frontend.api.*;
import se.contribe.bookstore.backend.api.Book;
import se.contribe.bookstore.backend.api.BookList;
import se.contribe.bookstore.frontend.core.BookStoreImpl;

import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookStoreImplTest {

    private BookStoreImpl bookStore;

    private BookList bookList;
    private BookStoreLoader bookStoreLoader;
    private List<BookStoreElement> bookStoreElements;

    @Before
    public void setUp() throws Exception {
        bookStoreElements = new ArrayList<BookStoreElement>(asList(
                new BookStoreElement(new Book("Mastering åäö", "Average Swede", BigDecimal.TEN), 5),
                new BookStoreElement(new Book("Generic Title", "First Author", BigDecimal.TEN), 3)
        ));
        bookList = mock(BookList.class);
        bookStoreLoader = mock(BookStoreLoader.class);
        when(bookStoreLoader.load(any(Reader.class))).thenReturn(bookStoreElements);
        bookStore = new BookStoreImpl(bookList, bookStoreLoader);
    }

    @Test
    public void testFoo() {
        //bookStore.run();
        //assertThat(bookStore.hashCode(), is(3));
        verify(bookList, times(2)).add(any(Book.class), any(Integer.class));
    }
}
