package kolok2.zad1;

import java.util.*;
import java.util.stream.Collectors;

class Book implements Comparable<Book>{
    String title;
    String category;
    float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f",title,category,price);
    }

    @Override
    public int compareTo(Book o) {
        return category.compareTo(o.category);
    }
}

class BookCollection{
    TreeMap<String, List<Book>> books;

    public BookCollection() {
        books = new TreeMap<>();
    }

    public void addBook(Book book) {
        if (books.get(book.category) == null){
            books.put(book.category, new ArrayList<>());
        }
        books.get(book.category).add(book);
    }


    public void printByCategory(String category) {
        Comparator comparator = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
        books.get(category).stream().sorted(comparator).forEach(System.out::println);
    }

    public List<Book> getCheapestN(int n) {
        List<Book> cheapestBooks = new ArrayList<>();
        books.values().stream().forEach(list -> {
            list.stream().forEach(book -> {
                cheapestBooks.add(book);
            });
        });
        Comparator comparator = Comparator.comparing(Book::getPrice);
        Comparator comparator1 = Comparator.comparing(Book::getCategory).reversed();
        return (List<Book>) cheapestBooks.stream().sorted(comparator.thenComparing(comparator1)).limit(n).collect(Collectors.toList());
    }
}

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде