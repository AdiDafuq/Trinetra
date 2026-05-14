package com.trinetra.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFeedback(
            @RequestParam("file") MultipartFile file
    ) {

        return ResponseEntity.ok(
                Map.of(
                        "message", "File received successfully",
                        "fileName", file.getOriginalFilename()
                )
        );
    }
}