package com.forexai.controller;

import com.forexai.model.Pattern;
import com.forexai.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/patterns")
public class PatternController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private SentimentService sentimentService;

    private List<Pattern> patterns = Collections.synchronizedList(new ArrayList<>());

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPattern(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            filePath = UPLOAD_DIR + UUID.randomUUID() + "-" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        Pattern pattern = new Pattern(name, description, filePath);
        patterns.add(pattern);
        return ResponseEntity.ok(pattern);
    }

    @GetMapping
    public List<Pattern> getAllPatterns() {
        return patterns;
    }

    @PostMapping("/analyze-sentiment")
    public Map<String, Object> analyzeSentiment(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        return sentimentService.analyze(text);
    }
}
