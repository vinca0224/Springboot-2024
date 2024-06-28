package com.vinca.backboard.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinca.backboard.service.MailService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class RestMailController {
    
    private final MailService mailService;

    @PostMapping("/test-email")
    public ResponseEntity<HttpStatus> testEmail() {
        String to = "dlarudtp98@gmail.com";
        String subject = "전송테스트 메일";
        String message = "테스트 메일 메시지입니다.";
        
        mailService.sendMail(to, subject, message);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    
}
