package com.vinca.backboard.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vinca.backboard.entity.Member;
import com.vinca.backboard.entity.Reset;
import com.vinca.backboard.service.MemberService;
import com.vinca.backboard.service.ResetService;
import com.vinca.backboard.validation.MemberForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
@Log4j2
public class MemberController {
    
    private final MemberService memberService;
    private final ResetService resetService;

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/reset")
    public String reset() {
        return "member/reset";  // templates/member/reset.html
    }
    
    @GetMapping("/reset-password/{uuid}")
    public String reset_password(MemberForm memberForm, @PathVariable("uuid") String uuid) {
        Reset reset = this.resetService.getReset(uuid);
        log.info(String.format("▶▶▶ 확인된 이메일 : [%s]", reset.getEmail()));

        Member member = this.memberService.getMemberByEmail(reset.getEmail());
        memberForm.setUsername(member.getUsername());
        memberForm.setEmail(member.getEmail());
        return "member/newpassword";
    }
    
    @PostMapping("/reset-password")
    public String reset_password(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/newpassword";
        }

        if(!memberForm.getPassword1().equals(memberForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 일치하지 않습니다.");
        }

        Member member = this.memberService.getMember(memberForm.getUsername()); // 현재 사용자 가져옴
        member.setPassword(memberForm.getPassword1());  // 바뀐 패스워드로 변경

        this.memberService.setMember(member);   // 업데이트

        return "redirect:/member/login";
    }
    

    @GetMapping("/register")
    public String register(MemberForm memberForm) {
        return "member/register";   //templates/member/register.html
    }

    @PostMapping("/register")
    public String register(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "member/register";
        }
        if(!memberForm.getPassword1().equals(memberForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordIncorrect", "패스워드가 일치하지 않습니다.");
        }

        // 중복 사용자 처리
        try{
            this.memberService.setMember(memberForm.getUsername(), memberForm.getEmail(), memberForm.getPassword1());
        }catch(DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("registerFailed", "이미 등록된 사용자입니다.");
            return "member/register";
        }catch(Exception e){
            e.printStackTrace();
            bindingResult.reject("registerFailed", e.getMessage());
            return "member/register";
        }
        return "redirect:/";
    }
    
    
}
