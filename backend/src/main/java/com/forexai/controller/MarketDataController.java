package com.forexai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles pattern upload endpoints.
 * API: POST /api/patterns/upload (multipart/form-data)
 */
@RestController
@RequestMapping("/api/patterns")
public class PatternController {

    // Where to save uploaded files (change as needed for production)
    private static final String UPLOAD_DIR = "uploads/patterns/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPattern(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // Validate inputs
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Pattern name is required.");
        }
        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Pattern description is required.");
        }

        String filename = null;
        String fileUrl = null;

        if (file != null && !file.isEmpty()) {
            try {
                // Ensure upload directory exists
                Files.createDirectories(Paths.get(UPLOAD_DIR));

                // Clean filename, avoid path traversal
                filename = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                Path filePath = Paths.get(UPLOAD_DIR, filename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Assuming the frontend can access /uploads/patterns/ via static mapping
                fileUrl = "/uploads/patterns/" + filename;

            } catch (IOException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to save uploaded file: " + ex.getMessage());
            }
        }

        // TODO: Save pattern info (name, description, fileUrl) to a database if desired.

        // Build response
        Map<String, Object> resp = new HashMap<>();
        resp.put("name", name);
        resp.put("description", description);
        if (fileUrl != null) {
            resp.put("fileUrl", fileUrl);
            resp.put("filename", filename);
        }

        return ResponseEntity.ok(resp);
    }
}

