package com.api.imageapi.domain.service;

import com.api.imageapi.domain.AccessToken;
import com.api.imageapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);

    User save(User user);

    AccessToken authenticate(String email, String password);
}
