package com.itheima.pyg.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.pojo.user.User;
import com.itheima.pyg.service.order.OrderService;
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
        return orderService.getOrderListByPage(pageNum,pageSize);
    }

    /**
     * 运营商后台根据时间段查询销售额
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("findOrderCountByTime")
    public ZImageResult findOrderCountByTime(String startTime,String endTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            return orderService.findOrderCountByTime(start,end);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 运营商后台查询各个商家销售额
     * @return
     */
    @RequestMapping("findTotalMoneyBySellerId")
    public BImageResult findTotalMoneyBySellerId(){
        return orderService.findTotalMoneyBySellerId();
    }



    /**
     * 需求1:运营商订单查询
     * @param
     * @param
     * @param order
     * @return
     */
    @RequestMapping("/search")
    public PageResult selectOrder(Integer page, Integer rows, @RequestBody Order order) {
        return orderService.selectOrder(page, rows, order);
    }


    @RequestMapping("/findAll")
    public List<Order> findAll() {
        return orderService.findAll();
    }


}
