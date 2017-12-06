package se.contribe.bookstore.frontend.api;

import java.io.Reader;
import java.util.List;

public interface BookStoreLoader {
    List<BookStoreElement> load(Reader reader);
}
