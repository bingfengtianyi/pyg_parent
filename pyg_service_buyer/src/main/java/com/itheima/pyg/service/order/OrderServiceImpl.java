package com.itheima.pyg.service.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pyg.dao.item.ItemDao;
import com.itheima.pyg.dao.log.PayLogDao;
import com.itheima.pyg.dao.order.OrderDao;
import com.itheima.pyg.dao.order.OrderItemDao;
import com.itheima.pyg.dao.seller.SellerDao;
import com.itheima.pyg.entity.BImageResult;
import com.itheima.pyg.entity.PageResult;
import com.itheima.pyg.entity.ZImageResult;
import com.itheima.pyg.entity.vo.Cart;
import com.itheima.pyg.entity.vo.OrderVo;
import com.itheima.pyg.pojo.item.Item;
import com.itheima.pyg.pojo.log.PayLog;
import com.itheima.pyg.pojo.order.Order;
import com.itheima.pyg.pojo.order.OrderItem;
import com.itheima.pyg.pojo.order.OrderItemQuery;
import com.itheima.pyg.pojo.order.OrderQuery;
import com.itheima.pyg.pojo.seller.Seller;
import com.itheima.pyg.pojo.user.User;
import com.itheima.pyg.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private SellerDao sellerDao;

    /**
     * 分页获得订单列表
     * @return
     */
    @Override
    public PageResult<Order> getOrderListByPage(Integer pageNum, Integer pageSize) {
        //设置分页查询条件
        PageHelper.startPage(pageNum,pageSize);
        //进行查询
        Page<Order> page = (Page<Order>) orderDao.selectByExample(null);
        //封装PageResult对象
        PageResult<Order>   pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public PageResult<Order> getOrderListByPageAndSeller(Integer pageNum, Integer pageSize,String seller) {
        //设置分页查询条件
        PageHelper.startPage(pageNum,pageSize);
        //进行查询
        OrderQuery query = new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andSellerIdEqualTo(seller);
        Page<Order> page = (Page<Order>) orderDao.selectByExample(query);
        //封装PageResult对象
        PageResult<Order>   pageResult = new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 保存订单信息及订单明细到数据库
     *
     * @param order
     */
    @Override
    public void add(Order order) {
        //1.从redis中获取购物车数据
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(order.getUserId());
        if (cartList == null) {
            throw new RuntimeException("购物车为空");
        }
        //2.保存订单和订单明细
        //支付订单号
        String outTradeNo = idWorker.nextId() + "";
        long totalMoney = 0L;
        Order tbOrder = new Order();
        for (Cart cart : cartList) {
            for (OrderItem orderItem : cart.getOrderItemList()) {
                Item item = itemDao.selectByPrimaryKey(orderItem.getItemId());
                if (item.getNum()>=orderItem.getNum()){
                    if (item.getStockCount()!=null){
                        item.setStockCount(item.getStockCount()+orderItem.getNum());
                    }else {
                        item.setStockCount(0+orderItem.getNum());
                    }
                    item.setNum(item.getNum()-orderItem.getNum());
                    itemDao.updateByPrimaryKeySelective(item);
                }else {
                    throw new RuntimeException("该商品库存不足");
                }
            }
            long orderId = idWorker.nextId();//订单id
            tbOrder.setOrderId(orderId);
            tbOrder.setOutTradeNo(outTradeNo);//支付订单号,一个购物车列表整体对应一个订单
            tbOrder.setPaymentType(order.getPaymentType());//支付方式
            tbOrder.setStatus("1");//支付状态
            tbOrder.setCreateTime(new Date());//创建时间
            tbOrder.setUpdateTime(new Date());//修改时间
            tbOrder.setUserId(order.getUserId());//用户id
            tbOrder.setReceiverAreaName(order.getReceiverAreaName());//收货地址
            tbOrder.setReceiver(order.getReceiver());//收件人
            tbOrder.setReceiverMobile(order.getReceiverMobile());//电话
            tbOrder.setSourceType(order.getSourceType());//订单来源
            tbOrder.setSellerId(cart.getSellerId());//商家id
            //实付金额
            double money = 0;
            List<OrderItem> orderItemList = cart.getOrderItemList();
            if (orderItemList == null) {
                throw new RuntimeException("购物车内没东西");
            }
            for (OrderItem orderItem : orderItemList) {
                orderItem.setOrderId(orderId);
                orderItem.setId(idWorker.nextId());
                orderItemDao.insertSelective(orderItem);
                money += orderItem.getTotalFee().doubleValue();
            }
            totalMoney += (long) (money * 100);
            tbOrder.setPayment(new BigDecimal(money));
            orderDao.insertSelective(tbOrder);
        }
        //3.如果是微信支付,则在支付日志中添加记录
        if ("1".equals(order.getPaymentType())) {
            PayLog payLog = new PayLog();
            payLog.setCreateTime(new Date());
            payLog.setOutTradeNo(outTradeNo);
            payLog.setPayType(order.getPaymentType());
            payLog.setTotalFee(totalMoney);
            payLog.setUserId(order.getUserId());
            payLog.setTradeState("0");
            payLogDao.insertSelective(payLog);

            //将支付日志以用户名为key存入redis,以便修改订单支付状态
            redisTemplate.boundHashOps("payLog").put(order.getUserId(),payLog);
        }
        //4.清除购物车记录
        redisTemplate.boundHashOps("cartList").delete(order.getUserId());
    }

    /**
     * //TODO 待实现用户查询自己的订单
     * 根据用户名查询订单
     * @param userId
     * @return
     */
    @Override
    public List<Order> findOrderListByUserId(String userId) {
        return null;
    }

    /**
     * 根据用户名从redis中查询支付日志
     * @param userId
     * @return
     */
    @Override
    public PayLog findPayLogByUserIdFromRedis(String userId) {
        return (PayLog) redisTemplate.boundHashOps("payLog").get(userId);
    }

    /**
     * 修改订单状态
     * @param out_trade_no  支付订单号
     * @param transaction_id    微信返回的交易流水号
     */
    @Override
    public void updateOrderStatus(String out_trade_no, String transaction_id) {
        //1.修改支付日志状态及相关字段
        PayLog payLog = payLogDao.selectByPrimaryKey(out_trade_no);
        payLog.setTransactionId(transaction_id);
        payLog.setPayTime(new Date());
        payLog.setTradeState("1");
        payLogDao.updateByPrimaryKeySelective(payLog);
        //2.修改订单的状态
        Order order = new Order();
        order.setStatus("2");
        order.setPaymentTime(new Date());
        OrderQuery query = new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andOutTradeNoEqualTo(out_trade_no);
        orderDao.updateByExampleSelective(order,query);
        //3.清除redis中支付日志
        redisTemplate.boundHashOps("payLog").delete(payLog.getUserId());
    }



    /**
     * 查出该用户未付款订单
     * @param userId
     * @return
     */
    @Override
    public List<OrderVo> findOrderListByUserIdUnPay(String userId) {
        List<OrderVo> orderVoList =new ArrayList<>();

        OrderQuery query=new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo("1");
        query.setOrderByClause("create_time desc");
        List<Order> orders = orderDao.selectByExample(query);


        if(orders!=null && orders.size()>0){

            for (Order order : orders) {
                OrderVo orderVo=new OrderVo();

                Date createTime = order.getCreateTime();
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String newTime = format.format(createTime);
                orderVo.setCreateTime(newTime);
                orderVo.setOrderId(order.getOrderId().toString());

                String sellerId = order.getSellerId();
                if (sellerId==null){
                    sellerId="pyg";
                }

                Seller seller = sellerDao.selectByPrimaryKey(sellerId);
                orderVo.setNickName(seller.getNickName());

                //添加订单对应商品
                OrderItemQuery orderItemQuery=new OrderItemQuery();
                OrderItemQuery.Criteria criteria1 = orderItemQuery.createCriteria();
                criteria1.andOrderIdEqualTo(order.getOrderId());
                List<OrderItem> orderItems = orderItemDao.selectByExample(orderItemQuery);

                //将订单对应的商品价格和订单号存入redis
                if(orderItems!=null && orderItems.size()>0){
                    long totalFee=0L;
                    for (OrderItem orderItem : orderItems) {
                        totalFee+=orderItem.getTotalFee().doubleValue()*100;
                    }
                    redisTemplate.boundHashOps("unPayOrderList").put(order.getOrderId()+"",totalFee);
                }
                orderVo.setOrderItemList(orderItems);
                orderVoList.add(orderVo);
            }

        }

        return orderVoList;
    }


    /**
     * 根据订单号查找到订单金额
     * @param out_trade_no
     * @return
     */
    @Override
    public Long findTotalFeeFromRedis(String out_trade_no) {
        return (Long) redisTemplate.boundHashOps("unPayOrderList").get(out_trade_no);
    }




    /**
     * 支付成功
     * 修改未支付订单状态
     * @param out_trade_no 订单号
     * @param transaction_id
     */
    @Override
    public void updateUnPayOrderStatus(String out_trade_no, String transaction_id) {

        //修改订单状态
        Order order1 = orderDao.selectByPrimaryKey(Long.valueOf(out_trade_no));
        order1.setStatus("2");
        order1.setPaymentTime(new Date());
        orderDao.updateByPrimaryKeySelective(order1);

        //存支付日志
        PayLog payLog = new PayLog();
        payLog.setCreateTime(new Date());
        payLog.setOutTradeNo(out_trade_no);
        payLog.setPayType(order1.getPaymentType());
        payLog.setTotalFee((Long) redisTemplate.boundHashOps("unPayOrderList").get(out_trade_no));
        payLog.setUserId(order1.getUserId());
        payLog.setTradeState("1");
        payLog.setPayTime(new Date());
        payLog.setTransactionId(transaction_id);
        payLogDao.insertSelective(payLog);

        //删除redis中该订单的数据
        redisTemplate.boundHashOps("unPayOrderList").delete(out_trade_no);
    }







    /**
     * 运营商后台,查询订单数据,用于导出excel
     * @return
     */
    @Override
    public List<Order> getOrderList() {
        return orderDao.selectByExample(null);
    }

    /**
     * 运营商后台根据时间查询订单量
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public ZImageResult findOrderCountByTime(Date startTime, Date endTime) {
        ZImageResult result = new ZImageResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        Map<String,Long> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        String date = null;
        OrderQuery query = new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andCreateTimeGreaterThanOrEqualTo(startTime);
        criteria.andCreateTimeLessThanOrEqualTo(endTime);
        List<Order> orderList = orderDao.selectByExample(query);
        if (orderList!=null&&orderList.size()>0){
            for (Order order : orderList) {
                BigDecimal payment = order.getPayment();
                if (payment!=null){
                    date = dateFormat.format(order.getCreateTime());
                    if (list.contains(date)){
                        resultMap.put(date,resultMap.get(date)+payment.longValue());
                    }else {
                        list.add(date);
                        resultMap.put(date,payment.longValue());
                    }
                }
            }
        }
        List<Long> dataList = new ArrayList<>();
        if (list.size()>0){
            for (String s : list) {
                dataList.add(resultMap.get(s));
            }
        }

        String[] strs = new String[list.size()];
        Long[] longs = new Long[dataList.size()];

        result.setDateList(list.toArray(strs));
        result.setDataList(dataList.toArray(longs));
        return result;
    }

    /**
     * 运营商后台查询各商家销售额
     * @return
     */
    @Override
    public BImageResult findTotalMoneyBySellerId() {
        BImageResult result = new BImageResult();
        List<String> sellerList = new ArrayList<>();
        List<Long> moneyList = new ArrayList<>();
        Map<String,Long> resultMap = new HashMap<>();
        OrderQuery query = new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        criteria.andStatusEqualTo("2");
        List<Order> orderList = orderDao.selectByExample(query);
        if (orderList!=null&&orderList.size()>0){
            for (Order order : orderList) {
                String sellerId = order.getSellerId();
                if (sellerId!=null){
                    Seller seller = sellerDao.selectByPrimaryKey(sellerId);
                    String name = seller.getName();
                    if (sellerList.contains(name)){
                        resultMap.put(name,resultMap.get(name)+order.getPayment().longValue());
                    }else {
                        sellerList.add(name);
                        resultMap.put(name,order.getPayment().longValue());
                    }
                }
            }
        }
        if (sellerList.size()>0){
            for (String s : sellerList) {
                moneyList.add(resultMap.get(s));
            }
        }
        String[] strs = new String[sellerList.size()];
        Long[] longs = new Long[sellerList.size()];
        result.setSellerList(sellerList.toArray(strs));
        result.setMoneyList(moneyList.toArray(longs));
        return result;
    }
}
