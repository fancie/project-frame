package com.hidiu.server1.service.impl;

import com.hidiu.server1.annotation.TargetDataSource;
import com.hidiu.server1.entity.Users;
import com.hidiu.server1.repository.UsersRepository;
import com.hidiu.server1.service.UsersService;
import com.hidiu.ems.DataSourceNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users save(Users user) {
        return usersRepository.save(user);
    }

    @Override
    @TargetDataSource(value = DataSourceNames.slave)
    public Users findById(Integer id){
        return usersRepository.findByIdFromSql(id);
    }
}
