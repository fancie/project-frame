package com.hidiu.server2.service.impl;

import com.hidiu.server2.entity.Orders;
import com.hidiu.server2.repository.OrdersRepository;
import com.hidiu.server2.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Orders save(Orders order) {
        return ordersRepository.save(order);
    }
}
