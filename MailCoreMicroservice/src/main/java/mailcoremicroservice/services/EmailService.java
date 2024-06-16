package mailcoremicroservice.services;

public interface EmailService {
    public void sendSimpleMessage(String toAddress, String subject, String message);
}
