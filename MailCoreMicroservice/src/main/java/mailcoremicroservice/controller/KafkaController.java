package mailcoremicroservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mailcoremicroservice.events.UserRegisteredEvent;
import mailcoremicroservice.services.ProjectEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@EnableKafka
public class KafkaController {

    @Autowired
    private ProjectEmailService projectEmailService;

    @KafkaListener(topics="register")
    public void handleUserCreatedEvent(String json) throws JsonProcessingException {
        UserRegisteredEvent event = new ObjectMapper().readValue(json, UserRegisteredEvent.class);
        String userEmail = event.email;
        String message = event.message;
        projectEmailService.sendSimpleMessage(userEmail, "Добро пожаловать!", message);
    }

}
