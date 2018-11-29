package com.itheima.pyg.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.service.order.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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




    @RequestMapping("/search")
    public PageResult selectOrder(Integer page, Integer rows, @RequestBody Order order) {
        String sellerName = SecurityContextHolder.getContext().getAuthentication().getName();//获取当前用户登录信息
        order.setSellerId(sellerName);
        System.out.println(sellerName);
        PageResult pageResult = orderService.selectOrder(page, rows, order);
        return pageResult;
    }



    /**
     * 商家后台订单查询
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<Order> findAll() {
        return orderService.findAll();
    }


}
