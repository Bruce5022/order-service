package com.inca.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inca.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service(OrderService.name)
public class OrderServiceImpl implements OrderService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order saveOrder(Order order) throws Exception {
        Object obj = restTemplate.getForObject("http://product-service/api/product/findOne?id=" + order.getProductId(), Object.class);
        ObjectMapper om = new ObjectMapper();
        String skuStr = om.writeValueAsString(obj);
        HashMap sku = om.readValue(skuStr, HashMap.class);
        System.out.println(sku.get("name"));
        order.setProductName((String) sku.get("name"));
        order.setBuyerName("史战伟");
        order.setPrice((Integer) sku.get("price"));

        return order;
    }
}
