package com.example.bai1.borrowing;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrowing")
public class BorrowingController {
    private final BorrowingRepository borrowingRepository;
    private final BorrowingService borrowingService;

    @PostMapping
    public Borrowing findById(@RequestBody BorrowingDto borrowingDto) {
        return borrowingService.borrowBook(borrowingDto.memberId(), borrowingDto.bookId(), borrowingDto.borrowDate(), borrowingDto.returnDate());
    }
}
