package mailcoremicroservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mailcoremicroservice.events.UserRegisteredEvent;
import mailcoremicroservice.services.ProjectEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableKafka
public class KafkaCntroller {

    @Autowired
    private ProjectEmailService projectEmailService;

    @KafkaListener(topics="register")
    public void handleUserCreatedEvent(String json) throws JsonProcessingException {
        UserRegisteredEvent event = new ObjectMapper().readValue(json, UserRegisteredEvent.class);

        // Доступ к email пользователя (например, из сообщения):
        String userEmail = event.email;
        String message = event.message;
        // Отправка письма:
        System.out.println(json);
        projectEmailService.sendSimpleMessage(userEmail, "Добро пожаловать!", message);
    }

}
