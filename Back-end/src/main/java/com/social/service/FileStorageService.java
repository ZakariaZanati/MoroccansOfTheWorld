package com.social.service;

import com.social.model.User;
import com.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {

    @Autowired
    private UserRepository userRepository;

    public void storeResume(MultipartFile file, Long id) throws IOException{
        User user = userRepository.findById(id).get();
        user.setResume(file.getBytes());
        userRepository.save(user);
    }


}
