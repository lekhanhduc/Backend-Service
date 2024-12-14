package vn.khanhduc.backendservice.service;

public interface EmailService {
    void send(String to, String subject, String body);
    void emailVerification(String to, String name);
}
