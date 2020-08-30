package com.social.controller;

import com.social.dto.UserDetailsDto;
import com.social.exceptions.SpringRedditException;
import com.social.model.Image;
import com.social.model.User;
import com.social.repository.ImageRepository;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/infos")
    public ResponseEntity.BodyBuilder saveUserDetails(UserDetailsDto userDetailsDto){

        authService.saveInfos(userDetailsDto);
        return ResponseEntity.status(HttpStatus.OK);

    }

    @GetMapping("/profile")
    public ResponseEntity<User> currentUserInfos(){
        System.out.println("test profile");
        return new ResponseEntity<User>(this.getCurrentUser(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile/{userName}")
    public ResponseEntity<User> getUserInfos(@PathVariable String userName){
        Optional<User> user = userRepository.findByUsername(userName);
        return new ResponseEntity<User>(user.get(),HttpStatus.OK);
    }

    @PostMapping("/img")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("save image");
        User user = authService.getCurrentUser();

        Image img = new Image(file.getOriginalFilename(),file.getContentType(),compressBytes(file.getBytes()));
        imageRepository.save(img);
        user.setImage(img);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping("/profile/img")
    public ResponseEntity<Image> getImage(){
        System.out.println("get image");
        //final Optional<Image> retrieveImage = imageRepository.findByUser(this.getCurrentUser());
        User user = authService.getCurrentUser();
        Image img = new Image(user.getImage().getName(),user.getImage().getType(),decompressBytes(user.getImage().getPicByte()));

        return new ResponseEntity<>(img,HttpStatus.OK);
    }

    private User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(()->new SpringRedditException("User Not Found"));
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }




}
