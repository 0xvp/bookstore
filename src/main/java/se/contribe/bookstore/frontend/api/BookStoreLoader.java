package se.contribe.bookstore.frontend.api;

import java.io.Reader;
import java.util.List;

public interface BookStoreLoader {

    /**
     * Loads the data from a given reader e.g from an inputstream from an url
     *
     * @param reader reader
     * @return List of BookStoreElement
     */
    List<BookStoreElement> load(Reader reader);
}
