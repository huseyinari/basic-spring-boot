package org.sinhasoft.basicspringboot.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jms")
@RequiredArgsConstructor
public class JmsController {
    private final MyTopicProducer myTopicProducer;
    private final MyQueueProducer myQueueProducer;

    @PostMapping("/topic")
    public ResponseEntity<?> sendTopic(@RequestBody String message) {
        myTopicProducer.sendMessage(message);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/queue")
    public ResponseEntity<?> sendQueue(@RequestBody String message) {
        myQueueProducer.sendMessage(message);
        return ResponseEntity.ok().build();
    }
}
