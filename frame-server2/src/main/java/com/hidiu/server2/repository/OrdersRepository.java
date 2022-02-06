package com.hidiu.server2.repository;

import com.hidiu.server2.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * 订单Repository接口
 * @author fancie
 * @email 1084961@qq.com
 * @date 2022-02-01 09:40:47
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {


}
