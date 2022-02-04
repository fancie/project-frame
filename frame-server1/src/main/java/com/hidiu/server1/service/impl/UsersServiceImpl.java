package com.hidiu.server1.service.impl;

import com.hidiu.server1.entity.Users;
import com.hidiu.server1.repository.UsersRepository;
import com.hidiu.server1.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users save(Users user) {
        return usersRepository.save(user);
    }
}
