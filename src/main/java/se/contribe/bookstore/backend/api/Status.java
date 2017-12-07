package se.contribe.bookstore.backend.api;

/**
 * States for availability of books in the bookstore
 */
public enum Status {
    OK(0),
    NOT_IN_STOCK(1),
    DOES_NOT_EXIST(2);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
