package com.hidiu.server1.repository;

import com.hidiu.server1.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * 用户Repository类
 * @author fancie
 * @email 1084961@qq.com
 * @date 2022-02-01 09:40:47
 */
@Component
public interface UsersRepository extends JpaRepository<Users, Integer> {


}
