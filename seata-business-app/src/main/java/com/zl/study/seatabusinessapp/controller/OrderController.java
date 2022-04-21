package com.zl.study.seatabusinessapp.controller;

import com.zl.study.seatabusinessapp.model.Order;
import com.zl.study.seatabusinessapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/findOrder")
    public Order findOrder() {
        return orderService.findOrderById(1);
    }

    @PostMapping("/insertOrder")
    public String insertOrder(@RequestBody Order order) {
        // 验证问题:外层事物不影响内层事物的提交和回滚
        // return orderService.insert(order) == 1 ? "ok": "fail";
        // 验证问题:内层事物的异常,会影响外部事物的回滚
        // return orderService.insertV2(order) == 1 ? "ok": "fail";
        // 解决问题:内层事物的异常,会影响外部事物的回滚
        return orderService.insertV3(order) == 1 ? "ok": "fail";
    }

    @PostMapping("delOrder/{id}")
    public String delOrder(@PathVariable("id") Integer id) {
        return orderService.delete(id) == 1 ? "ok" : "fail";
    }
}
