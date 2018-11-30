package com.itheima.pyg.service.order;

import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.entity.vo.OrderVo;
import com.itheima.pyg.pojo.log.PayLog;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.pojo.user.User;

import java.util.Date;
import java.util.List;

public interface OrderService {

    void add(Order order);

    List<Order> findOrderListByUserId(String userId);

    PayLog findPayLogByUserIdFromRedis(String userId);

    void updateOrderStatus(String out_trade_no,String transaction_id);


    //查出该用户未付款订单
    List<OrderVo> findOrderListByUserIdUnPay(String userId);

    /**
     * 根据订单号查找到订单金额
     * @param out_trade_no
     * @return
     */
    Long findTotalFeeFromRedis(String out_trade_no);

    /**
     * 修改未支付订单状态
     * @param out_trade_no
     * @param transaction_id
     */
    void updateUnPayOrderStatus(String out_trade_no,String transaction_id);



    /**
     * 运营商后台 查询全部订单数据用于导出excel
     * @return
     */
    List<Order> getOrderList();

    List<OrderVo> getOrderListByPageAndUserId(String userId);

    PageResult<Order> getOrderListByPage(Integer pageNum, Integer pageSize);

    ZImageResult findOrderCountByTime(Date startTime, Date endTime);

    BImageResult findTotalMoneyBySellerId();

    PageResult<Order> getOrderListByPageAndSeller(Integer pageNum, Integer pageSize,String seller);


    // 订单查询
    public PageResult selectOrder(Integer pageNum, Integer pageSize, Order order);

    public List<Order> findAll();
}
