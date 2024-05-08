package com.tychicus.opentalk.controller;
import com.tychicus.opentalk.service.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {

    private final ICloudinaryService cloudinaryService;

    @PostMapping("/upload/slide")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file
                                         ) throws IOException {
        return ResponseEntity.ok(cloudinaryService.uploadFile(file));
    }

    @GetMapping("/get-url")
    public ResponseEntity<?> getUrlImage(@RequestParam("folder") String folderName) {
        return ResponseEntity.ok(cloudinaryService.getUrlImage(folderName));
    }
}
