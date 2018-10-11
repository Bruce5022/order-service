package com.inca.orderservice.controller;

import com.inca.orderservice.entity.Order;
import com.inca.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Value("${server.port}")
    private String port;

    @Autowired
    OrderService orderService;

    @RequestMapping("save")
    public Object save(Order order) throws Exception {
        order = orderService.saveOrder(order);
        order.setTradeNo(port + ":" + order.getTradeNo());
        return order;
    }


}
