package com.inca.orderservice.controller;

import com.inca.orderservice.entity.Order;
import com.inca.orderservice.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Value("${server.port}")
    private String port;

    @Autowired
    OrderService orderService;
    @Autowired
    StringRedisTemplate redisTemplate;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveFail")
    public Object save(Order order) throws Exception {
//        TimeUnit.SECONDS.sleep(10);
        Map<String, Object> map = new HashMap();
        map.put("code", "0");
        order = orderService.saveOrder(order);
        order.setTradeNo(port + ":" + order.getTradeNo());
        map.put("data", order);
        return map;
    }


    private Object saveFail(Order order) {
        String key = "save-order";
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String val = valueOp.get(key);
        if (StringUtils.isEmpty(val)) {
            System.out.println("系统故障:赶快处理!!!");
            valueOp.set(key, "" + System.currentTimeMillis(), 20, TimeUnit.SECONDS);
        } else {
            System.out.println("已发送短信给维修人员");
        }
        Map<String, Object> map = new HashMap();
        map.put("code", "-1");
        map.put("msg", "下单失败!");
        return map;
    }
}
