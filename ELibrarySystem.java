import java.util.*;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Book {
    private String title;
    private String author;
    private String category;
    private boolean available;

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Library {
    private List<User> users;
    private List<Book> books;
    private User currentUser;

    public Library() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void displayBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Category: " + book.getCategory());
                System.out.println();
            }
        }
    }

    public void borrowBook(String bookTitle) {
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle) && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println(currentUser.getUsername() + " has borrowed the book: " + bookTitle);
                return;
            }
        }
        System.out.println("Book not available for borrowing: " + bookTitle);
    }

    public void returnBook(String bookTitle) {
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle) && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println(currentUser.getUsername() + " has returned the book: " + bookTitle);
                return;
            }
        }
        System.out.println("Book not available for returning: " + bookTitle);
    }
}

public class ELibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Adding users
        library.addUser(new User("user1", "password1"));
        library.addUser(new User("user2", "password2"));

        // Adding books
        library.addBook(new Book("Java Programming", "John Doe", "Programming"));
        library.addBook(new Book("Data Structures", "Jane Smith", "Programming"));
        library.addBook(new Book("History of Science", "Alan Johnson", "History"));

        // User authentication
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User authenticatedUser = library.authenticateUser(username, password);
        if (authenticatedUser != null) {
            System.out.println("User authenticated: " + authenticatedUser.getUsername());
            library.setCurrentUser(authenticatedUser);
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
            return;
        }

        while (true) {
            // Display available actions
            System.out.println("\nChoose an action:");
            System.out.println("1. Browse Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. Exit");

            // Get user choice
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 1:
                    // Display available books
                    library.displayBooks();
                    break;
                case 2:
                    // Borrow a book
                    System.out.print("Enter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine();
                    library.borrowBook(borrowTitle);
                    break;
                case 3:
                    // Return a book
                    System.out.print("Enter the title of the book to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnBook(returnTitle);
                    break;
                case 4:
                    // Exit the program
                    System.out.println("Exiting the E-Library System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}
