package com.example.bai1.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book findById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id is null or blank");
        }
        return bookRepository.findById(id);
    }
}
