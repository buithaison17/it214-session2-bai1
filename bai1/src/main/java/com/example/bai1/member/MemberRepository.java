package com.example.bai1.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class MemberRepository {
    private final List<Member> members = new ArrayList<>();

    public MemberRepository() {
        members.add(new Member("1", "Member 1", "Address 1", "Phone 1"));
        members.add(new Member("2", "Member 2", "Address 2", "Phone 2"));
        members.add(new Member("3", "Member 3", "Address 3", "Phone 3"));
    }

    public List<Member> findAll() {
        return members;
    }

    public Member findById(String id) {
        return members.stream()
                .filter(member -> member.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Member not found"));
    }
}