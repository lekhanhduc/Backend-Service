package vn.khanhduc.backendservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.khanhduc.backendservice.service.EmailService;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-CONTROLLER")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public void sendEmail(@RequestParam String to,
                          @RequestParam String subject,
                          @RequestParam String content) {
        log.info("Send email to {}", to);
        emailService.send(to, subject, content);
    }

    @GetMapping("/verify-email")
    public void emailVerification(@RequestParam String to, String name) {
        log.info("Verify email to {}", to);
        emailService.emailVerification(to, name);
    }
}
