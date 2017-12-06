package se.contribe.bookstore;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.contribe.bookstore.frontend.api.BookStore;

@Configuration
@ComponentScan
public class BookStoreApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BookStoreApp.class);
        BookStore bookStore = context.getBean(BookStore.class);
        bookStore.run(args);
        context.close();
    }
}
