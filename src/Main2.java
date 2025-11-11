import java.util.*;

abstract class LibraryItem {
    String id;
    String title;
    boolean isAvailable;

    public LibraryItem(String id, String title, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.isAvailable = isAvailable;
    }

    abstract void displayInfo();
}

interface Borrowable {
    void borrowItem(String userId) throws BookNotAvailableException;
    void returnItem(String userId) throws InvalidReturnException;
}

class BookNotAvailableException extends Exception {
    public BookNotAvailableException() {
        super("Book is not available to borrow.");
    }
}

class InvalidReturnException extends Exception {
    public InvalidReturnException() {
        super("Invalid user trying to return the book.");
    }
}

class Book extends LibraryItem implements Borrowable {
    String author;
    String borrowedBy;

    public Book(String id, String title, String author, boolean isAvailable) {
        super(id, title, isAvailable);
        this.author = author;
    }

    @Override
    public void borrowItem(String userId) throws BookNotAvailableException {
        if (!isAvailable) {
            throw new BookNotAvailableException();
        } else {
            isAvailable = false;
            borrowedBy = userId;
            System.out.println("Book '" + title + "' borrowed successfully by User " + userId);
        }
    }

    @Override
    public void returnItem(String userId) throws InvalidReturnException {
        if (!Objects.equals(userId, borrowedBy)) {
            throw new InvalidReturnException();
        } else {
            isAvailable = true;
            borrowedBy = null;
            System.out.println("✅ Book '" + title + "' returned successfully by User " + userId);
        }
    }

    @Override
    void displayInfo() {
        System.out.println("📖 ID: " + id +
                ", Title: " + title +
                ", Author: " + author +
                ", Available: " + isAvailable);
    }
}

class Library {
    private Map<String, LibraryItem> items = new HashMap<>();

    public void addItem(LibraryItem item) {
        items.put(item.id, item);
    }

    public LibraryItem findItem(String id) {
        return items.get(id);
    }

    public void displayAllItems() {
        System.out.println("\n📚 Library Inventory:");
        for (LibraryItem item : items.values()) {
            item.displayInfo();
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
        Library library = new Library();

        // Create some books
        Book b1 = new Book("B001", "The Love Hypothesis", "Ali Hazelwood", true);
        Book b2 = new Book("B002", "Atomic Habits", "James Clear", true);
        Book b3 = new Book("B003", "It Ends With Us", "Colleen Hoover", true);

        // Add books to library
        library.addItem(b1);
        library.addItem(b2);
        library.addItem(b3);

        // Display all books initially
        library.displayAllItems();

        System.out.println("\n Borrowing Operations");
        try {
            b1.borrowItem("U001");
            b1.borrowItem("U002"); // This should throw BookNotAvailableException
        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n Returning Operations");
        try {
            b1.returnItem("U002"); // Wrong user, throws InvalidReturnException
        } catch (InvalidReturnException e) {
            System.out.println(e.getMessage());
        }

        try {
            b1.returnItem("U001"); // Correct user
        } catch (InvalidReturnException e) {
            System.out.println(e.getMessage());
        }

        // Display all books after operations
        library.displayAllItems();
    }
}
