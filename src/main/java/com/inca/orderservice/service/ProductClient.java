package com.inca.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service")
public interface ProductClient {

    @RequestMapping("/api/product/list")
    String list();

    @RequestMapping("/api/product/findOne")
    String findById(@RequestParam(value = "id") Integer id);
}
