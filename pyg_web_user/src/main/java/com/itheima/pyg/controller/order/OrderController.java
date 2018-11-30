package com.itheima.pyg.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.vo.OrderVo;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;


    /**
     * 查出该用户未付款订单
     * @return
     */
    @RequestMapping("findOrderListUnPay")
    public List<OrderVo> findOrderListByUserIdUnPay(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);

        List<OrderVo> orderList=new ArrayList<>();

        if(!"anonymousUser".equals(username)){
            orderList=orderService.findOrderListByUserIdUnPay(username);
        }
        return orderList;
    }

    @RequestMapping("findOrderListByPageAndUserId")
    public List<OrderVo> findOrderListByPageAndUserId(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.getOrderListByPageAndUserId(userId);
    }

}
