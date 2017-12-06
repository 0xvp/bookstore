package se.contribe.bookstore.backend.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class Book {

    private String title;

    private String author;

    private BigDecimal price;

    public Book(String title, String author, BigDecimal price) {
        if (title == null) {
            throw new IllegalArgumentException("title must not be null");
        }
        if (author == null) {
            throw new IllegalArgumentException("author must not be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("price must not be null");
        }
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder()
                .append(title, book.title)
                .append(author, book.author)
                .append(price, book.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(author)
                .append(price)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("author", author)
                .append("price", price)
                .toString();
    }
}