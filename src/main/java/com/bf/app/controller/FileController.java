package com.bf.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.bf.app.entities.User;
import com.bf.app.util.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("file")
public class FileController {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @GetMapping("streamingUser")
    public ResponseEntity<StreamingResponseBody> streamingUser() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response -> {
                    Executors.repeatingIOTask(15, index -> {
                        User user = new User("user " + index, "password " + index, "nickname " + index);
                        String line = objectMapper.writeValueAsString(user) + "\n";
                        response.write(line.getBytes());
                        response.flush();
                        Executors.tryExceptionTask(() -> Thread.sleep(1000));
                    });
                });
    }

}
