package com.inca.orderservice.fallback;

import com.inca.orderservice.service.ProductClient;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public String list() {
        System.out.println("类ProductClientFallback的  list  已经执行");
        return null;
    }

    @Override
    public String findById(Integer id) {
        System.out.println("类ProductClientFallback的  findById  已经执行");
        return null;
    }
}
