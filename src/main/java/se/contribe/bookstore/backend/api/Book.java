package se.contribe.bookstore.backend.api;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Books
 */
public class Book {

    private String title;

    private String author;

    private BigDecimal price;

    /**
     * A book is unique over the three parameters title, author and price
     * this allows books with same title and author but different price
     * since no handling for duplicates with different prices is provided
     *
     * @param title
     * @param author
     * @param price
     */
    public Book(String title, String author, BigDecimal price) {
        Validate.notNull(title);
        Validate.notNull(author);
        Validate.notNull(price);
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

    /**
     * A book is equal when title, author and price are equal
     * the price is equal when the value is equal ignoring trailing zero decimal places
     *
     * @param o other
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!title.equals(book.title)) return false;
        if (!author.equals(book.author)) return false;
        return price.compareTo(book.price) == 0; // equals is not enough
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(author)
                .append(price.doubleValue()) // equals => same hashCode but not the other way round
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
