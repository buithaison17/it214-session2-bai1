package com.example.bai1.borrowing;

public record BorrowingDto(
        String bookId,
        String memberId,
        String borrowDate,
        String returnDate
) {
}
