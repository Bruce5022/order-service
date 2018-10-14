package com.inca.orderservice.service;

import com.inca.orderservice.fallback.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-service", fallback = ProductClientFallback.class)
public interface ProductClient {

    @RequestMapping("/api/product/list")
    String list();

    @RequestMapping("/api/product/findOne")
    String findById(@RequestParam(value = "id") Integer id);
}
