package com.vinca.backboard.service;

import java.time.LocalDateTime;
import java.util.Optional;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinca.backboard.common.NotFoundException;
import com.vinca.backboard.entity.Member;
import com.vinca.backboard.repository.MemberRepository;
import com.vinca.backboard.security.MemberRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 새로운 사용자 생성
    public Member setMember(String username, String email, String password){
        Member member = Member.builder().username(username).email(email).regDate(LocalDateTime.now()).build();
        
        // ... 처리되는 일이 많아서 1~2초 시간이 걸리면
        // BCryptPasswordEncoder 매번 새롭게 객체를 생성
        // 이 방법보다는 Bean에 등록하고 쓰는게 유지보수를 위해서 더 좋음
        // BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(password));    // 암호화한 값을 DB에 저장
        member.setRegDate(LocalDateTime.now());
        member.setRole(MemberRole.USER);    // 일반 사용자 권한 부여
        this.memberRepository.save(member);
        
        return member;
    }
    
    // 기존 사용자 비밀번호 초기화
    public void setMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword())); // Bcrypt 암호화
        this.memberRepository.save(member); // update
    }

    // 사용자를 가져오는 메서드
    public Member getMember(String username){
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if(member.isPresent()){
            return member.get();
        }
        else
            throw new NotFoundException("Member not found");
    }

    // 24. 06. 28. 이메일로 사용자 검색 메서드
    public Member getMemberByEmail(String email){
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if(member.isPresent()){
            return member.get();
        }else{
            throw new NotFoundException("Member not found");
        }
    }

    // 24. 07. 04. React에서 넘어온 정보로 로그인 확인하기
    public Member getMemberByUsernameAndPassword(String username, String password) {
        Optional<Member> _member = this.memberRepository.findByUsername(username);
        Member member;
        if (_member.isPresent()) {
            member = _member.get(); // 같은 이름의 사용자 정보가 다 넘어옴(암호화된 비밀번호까지)

            // plain text와 암호화된 값이 같은 값을 가지고 있는지 체크
            boolean isMatched = passwordEncoder.matches(password, member.getPassword());

            if (isMatched) {
                return member;
            }
            else {
                throw new NotFoundException("member not found");
            }
        } else {
            throw new NotFoundException("member not found");

        }
    }

}
