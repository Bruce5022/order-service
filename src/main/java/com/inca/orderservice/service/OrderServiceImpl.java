package com.inca.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inca.orderservice.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service(OrderService.name)
public class OrderServiceImpl implements OrderService {

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Override
    public Order saveOrder(Order order) throws Exception {
//        Object obj = restTemplate.getForObject("http://product-service/api/product/findOne?id=" + order.getProductId(), Object.class);

        ServiceInstance instance = loadBalancerClient.choose("product-service");
        String url=String.format("http://%s:%s/api/product/findOne?id=" + order.getProductId(),instance.getHost(),instance.getPort());
        RestTemplate restTemplate=new RestTemplate();
        Object obj = restTemplate.getForObject(url, Object.class);
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
