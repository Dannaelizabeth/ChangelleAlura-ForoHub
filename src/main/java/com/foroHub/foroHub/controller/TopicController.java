package com.foroHub.foroHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foroHub.foroHub.topic.Topic;
import com.foroHub.foroHub.topic.TopicRepository;
import com.foroHub.foroHub.user.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    
    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid Topic topic) {
        // Suponiendo que el usuario está autenticado y su ID está disponible
        User user = userRepository.findById(authenticatedUserId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        topic.setUser(user);
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }
    
    // Otros métodos...
}
