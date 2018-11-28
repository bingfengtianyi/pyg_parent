package com.itheima.pyg.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.pojo.user.User;
import com.itheima.pyg.service.order.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @RequestMapping("findOrderList")
    public PageResult<Order> findOrderList(Integer pageNum, Integer pageSize){
        return orderService.getOrderListByPage(pageNum,pageSize);
    }

    /**
     * 运营商后台根据时间段查询销售额
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("findOrderCountByTime")
    public ZImageResult findOrderCountByTime(Date startTime,Date endTime){
        return orderService.findOrderCountByTime(startTime,endTime);
    }

    /**
     * 运营商后台查询各个商家销售额
     * @return
     */
    @RequestMapping("findTotalMoneyBySellerId")
    public BImageResult findTotalMoneyBySellerId(){
        return orderService.findTotalMoneyBySellerId();
    }
}
