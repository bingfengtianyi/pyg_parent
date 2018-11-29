package com.itheima.pyg.controller.seckillGoodsOrder;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pyg.pojo.seckill.SeckillGoods;
import com.itheima.pyg.pojo.seckill.SeckillOrder;
import com.itheima.pyg.service.seckill.SeckillGoodsService;
import com.itheima.pyg.service.seckill.SeckillOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("seckillGoodsOrder")
public class SeckillGoodsOrderController {

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 查询秒杀活动订单
     *
     * @return
     */
    @RequestMapping("findOrderList")
    public List<SeckillOrder> findList() {
        return seckillOrderService.findSeckillOrderListFromDB();
    }
}
