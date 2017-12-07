package se.contribe.bookstore.frontend.api;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import se.contribe.bookstore.backend.api.Book;

/**
 * Handles the storage of a book with its quantity, will be obsolete when a DB is added
 */
public class BookStoreElement {
    private Book book;
    private int quantity;

    public BookStoreElement(Book book, int quantity) {
        Validate.notNull(book);
        Validate.isTrue(quantity >= 0, "quantity must not be negative");
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BookStoreElement that = (BookStoreElement) o;

        return new EqualsBuilder()
                .append(quantity, that.quantity)
                .append(book, that.book)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(book)
                .append(quantity)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("book", book)
                .append("quantity", quantity)
                .toString();
    }
}
