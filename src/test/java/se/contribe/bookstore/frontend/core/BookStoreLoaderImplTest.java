package se.contribe.bookstore.frontend.core;

import org.apache.commons.io.input.BOMInputStream;
import org.junit.Before;
import org.junit.Test;
import se.contribe.bookstore.frontend.api.BookStoreElement;
import se.contribe.bookstore.frontend.cli.BookStoreRunner;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BookStoreLoaderImplTest {

    private BookStoreLoaderImpl loader;

    @Before
    public void setUp() throws Exception {
        loader = new BookStoreLoaderImpl();
    }

    @Test
    public void testLoad() throws Exception {
        URL url = BookStoreRunner.class.getResource("/bookstoredata.csv");
        Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");

        List<BookStoreElement>  bookStoreElements = loader.load(reader);

        assertThat(bookStoreElements.size(), is(7));
    }

    @Test (expected = NullPointerException.class)
    public void testNullLoad() throws Exception {
        URL url = null;
        Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
    }

    @Test
    public void testUnformattedLoad() throws Exception {
        URL url = BookStoreRunner.class.getResource("/bookstoredata_badfile.csv");
        Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");

        List<BookStoreElement>  bookStoreElements = loader.load(reader);
        assertThat(bookStoreElements.size(), is(0));
    }

    @Test
    public void testMalformattedLoad() throws Exception {
        URL url = BookStoreRunner.class.getResource("/bookstoredata_malformed.csv");
        Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
        List<BookStoreElement>  bookStoreElements = loader.load(reader);

        assertThat(bookStoreElements.size(), is(4));
    }
}