package com.social.service;

import com.social.exceptions.UserTypeException;
import com.social.model.Group;
import com.social.model.Provider;
import com.social.model.User;
import com.social.model.UserType;
import com.social.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
@Slf4j
@Transactional
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthService authService;

    public void save(MultipartFile file,String name,String description) throws IOException {
        User user = authService.getCurrentUser();
        if (user.getUserType().equals(UserType.PROVIDER)){
            Group group;
            if (file != null){
                group = Group.builder()
                        .adminUserName(user.getUsername())
                        .createdDate(Instant.now())
                        .name(name)
                        .description(description)
                        .imageBytes(file.getBytes())
                        .provider((Provider) user)
                        .build();
            }
            else{
                group = Group.builder()
                        .adminUserName(user.getUsername())
                        .createdDate(Instant.now())
                        .name(name)
                        .description(description)
                        .provider((Provider) user)
                        .build();
            }

            groupRepository.save(group);
        }
        else throw new UserTypeException("Operation not allowed for this type of user");
    }
}
