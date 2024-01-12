package com.api.imageapi.application.users;

import com.api.imageapi.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToUser(UserDTO dto) {
        return User.builder().email(dto.getEmail()).name(dto.getName()).password(dto.getPassword()).build();
    }
}
