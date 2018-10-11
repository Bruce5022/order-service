package com.inca.orderservice.service;

import com.inca.orderservice.entity.Order;

public interface OrderService {
    String name = "productService";

    Order saveOrder(Order order) throws Exception;
}
