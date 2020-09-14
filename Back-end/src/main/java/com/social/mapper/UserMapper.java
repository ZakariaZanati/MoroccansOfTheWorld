package com.social.mapper;

import com.social.controller.ProfileController;
import com.social.dto.UserInfos;
import com.social.model.User;
import com.social.repository.UserRepository;
import com.social.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private UserRepository userRepository;


    @Mapping(target = "id",source = "userId")
    @Mapping(target = "firstName",source = "firstName")
    @Mapping(target = "lastName",source = "lastName")
    @Mapping(target = "username",source = "username")
    @Mapping(target = "image" ,expression = "java(getImage(user.getUsername()))")
    public abstract UserInfos mapToDto(User user);

    byte[] getImage(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User name not found - " + username));;
        if (user.getImage() == null)
            return null;
        return ProfileController.decompressBytes(user.getImage().getPicByte());
    }
}
