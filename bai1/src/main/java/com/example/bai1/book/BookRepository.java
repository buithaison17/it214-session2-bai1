package com.example.bai1.book;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        books.add(new Book("1", "C++", "Bjarne Stroustrup", "Pearson", "2020"));
        books.add(new Book("2", "JavaScript: The Definitive Guide", "David Flanagan, Yuki Kawashima", "O'Reilly Media", "2019"));
        books.add(new Book("3", "Effective Java (3rd Edition)", "Joshua Bloch", "O'Reilly Media", "2019"));
        books.add(new Book("4", "Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin", "Pragmatic Bookshelf", "2019"));
        books.add(new Book("5", "Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Addison-Wesley Professional", "1995"));
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
