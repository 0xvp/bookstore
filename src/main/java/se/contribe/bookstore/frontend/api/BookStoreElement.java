package se.contribe.bookstore.frontend.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import se.contribe.bookstore.backend.api.Book;

public class BookStoreElement {
    private Book book;
    private int quantity;

    public BookStoreElement(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
