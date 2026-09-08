package com.example.bai1.borrowing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;

    public boolean canBorrowBook(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            return false;
        }
        long currentBorrowedByMember = borrowingRepository.getCurrentBorrowedByMember(memberId);
        return currentBorrowedByMember < 5;
    }

    public Borrowing borrowBook(String memberId, String bookId, String borrowDate, String returnDate) {
        Borrowing borrowing = new Borrowing(
                UUID.randomUUID().toString(),
                bookId,
                memberId,
                borrowDate,
                returnDate
        );
        return borrowingRepository.addBorrowing(borrowing);
    }
}
