package com.foroHub.foroHub.topic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.foroHub.foroHub.user.User;
import com.foroHub.foroHub.user.UserRepository;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid Topic topic) {
        // Suponiendo que el usuario está autenticado y su ID está disponible
        Long authenticatedUserId = 1L; // Esto es solo un ejemplo, reemplaza con el ID real del usuario autenticado
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        topic.setUser(user);
        Topic savedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(savedTopic);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody @Valid Topic topicDetails) {
        return topicRepository.findById(id)
                .map(topic -> {
                    topic.setTitle(topicDetails.getTitle());
                    topic.setMessage(topicDetails.getMessage());
                    return ResponseEntity.ok(topicRepository.save(topic));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(topic -> {
                    topicRepository.delete(topic);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
