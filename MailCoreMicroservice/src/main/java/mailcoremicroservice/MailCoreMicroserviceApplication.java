package mailcoremicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableKafka
@SpringBootApplication
public class MailCoreMicroserviceApplication {

//    @KafkaListener(topics = "msg")
//    public void msgListener(String msg){
//        System.out.println(msg);
//    }


    public static void main(String[] args) {
        SpringApplication.run(MailCoreMicroserviceApplication.class, args);
    }

}
