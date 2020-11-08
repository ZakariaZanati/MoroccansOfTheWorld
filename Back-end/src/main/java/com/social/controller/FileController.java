package com.social.controller;

import com.social.dto.ResponseMessage;
import com.social.model.User;
import com.social.repository.UserRepository;
import com.social.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}")
    public ResponseEntity<ResponseMessage> uploadResume(MultipartFile file, @PathVariable Long id){
        System.out.println("user id ******************************************************************" +id);
        String message = "";
        try {
            storageService.storeResume(file, id);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (IOException e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getResumeData(@PathVariable Long id){
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+user.getUsername()+"_resume.pdf\"")
                .body(user.getResume());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getResume(@PathVariable Long id){
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/download/")
                .path(String.valueOf(id))
                .toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(fileDownloadUri);
    }
}
