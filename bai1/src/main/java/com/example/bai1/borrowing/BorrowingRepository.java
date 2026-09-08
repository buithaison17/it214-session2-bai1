package com.example.bai1.borrowing;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BorrowingRepository {
    private List<Borrowing> borrowings = new ArrayList<>();

    public BorrowingRepository() {
        borrowings.add(new Borrowing("1", "1", "1", "2021-01-01", "2021-01-10"));
        borrowings.add(new Borrowing("2", "2", "2", "2021-01-02", "2021-01-11"));
        borrowings.add(new Borrowing("3", "3", "3", "2021-01-03", "2021-01-12"));
        borrowings.add(new Borrowing("4", "4", "4", "2021-01-04", "2021-01-13"));
        borrowings.add(new Borrowing("5", "5", "5", "2021-01-05", "2021-01-14"));
    }

    public List<Borrowing> findAll() {
        return borrowings;
    }

    public long getCurrentBorrowedByMember(String memberId) {
        return borrowings.stream()
                .filter(borrowing -> borrowing.memberId().equals(memberId))
                .count();
    }

    public Borrowing addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
        return borrowing;
    }
}
