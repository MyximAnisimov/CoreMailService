//package org.example.mailcoremicroservice.model;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("msg")
//public class KafkaCntroller {
//    @Autowired
//    private KafkaTemplate<String, String> kafkaExample;
//
//    @PostMapping
//    public void sendOrder(String msgId, String msg){
//        kafkaExample.send("msg", msgId, msg);
//    }
//
//}
