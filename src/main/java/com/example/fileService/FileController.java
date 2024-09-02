package com.example.fileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createFile(@RequestBody String requestBody) {
        try {
            System.out.println("Request Body: " + requestBody);

            JsonNode jsonNode = objectMapper.readTree(requestBody);
            String title = jsonNode.get("title").asText();
            String creationDateStr = jsonNode.get("creationDate").asText();
            String description = jsonNode.has("description") ? jsonNode.get("description").asText() : null;

            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr);

            FileEntity fileEntity = new FileEntity();
            fileEntity.setTitle(title);
            fileEntity.setCreationDate(creationDate);
            fileEntity.setDescription(description);

            FileEntity savedFile = fileRepository.save(fileEntity);
            return ResponseEntity.ok(savedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid JSON input");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        if (fileEntity.isPresent()) {
            return ResponseEntity.ok(fileEntity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "creationDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        Page<FileEntity> filePage = fileRepository.findAll(pageable);

        return ResponseEntity.ok(filePage);
    }
}
