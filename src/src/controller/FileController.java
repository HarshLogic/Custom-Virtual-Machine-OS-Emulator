package controller;

import com.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    // Dependency Injection
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCodeFile(@RequestParam("file") MultipartFile file) {

        // Base case: If file is missing or empty
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Please upload a valid file.");
        }

        try {
            // Hand over the file to the service layer to parse the instructions
            String result = fileService.processAndLoadFile(file);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to process file: " + e.getMessage());
        }
    }
}