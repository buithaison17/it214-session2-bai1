package com.example.bai1.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id is null");
        }
        return memberRepository.findById(id);
    }
}
