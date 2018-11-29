package com.itheima.pyg.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @RequestMapping("findOrderList")
    public PageResult<Order> findOrderList(Integer pageNum, Integer pageSize){
        String seller = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getOrderListByPageAndSeller(pageNum,pageSize,seller);
    }

}
