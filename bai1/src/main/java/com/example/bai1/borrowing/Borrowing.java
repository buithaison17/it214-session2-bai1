package com.example.bai1.borrowing;

public record Borrowing(
        String id,
        String bookId,
        String memberId,
        String borrowDate,
        String returnDate
) {
}
